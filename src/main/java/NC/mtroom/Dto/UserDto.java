package NC.mtroom.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor

// Что- то по типу шаблона
//Используется как прослойка между API и Контроллером

public class UserDto {

    private String login;
    private String username;
    private String password;

}
