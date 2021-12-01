package NC.mtroom.room.impl.service;

import NC.mtroom.room.api.service.IRoomService;
import NC.mtroom.room.impl.entity.Equipment;
import NC.mtroom.room.impl.entity.EquipmentType;
import NC.mtroom.room.impl.entity.Room;
import NC.mtroom.room.api.exeptions.RoomNotFound;

import NC.mtroom.room.impl.repository.EquipmentRepository;
import NC.mtroom.room.impl.repository.RoomRepository;

import NC.mtroom.user.api.model.UserHistoryDto;
import NC.mtroom.user.impl.entity.History;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.entity.UserHistory;
import NC.mtroom.user.impl.repository.HistoryRepository;
import NC.mtroom.user.impl.repository.UserHistoryRepository;
import NC.mtroom.user.impl.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.Instant;

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
    public RoomDto getRoom(Long id) throws RoomNotFound {

        Room room = roomRepository.findByRoomID(id);
        if ( room.equals(null) ) {
            throw new RoomNotFound("Комната не была найдена");
        }

        RoomDto roomDto = new RoomDto();

        roomDto.setRoom_uuid(room.getRoomID());
        //currentDto.setDescription(room.);
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
                    equipmentTypeDto.setDescription(equipmentType.getDescription());
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
    public List<TimeSegmentDto> getBooking(Long id) throws RoomNotFound{
        Room room = roomRepository.findByRoomID(id);
        if ( room.equals(null) ) {
            throw new RoomNotFound("Комната не была найдена");
        }
        List<History> historyList = room.getHistories();
        LinkedList<TimeSegmentDto> timeSegmentDtoLinkedList = new LinkedList<>();
        for (History history : historyList)
        {
            TimeSegmentDto timeSegmentDto = new TimeSegmentDto();
            timeSegmentDto.setStart(history.getStart().toString());
            timeSegmentDto.setEnd(history.getEnd().toString());

            timeSegmentDtoLinkedList.add(timeSegmentDto);
        }

        return timeSegmentDtoLinkedList;
    }

   @Override
    public ResponseEntity<?> setBooking(Long id, BookingDto bookingDto) throws Exception{

            History history = new History();
            history.setStart(Timestamp.valueOf(bookingDto.getTime().getStart()));
            history.setEnd(Timestamp.valueOf(bookingDto.getTime().getEnd()));
            Room room = roomRepository.findByRoomID(id);
            history.setRoomID(room);

            historyRepository.save(history);
            UserHistory userHistory = new UserHistory();
            UserEntity userEntity = userRepository.findByUsername(bookingDto.getAdmin());
            userHistory.setUserID(userEntity);
            userHistory.setOrg(true);
            userHistory.setHistoryID(history);

            userHistoryRepository.save(userHistory);
            return ResponseEntity.ok().body("Room successfully booked! BookingID = " + userHistory.getHistoryID().getHistoryID());

    }
    @Transactional
    @Override
    public ResponseEntity<?> deleteBooking(Long id,Long bookingID) throws Exception{
        historyRepository.deleteByHistoryID(bookingID);
        return ResponseEntity.ok().body("Successfully delete booking");
    }
}
