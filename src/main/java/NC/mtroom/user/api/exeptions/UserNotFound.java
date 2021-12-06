package NC.mtroom.user.api.exeptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message) {
        super("User with login = '" +message+ "' not found");
    }
}
