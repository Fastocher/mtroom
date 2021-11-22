package NC.mtroom.room.impl.repository;



import NC.mtroom.room.impl.entity.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EquipmentRepository extends CrudRepository<Equipment, Long> {
//    List<Equipment> findAll();
}
