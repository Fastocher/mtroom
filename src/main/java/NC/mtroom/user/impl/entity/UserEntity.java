package NC.mtroom.user.impl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor

//Создание сущности пользователя. Также создается таблица в бд с колонками ниже
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long userID;

    @JsonIgnore
    @OneToMany(mappedBy = "userID")
    private List<UserHistory> userHistories;

    private String login;

    private String username;

    @JsonIgnore
    private String password;


}
