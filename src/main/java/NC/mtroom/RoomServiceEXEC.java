package NC.mtroom;

import org.springframework.stereotype.Service;

@Service
public class RoomServiceEXEC implements RoomService {
    @Override
    public Room getbyID(Integer id)
    {
        return RoomREPOS.findbyId(id).orElse(null);
    }
}
