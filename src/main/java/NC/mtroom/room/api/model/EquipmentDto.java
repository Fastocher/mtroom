package NC.mtroom.room.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {

    private Long eqID;

    private String name;
    private EquipmentTypeDto equipmentType;

}
