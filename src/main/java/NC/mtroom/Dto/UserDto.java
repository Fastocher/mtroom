package NC.mtroom.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor

// Что- то по типу шаблона
//Используется как прослойка между API и Контроллером
//@Entity
public class UserDto {
    @Pattern(regexp = "^[0-9]$")
    private String login;
    @NotBlank(message = "Please provide a price")
    private String username;
    @NotEmpty(message = "Please provide a price")
    private String password;

}
