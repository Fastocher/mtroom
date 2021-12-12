package NC.mtroom.room.api.model;

import NC.mtroom.room.impl.entity.Equipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long room_uuid;

    private String name;

    private String photoURLs;

    private Long number_of_seats;

    private String location;

    private List<EquipmentDto> equipment;



}
