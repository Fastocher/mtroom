package NC.mtroom.room.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    @NotNull(message = "Поле Title не найдено")
    @NotBlank(message = "Поле Title не должно быть пустым")
    private String title;

    @NotNull(message = "Поле Admin не найдено")
    @NotBlank(message = "Поле Admin не должно быть пустым")
    private String admin;

    @NotNull(message = "Поле Room_id не найдено")
    private Long room_uuid;


    private TimeSegmentDto time;

    private List<String> invited_users;
}
