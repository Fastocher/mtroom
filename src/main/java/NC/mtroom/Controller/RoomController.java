package NC.mtroom.Controller;

import NC.mtroom.Entity.Room;
import NC.mtroom.Exception.RoomNotFound;
import NC.mtroom.Exception.UserAlreadyExist;
import NC.mtroom.Service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/room")
@AllArgsConstructor
public class RoomController {

    @Autowired
    private RoomService roomService;

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
//    @GetMapping
//    public ResponseEntity getoneRoom(@RequestParam Long id){
//        try{
//           return ResponseEntity.ok(roomService.getone(id));
//        } catch (RoomNotFound e) {
//           return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Ошибка");
//        }
//    }


}
