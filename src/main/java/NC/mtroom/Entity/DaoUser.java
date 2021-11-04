package NC.mtroom.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor

public class DaoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //private String login;

    private String username;

    @JsonIgnore
    private String password;


}
