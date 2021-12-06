package NC.mtroom.room.api.exeptions;

import java.time.LocalDateTime;

public class RoomNotFound extends RuntimeException{
    public RoomNotFound(Long message) {
        super("Room with ID = '"+message+"' not found");
    }
}
