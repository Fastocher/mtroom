package NC.mtroom.room.impl.service;

import NC.mtroom.room.api.service.IRoomService;
import NC.mtroom.room.impl.entity.Room;
import NC.mtroom.room.api.exeptions.RoomNotFound;

import NC.mtroom.room.impl.repository.RoomRepository;

import NC.mtroom.user.impl.entity.UserHistory;
import NC.mtroom.user.impl.repository.HistoryRepository;
import NC.mtroom.user.impl.repository.UserHistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final HistoryRepository historyRepository;

    public RoomService(RoomRepository roomRepository, UserHistoryRepository userHistoryRepository, HistoryRepository historyRepository) {
        this.roomRepository = roomRepository;
        this.userHistoryRepository = userHistoryRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public UserHistory getUserHistory(Long id) {
        return userHistoryRepository.findByUserID(id);
    }

    @Override
    public Room getRoom(Long id) throws RoomNotFound {
        Room room = roomRepository.findByRoomID(id);
        if ( room.equals(null) ) {
            throw new RoomNotFound("Комната не была найдена");
        }
        return room;
    }
}
