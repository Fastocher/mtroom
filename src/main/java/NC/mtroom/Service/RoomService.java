package NC.mtroom.Service;

import NC.mtroom.Entity.Room;
import NC.mtroom.Exception.RoomNotFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

//    @Autowired
//    private RoomRepository roomRepository;


//    public Room getone(Long id) throws RoomNotFound {
//        Room room = roomRepository.findById(id).get();
//        if ( room.equals(null) ) {
//            throw new RoomNotFound("Комната не была найдена");
//        }
//        return room;
//    }
}
