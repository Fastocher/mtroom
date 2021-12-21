package NC.mtroom.room.api.exeptions;

public class NonWorkingHours extends RuntimeException{
    public NonWorkingHours() {
        super("You can't book between 22:00 and 8:00");
    }
}