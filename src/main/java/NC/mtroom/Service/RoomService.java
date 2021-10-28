package NC.mtroom.Service;

import NC.mtroom.Entity.Room;
import NC.mtroom.Exception.RoomNotFound;
import NC.mtroom.Exception.UserAlreadyExist;
import NC.mtroom.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room registration(Room room) throws UserAlreadyExist {
        if (roomRepository.findByName( room.getName()) != null ){
            throw new UserAlreadyExist("Пользователь уже существует");
        }
       return roomRepository.save(room);
    }
    public Room getone(Long id) throws RoomNotFound {
        Room room = roomRepository.findById(id).get();
        if ( room.equals(null) ) {
            throw new RoomNotFound("Комната не была найдена");
        }
        return room;
    }
}
