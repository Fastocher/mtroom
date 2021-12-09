package NC.mtroom.user.impl.repository;

import NC.mtroom.user.impl.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String Username);
    UserEntity findByLogin(String Login);
    List<UserEntity> findAllByLoginIn(List<String> users);
}

