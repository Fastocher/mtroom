package NC.mtroom.Repository;

import NC.mtroom.Entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
    Room findByName(String Name);
}
