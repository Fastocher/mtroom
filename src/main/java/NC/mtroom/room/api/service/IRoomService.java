package NC.mtroom.room.api.service;

import NC.mtroom.room.api.model.BookingDto;
import NC.mtroom.room.api.model.RoomDto;
import NC.mtroom.room.api.model.TimeSegmentDto;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    public RoomDto getRoom(Long id) ;
    public List<TimeSegmentDto> getBooking(Long id, LocalDate date);
    public void setBooking(BookingDto bookingDto) ;
    public void deleteBooking(Long bookingID) ;
    public Iterable<RoomDto> getAllRooms();

}
