package NC.mtroom.user.api.exeptions;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String message) {
        super("User with login = '" +message + "' already exist");
    }
}
