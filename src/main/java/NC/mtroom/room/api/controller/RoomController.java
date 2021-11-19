package NC.mtroom.room.api.controller;

import NC.mtroom.room.api.exeptions.RoomNotFound;
import NC.mtroom.room.api.service.IRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/room")
public class RoomController {
    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

//    @PostMapping
//    public ResponseEntity registration(@RequestBody Room room){
//        try{
//            roomService.registration(room);
//            return ResponseEntity.ok("Успешно сохранен");
//        } catch (UserAlreadyExist e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Ошибка");
//        }
//    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) throws RoomNotFound {
       return ResponseEntity.ok(roomService.getRoom(id));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory (Long userID) throws Exception{
        return ResponseEntity.ok(roomService.getUserHistory(userID));
    }
}
