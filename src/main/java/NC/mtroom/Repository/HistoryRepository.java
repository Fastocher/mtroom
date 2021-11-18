package NC.mtroom.Repository;

import NC.mtroom.Entity.History;
import NC.mtroom.Entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<History, Long> {
    History findByHistoryID(Integer HistoryID);
}
