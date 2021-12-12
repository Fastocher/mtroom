package NC.mtroom.room.api.exeptions;

public class BookingDuration extends RuntimeException{
    public BookingDuration(Long hours, int minutes) {
            super("Booking time should be less than 6 hours and more 15 minutes! You tried book " + hours + " hours " + minutes + " minutes!");
    }
    public BookingDuration(int minutes){
        super("Booking time should be less than 6 hours and more 15 minutes! You tried book " + minutes + " minutes!");
    }

}
