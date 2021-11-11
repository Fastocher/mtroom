package NC.mtroom.Entity;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@Table(name = "user_history")
@Getter
@Setter
@NoArgsConstructor
public class UserHistory {

    @Id
    private Integer Id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "history_id_history_id", nullable = false)
    private History historyID;


     private boolean isOrg;

    @ManyToOne
    @JoinColumn(name = "login_id")
    private UserEntity login;



}
