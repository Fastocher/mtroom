package NC.mtroom.user.impl.entity;

import NC.mtroom.room.impl.entity.Room;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.mapping.Array;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "history")
@Data
@AllArgsConstructor
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

    @ElementCollection
    @CollectionTable(name = "historyInvited_users", joinColumns = @JoinColumn(name = "history_historyid"))
    @Column(name = "invited_users")
    private List<String> invited_users;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roomRoomID")
    private Room roomID;

}
