package NC.mtroom.room.api.controller;

import NC.mtroom.room.api.model.BookingDto;
import NC.mtroom.room.api.model.RoomDto;
import NC.mtroom.room.api.service.IRoomService;
import io.github.classgraph.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;


@Validated
@RestController
@CrossOrigin
@RequestMapping(path = "/room")
public class RoomController {
    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("/{id}")
    public RoomDto getRoom(@PathVariable @Min(1) Long id)  {
        return roomService.getRoom(id);
    }

    // показывает время на которое забронированы комнаты
    @GetMapping("/booking/{id}")
    public ResponseEntity<?> getBooking(@PathVariable @Min(1) Long id, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(roomService.getBooking(id,date));
    }

    @GetMapping("/picture")
    public InputStream picture() throws IOException {
        return new ClassPathResource("static/img/room1.png").getInputStream();

    }

    @GetMapping("/all")
    public ResponseEntity<?> getBooking() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping("/booking/")
    public ResponseEntity<?> setBooking(@RequestBody @Valid BookingDto bookingDto){
        roomService.setBooking(bookingDto);
        return ResponseEntity.ok().body("Room successfully booked!");
    }
    @DeleteMapping("/booking/")
    public ResponseEntity<?> deleteBooking(@RequestParam @Min(1)  Long historyID){
        roomService.deleteBooking(historyID);
        return ResponseEntity.ok().body("Successfully delete booking");
    }


}
