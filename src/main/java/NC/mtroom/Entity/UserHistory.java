package NC.mtroom.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity
@Table(name = "user_history")
@Getter
@Setter
@NoArgsConstructor
public class UserHistory {

    @Id
    private Long id;

    private boolean isOrg;


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "historyHistoryID", nullable = false)
    private History historyID;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID")
    private UserEntity userID;



}
