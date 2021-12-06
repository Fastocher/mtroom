package NC.mtroom.room.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    @NotNull(message = "Поле Admin не найдено")
    @NotBlank(message = "Поле Admin не должно быть пустым")
    private String admin;

    @NotNull(message = "Поле Room_id не найдено")
    private Long room_uuid;


    private TimeSegmentDto time;

//    private InvitedUsers users;
}
