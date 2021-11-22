package NC.mtroom.user.impl.repository;

import NC.mtroom.user.impl.entity.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {
//    History findByHistoryID();
}
