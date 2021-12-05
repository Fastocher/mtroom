package NC.mtroom.room.api.service;

import NC.mtroom.room.api.model.BookingDto;
import NC.mtroom.room.api.model.RoomDto;
import NC.mtroom.room.api.model.TimeSegmentDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService {
    public RoomDto getRoom(Long id) ;
    public List<TimeSegmentDto> getBooking(Long id, LocalDate date);
    public ResponseEntity setBooking(Long id, BookingDto bookingDto) ;
    public ResponseEntity deleteBooking(Long id,Long bookingID) ;


}
