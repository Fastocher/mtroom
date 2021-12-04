package NC.mtroom.user.impl.repository;

import NC.mtroom.user.impl.entity.History;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {
    void deleteByHistoryID(Long BookingID);
    History findByHistoryID(Long BookingID);
}
