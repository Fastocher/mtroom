package NC.mtroom.room.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private String admin;

    private Long room_uuid;

    private TimeSegmentDto time;

//    private InvitedUsers users;
}
