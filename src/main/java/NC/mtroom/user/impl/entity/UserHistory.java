package NC.mtroom.user.impl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "user_history")
@Getter
@Setter
@NoArgsConstructor
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private Long id;

    private boolean isOrg;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "historyHistoryID", nullable = false)
    private History historyID;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "UserID")
    private UserEntity userID;



}
