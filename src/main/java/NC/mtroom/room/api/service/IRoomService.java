package NC.mtroom.room.api.service;

import NC.mtroom.room.api.model.BookingDto;
import NC.mtroom.room.api.model.RoomDto;
import NC.mtroom.room.api.model.TimeSegmentDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    public RoomDto getRoom(Long id) ;
    public List<TimeSegmentDto> getBooking(Long id, LocalDate date);
    public ResponseEntity setBooking(BookingDto bookingDto) ;
    public ResponseEntity deleteBooking(Long bookingID) ;


}
