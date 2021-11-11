package NC.mtroom.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyID;


    private Timestamp start;

    private Timestamp end;


    @ManyToOne
    @JoinColumn(name = "room_id_room_id")
    private Room roomID;

}
