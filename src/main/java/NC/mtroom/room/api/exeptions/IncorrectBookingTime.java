package NC.mtroom.room.api.exeptions;

public class IncorrectBookingTime extends RuntimeException{
    public IncorrectBookingTime() {
        super("End-time should be greater than start-time");
    }
}
