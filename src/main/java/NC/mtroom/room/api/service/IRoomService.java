package NC.mtroom.room.api.service;

import NC.mtroom.room.api.exeptions.RoomNotFound;
import NC.mtroom.room.api.model.BookingDto;
import NC.mtroom.room.api.model.RoomDto;
import NC.mtroom.room.api.model.TimeSegmentDto;
import NC.mtroom.room.impl.entity.Room;
import NC.mtroom.user.api.model.UserHistoryDto;
import NC.mtroom.user.impl.entity.UserEntity;
import NC.mtroom.user.impl.entity.UserHistory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoomService {
    public RoomDto getRoom(Long id) throws RoomNotFound;
    public List<TimeSegmentDto> getBooking(Long id) throws RoomNotFound;
    public ResponseEntity setBooking(Long id, BookingDto bookingDto) throws Exception;
    public ResponseEntity deleteBooking(Long id,Long bookingID) throws Exception;


}
