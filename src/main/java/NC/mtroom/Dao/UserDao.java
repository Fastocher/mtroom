package NC.mtroom.Dao;

import NC.mtroom.Entity.UserEntity;
import NC.mtroom.Entity.UserHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserDao extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
