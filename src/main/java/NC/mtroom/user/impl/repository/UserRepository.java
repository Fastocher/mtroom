package NC.mtroom.user.impl.repository;

import NC.mtroom.user.impl.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
   // UserEntity findByLogin(String login);
    UserEntity findByUsername(String Username);
}

