package NC.mtroom.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    private Integer type;

    @ManyToOne
    @JoinColumn(name = "room_id_room_id")
    private Room roomID;
}
