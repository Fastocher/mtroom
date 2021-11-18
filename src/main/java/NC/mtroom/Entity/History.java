package NC.mtroom.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyID;

    @OneToMany(mappedBy = "historyID")
//@OneToMany
//@JoinColumns ({
//        @JoinColumn(name="HistoryID", referencedColumnName = "historyHistoryID")
//})

    private List<UserHistory> userHistories;


    private Timestamp start;

    private Timestamp end;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roomRoomID")
    private Room roomID;

}
