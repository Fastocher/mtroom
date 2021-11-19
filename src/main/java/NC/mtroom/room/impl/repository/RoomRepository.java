package NC.mtroom.room.impl.repository;

import NC.mtroom.room.impl.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Room findByName(String Name);
    Room findByRoomID(Long RoomID);
}
