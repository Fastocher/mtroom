package NC.mtroom.room.api.controller;

import NC.mtroom.room.api.exeptions.RoomNotFound;
import NC.mtroom.room.api.model.BookingDto;
import NC.mtroom.room.api.service.IRoomService;
import NC.mtroom.user.api.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/room")
public class RoomController {
    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) throws RoomNotFound {
        return ResponseEntity.ok(roomService.getRoom(id));
    }
    // показывает время на которое забронированы комнаты
    @GetMapping("/booking/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) throws RoomNotFound{
        return ResponseEntity.ok(roomService.getBooking(id));
    }

    @PostMapping("/booking/{id}")
    public ResponseEntity<?> setBooking(@PathVariable Long id ,@RequestBody BookingDto bookingDto) throws Exception {
        return ResponseEntity.ok(roomService.setBooking(id,bookingDto));
    }
    @DeleteMapping("/booking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id ,@RequestParam Long bookingID) throws Exception{
        return ResponseEntity.ok(roomService.deleteBooking(id,bookingID));
    }


}
