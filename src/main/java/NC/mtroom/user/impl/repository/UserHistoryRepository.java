package NC.mtroom.user.impl.repository;

import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.entity.UserHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHistoryRepository extends CrudRepository<UserHistory, Long> {
    UserHistory findByUserID(UserEntity userEntity);
    List<UserHistory> findAllByUserID(UserEntity userEntity);
}
