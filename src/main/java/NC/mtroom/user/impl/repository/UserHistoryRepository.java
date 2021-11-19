package NC.mtroom.user.impl.repository;

import NC.mtroom.user.impl.entity.UserHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends CrudRepository<UserHistory, Long> {
    UserHistory findByUserID(Long userID);
}
