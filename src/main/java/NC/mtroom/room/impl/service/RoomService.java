package NC.mtroom.room.impl.service;

import NC.mtroom.room.api.exeptions.HistoryNotFound;
import NC.mtroom.room.api.exeptions.PastBooking;
import NC.mtroom.room.api.exeptions.RoomAlreadyBooked;
import NC.mtroom.room.api.exeptions.RoomNotFound;
import NC.mtroom.room.api.model.*;
import NC.mtroom.room.api.service.IRoomService;
import NC.mtroom.room.impl.entity.Equipment;
import NC.mtroom.room.impl.entity.EquipmentType;
import NC.mtroom.room.impl.entity.Room;
import NC.mtroom.room.impl.repository.EquipmentRepository;
import NC.mtroom.room.impl.repository.RoomRepository;
import NC.mtroom.user.api.exeptions.UserNotFound;
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

    public Iterable<RoomDto> getAllRooms(){

        Iterable<Room> rooms = roomRepository.findAll();
        LinkedList<RoomDto> roomDtoLinkedList = new LinkedList<>();
        for (Room room : rooms){
         roomDtoLinkedList.add(RoomtoRoomDto(room));
        }
        return roomDtoLinkedList;
    }

    public RoomDto RoomtoRoomDto(Room room){

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

            EquipmentTypeDto equipmentTypeDto = new EquipmentTypeDto(); //по образу и подобию
            EquipmentType equipmentType = equipment.getEquipmentType();
            equipmentTypeDto.setName(equipmentType.getName());
            equipmentDto.setEquipmentType(equipmentTypeDto);
            equipmentDtoLinkedList.add(equipmentDto); //Добавляю его в список
        }
        roomDto.setEquipment(equipmentDtoLinkedList); //Список добавляю в поле эквипмент в Дто комнаты
        return roomDto;
    }


    @Override
    public RoomDto getRoom(Long id) {
        Room room = roomRepository.findByRoomID(id);
        if ( room == null ) {
            throw new RoomNotFound(id);
        }
        return RoomtoRoomDto(room);
    }

    @Override
    public List<TimeSegmentDto> getBooking(Long id, LocalDate date) {
        Room room = roomRepository.findByRoomID(id);
        if ( room == null ) {
            throw new RoomNotFound(id);
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
                timeSegmentDto.setTitle(history.getTitle());
                timeSegmentDtoLinkedList.add(timeSegmentDto);
            }
        }
        return timeSegmentDtoLinkedList;
    }

   @Override
    public void setBooking(BookingDto bookingDto) {


            Room room = roomRepository.findByRoomID(bookingDto.getRoom_uuid());
            if (room == null ) {
                throw new RoomNotFound(bookingDto.getRoom_uuid());
            }

            UserEntity userEntity = userRepository.findByLogin(bookingDto.getAdmin());
            if (userEntity == null){
               throw new UserNotFound(bookingDto.getAdmin());
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            LocalDateTime start = LocalDateTime.parse(bookingDto.getTime().getStart(), formatter);
            LocalDateTime end = LocalDateTime.parse(bookingDto.getTime().getEnd(), formatter);
            if (start.isBefore(LocalDateTime.now())){
                throw new PastBooking(start);
            }

            List<History> historyList = room.getHistories();
            for (History historycheck : historyList) {
                if (!(end.isBefore(historycheck.getStart()) || end.isEqual(historycheck.getStart())
                           || start.isAfter(historycheck.getEnd()) || start.isEqual(historycheck.getEnd()) )){
                    throw new RoomAlreadyBooked(start,end);
                }
            }

       // с помощью gethistories выцепляем все истории что закреплены за комнатой
       // также важно чтобы выцеплялись только истории сегодняшнего дня
       // и проверяем не пересекаются ли даты


            History history = new History();
            history.setStart(start);
            history.setEnd(end);
            history.setRoomID(room);
            history.setTitle(bookingDto.getTitle());
            history.setInvited_users(bookingDto.getInvited_users());
            historyRepository.save(history);

       //Добавляю в историю колонку Invited users (массив)
       //С post запросом принимаю и записываю в колонку юзеров
       //Сделать функцию которая бы возвращала List of UserEntity по List of logins
       //Сразу прохожусь по массиву и каждому юзеру добавляю в User_history запись о том что он забукан на встречу

       LinkedList<UserHistory> userHistoryLinkedList = new LinkedList<>();
            UserHistory userHistoryAdmin = new UserHistory();// Add admin
            userHistoryAdmin.setUserID(userEntity);
            userHistoryAdmin.setAdmin(true);
            userHistoryAdmin.setHistoryID(history);
            userHistoryLinkedList.add(userHistoryAdmin);

        if (bookingDto.getInvited_users() != null) {
                List<UserEntity> users = userRepository.findAllByLoginIn(bookingDto.getInvited_users());
                for (UserEntity user : users) { //add from list
                    UserHistory userHistory = new UserHistory();

                    userHistory.setUserID(user);
                    userHistory.setAdmin(false);
                    userHistory.setHistoryID(history);

                    userHistoryLinkedList.add(userHistory);
                }
        }



            userHistoryRepository.saveAll(userHistoryLinkedList);
    }

    @Transactional
    @Override
    public void deleteBooking(Long historyID){
        if (historyRepository.findByHistoryID(historyID) == null){
            throw new HistoryNotFound(historyID);
        }
        historyRepository.deleteByHistoryID(historyID);
    }
}
