package NC.mtroom.room.impl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "equipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EqID;

    private String name;

    @OneToOne(mappedBy = "equipment")
    private EquipmentType equipmentType;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roomRoomID")
    private Room roomID;
}
