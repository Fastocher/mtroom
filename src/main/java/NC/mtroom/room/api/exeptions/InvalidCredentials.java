package NC.mtroom.room.api.exeptions;

public class InvalidCredentials extends RuntimeException{
    public InvalidCredentials() {
        super("Invalid login or password");
    }
}
