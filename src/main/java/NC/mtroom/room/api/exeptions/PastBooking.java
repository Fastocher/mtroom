package NC.mtroom.room.api.exeptions;

import java.time.LocalDateTime;

public class PastBooking extends RuntimeException{
    public PastBooking(LocalDateTime dateTime) {
        super("You can't book past! " + dateTime);
    }
}
