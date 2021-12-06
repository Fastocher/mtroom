package NC.mtroom.room.api.exeptions;

public class HistoryNotFound extends RuntimeException{
    public HistoryNotFound(Long message) {
        super("History with id = '" +message + "' not found");
    }
}
