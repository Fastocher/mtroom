package NC.mtroom.room.impl.service;

import NC.mtroom.room.api.exeptions.HistoryNotFound;
import NC.mtroom.room.api.exeptions.RoomAlreadyBooked;
import NC.mtroom.room.api.exeptions.RoomNotFound;
import NC.mtroom.room.api.model.*;
import NC.mtroom.room.api.service.IRoomService;
import NC.mtroom.room.impl.entity.Equipment;
import NC.mtroom.room.impl.entity.EquipmentType;
import NC.mtroom.room.impl.entity.Room;
import NC.mtroom.room.impl.repository.EquipmentRepository;
import NC.mtroom.room.impl.repository.RoomRepository;
import NC.mtroom.user.impl.entity.History;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.entity.UserHistory;
import NC.mtroom.user.impl.repository.HistoryRepository;
import NC.mtroom.user.impl.repository.UserHistoryRepository;
import NC.mtroom.user.impl.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final EquipmentRepository equipmentRepository;

    public RoomService(RoomRepository roomRepository,
                       UserHistoryRepository userHistoryRepository,
                       HistoryRepository historyRepository,
                       UserRepository userRepository,
                       EquipmentRepository equipmentRepository) {
        this.roomRepository = roomRepository;
        this.userHistoryRepository = userHistoryRepository;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
    }


    @Override
    public RoomDto getRoom(Long id) {
        Room room = roomRepository.findByRoomID(id);
        if ( room == null ) {
            throw new RoomNotFound();
        }


        RoomDto roomDto = new RoomDto();

        roomDto.setRoom_uuid(room.getRoomID());
        roomDto.setName(room.getName());
        roomDto.setNumber_of_seats(room.getNum_of_seats());
        roomDto.setLocation(room.getLocation());

        //Заполнение поля приборов в комнате

        List<Equipment> equipmentList = room.getEquipment(); //получаю список приборов
        LinkedList<EquipmentDto> equipmentDtoLinkedList =  new LinkedList<>(); //создаю пустой список приборов ДТО
        for (Equipment equipment : equipmentList){  //запускаю цикл по эквипментам
            EquipmentDto equipmentDto = new EquipmentDto(); //создаю новый экземпляр класса
            equipmentDto.setName(equipment.getName());  //Заполняю
            equipmentDto.setEqID(equipment.getEqID());
                //Заполнение типов приборов из тех что есть в комнате
                List<EquipmentType> equipmentTypeList = equipment.getEquipmentType(); //Получаю все типы что есть в текущей комнате
                LinkedList<EquipmentTypeDto> equipmentTypeDtoLinkedList =  new LinkedList<>(); //создаю пустой список типов приборов ДТО
                for (EquipmentType equipmentType : equipmentTypeList)
                {
                    EquipmentTypeDto equipmentTypeDto = new EquipmentTypeDto(); //по образу и подобию
                    equipmentTypeDto.setName(equipmentType.getName());
                    equipmentTypeDtoLinkedList.add(equipmentTypeDto);
                }
            equipmentDto.setEquipmentType(equipmentTypeDtoLinkedList); // добавляю список типов в список приборов
            equipmentDtoLinkedList.add(equipmentDto); //Добавляю его в список
        }
        roomDto.setEquipment(equipmentDtoLinkedList); //Список добавляю в поле эквипмент в Дто комнаты

        return roomDto;
    }

    @Override
    public List<TimeSegmentDto> getBooking(Long id, LocalDate date) {
        Room room = roomRepository.findByRoomID(id);
        if ( room == null ) {
            throw new RoomNotFound();
        }
        if (date == null){
            date = LocalDate.now();
        }
        List<History> historyList = room.getHistories();
        LinkedList<TimeSegmentDto> timeSegmentDtoLinkedList = new LinkedList<>();
        for (History history : historyList)
        {
            if (date.equals(history.getStart().toLocalDate())) {

                TimeSegmentDto timeSegmentDto = new TimeSegmentDto();
                timeSegmentDto.setStart(history.getStart().toString());
                timeSegmentDto.setEnd(history.getEnd().toString());
                timeSegmentDto.setHistoryID(history.getHistoryID());
                timeSegmentDtoLinkedList.add(timeSegmentDto);
            }
        }
        return timeSegmentDtoLinkedList;
    }

   @Override
    public ResponseEntity<?> setBooking(Long id, BookingDto bookingDto) {

            History history = new History();
            Room room = roomRepository.findByRoomID(bookingDto.getRoom_uuid());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
//            LocalDateTime start = bookingDto.getTime().getStart();
//            LocalDateTime end = bookingDto.getTime().getStart();

            LocalDateTime start = LocalDateTime.parse(bookingDto.getTime().getStart(), formatter);
            LocalDateTime end = LocalDateTime.parse(bookingDto.getTime().getEnd(), formatter);


       // с помощью gethistories выцепляем все истории что закреплены за комнатой
       // также важно чтобы выцеплялись только истории сегодняшнего дня
       // и проверяем не пересекаются ли даты

            List<History> historyList = room.getHistories();

            for (History historycheck : historyList) {
                if (!(end.isBefore(historycheck.getStart()) || end.isEqual(historycheck.getStart())
                        || start.isAfter(historycheck.getEnd()) || start.isEqual(historycheck.getEnd()) )){
                    throw new RoomAlreadyBooked();
                }
            }

            history.setStart(start);
            history.setEnd(end);
            history.setRoomID(room);

            historyRepository.save(history);
            UserHistory userHistory = new UserHistory();
            UserEntity userEntity = userRepository.findByUsername(bookingDto.getAdmin());
            userHistory.setUserID(userEntity);
            userHistory.setAdmin(true);
            userHistory.setHistoryID(history);

            userHistoryRepository.save(userHistory);
            return ResponseEntity.ok().body("Room successfully booked! BookingID = ");


    }
    @Transactional
    @Override
    public ResponseEntity<?> deleteBooking(Long id,Long bookingID){
        if (historyRepository.findByHistoryID(bookingID) == null){
            throw new HistoryNotFound();
        }
        historyRepository.deleteByHistoryID(bookingID);
        return ResponseEntity.ok().body("Successfully delete booking");
    }
}
