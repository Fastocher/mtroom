package NC.mtroom;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping(path = "/room")
//@Api(value = "ROOMS")

//@ApiOperation(value = "View room", response = Iterable.class)
//@RequestMapping (value = "/view")

public class RootController {

    private RoomService roomService;

    @ApiOperation(value = "View room", response = Room.class)
    @RequestMapping (value = "/view/{id}",method = GET,produces = "application/json")
    public Room showroom (@PathVariable Integer id, Model model){
            Room room  = roomService.getbyID(id);
        return room;
    }

}
