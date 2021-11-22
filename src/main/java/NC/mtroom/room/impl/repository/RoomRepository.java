package NC.mtroom.room.impl.repository;

import NC.mtroom.room.impl.entity.Room;
import NC.mtroom.user.impl.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Room findByName(String Name);
    Room findByRoomID(Long RoomID);

}
