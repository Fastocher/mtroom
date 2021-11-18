package NC.mtroom.Repository;

import NC.mtroom.Entity.UserHistory;

import org.springframework.data.repository.CrudRepository;



public interface UserHistoryRepository extends CrudRepository<UserHistory, Long> {
    UserHistory findByUserID(Long userID);
}
