package NC.mtroom.room.impl.service;

import NC.mtroom.JWTConfig.JwtRequestFilter;
import NC.mtroom.room.api.exeptions.*;
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

import java.time.Duration;
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
    private final JwtRequestFilter jwtRequestFilter;

    public RoomService(RoomRepository roomRepository,
                       UserHistoryRepository userHistoryRepository,
                       HistoryRepository historyRepository,
                       UserRepository userRepository,
                       JwtRequestFilter jwtRequestFilter) {
        this.roomRepository = roomRepository;
        this.userHistoryRepository = userHistoryRepository;
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.jwtRequestFilter = jwtRequestFilter;
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
        roomDto.setPhotoURLs(room.getPhotos());

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

           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
           LocalDateTime start = LocalDateTime.parse(bookingDto.getTime().getStart(), formatter);
           LocalDateTime end = LocalDateTime.parse(bookingDto.getTime().getEnd(), formatter);


           if (start.isAfter(end)){
               throw new IncorrectBookingTime();
           }

           if (start.isBefore(LocalDateTime.now().minusMinutes(5))){
               throw new PastBooking(start);
           }

           Duration duration = Duration.between(start,end);
           if (duration.toHours() > 6){
               throw new BookingDuration(duration.toHours(), end.getMinute()- start.getMinute());
           } else if(duration.toMinutes() < 15){
               throw new BookingDuration(end.getMinute()- start.getMinute());
           }

           Room room = roomRepository.findByRoomID(bookingDto.getRoom_uuid());
           if (room == null ) {
               throw new RoomNotFound(bookingDto.getRoom_uuid());
           }

           UserEntity userEntity = userRepository.findByLogin(bookingDto.getAdmin());
           if (userEntity == null){
               throw new UserNotFound(bookingDto.getAdmin());
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
            userHistoryLinkedList.add(setUserHistory(userEntity,history,true));
            if (bookingDto.getInvited_users() != null) {
                List<UserEntity> users = userRepository.findAllByLoginIn(bookingDto.getInvited_users());
                for (UserEntity user : users) { //add from list
                    userHistoryLinkedList.add(setUserHistory(user,history,false));
                }
            }
            userHistoryRepository.saveAll(userHistoryLinkedList);
    }
    public UserHistory setUserHistory(UserEntity userEntity, History history, boolean isAdmin)
    {
        UserHistory userHistory = new UserHistory();
        userHistory.setUserID(userEntity);
        userHistory.setAdmin(isAdmin);
        userHistory.setHistoryID(history);
        return userHistory;
    }

    @Transactional
    @Override
    public void deleteBooking(Long historyID){
        History history = historyRepository.findByHistoryID(historyID);
        if ( history == null){
            throw new HistoryNotFound(historyID);
        }

        UserEntity userEntity = userRepository.findByLogin(jwtRequestFilter.getCurrentLogin());
        UserHistory userHistory = userHistoryRepository.findDistinctByHistoryIDAndUserID(history, userEntity);
        if (userHistory == null){
            throw new HistoryNotFound(historyID);
        }
        if (userHistory.isAdmin()){
            historyRepository.deleteByHistoryID(historyID);
        }else{
            userHistoryRepository.delete(userHistory);
        }

    }
}
