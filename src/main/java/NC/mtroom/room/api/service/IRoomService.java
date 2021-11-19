package NC.mtroom.room.api.service;

import NC.mtroom.room.api.exeptions.RoomNotFound;
import NC.mtroom.room.impl.entity.Room;
import NC.mtroom.user.impl.entity.UserHistory;

public interface IRoomService {
    public Room getRoom(Long id) throws RoomNotFound;
    public UserHistory getUserHistory(Long id);
}
