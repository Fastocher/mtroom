package NC.mtroom.room.impl.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "equipment_type")
@Getter
@Setter
@NoArgsConstructor
public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "equipmentEqID")
    private Equipment equipment;

    private String name;

    private String description;
}
