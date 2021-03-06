package NC.mtroom.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer EqID;

    private String name;

    @OneToMany(mappedBy = "equipment")
    private List<EquipmentType> equipmentType;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roomRoomID")
    private Room roomID;
}
