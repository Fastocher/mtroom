package NC.mtroom.room.impl.repository;



import NC.mtroom.room.impl.entity.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {

}
