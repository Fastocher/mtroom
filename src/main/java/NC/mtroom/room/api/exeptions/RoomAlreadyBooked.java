package NC.mtroom.room.api.exeptions;

import java.time.LocalDateTime;

public class RoomAlreadyBooked extends RuntimeException{
    public RoomAlreadyBooked(LocalDateTime start, LocalDateTime end) {
        super("Time : " + start.toLocalTime() + " - " + end.toLocalTime() + " already booked");
    }
}
