package NC.mtroom.Repository;

import NC.mtroom.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);
}
