package NC.mtroom.user.api.model;

import NC.mtroom.room.api.model.TimeSegmentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserHistoryDto {

    private Long room_uuid;

    private TimeSegmentDto time;

    private boolean isAdmin;

}
