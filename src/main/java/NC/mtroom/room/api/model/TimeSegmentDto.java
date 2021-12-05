package NC.mtroom.room.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSegmentDto {

   // @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime start;
   // @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime end;

    private Long HistoryID;

}
