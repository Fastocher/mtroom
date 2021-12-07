package NC.mtroom.user.impl.entity;

import NC.mtroom.room.impl.entity.Room;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyID;

    private String title;

    @OneToMany(mappedBy = "historyID", cascade = CascadeType.ALL)
    private List<UserHistory> userHistories;

    private LocalDateTime start;

    private LocalDateTime end;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roomRoomID")
    private Room roomID;

}
