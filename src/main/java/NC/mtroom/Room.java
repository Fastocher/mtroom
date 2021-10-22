package NC.mtroom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue
    @Column(name = "RoomID", nullable = false)
    private int RoomID;
    @Column(name = "Name", length = 128, nullable = false)
    private String Name;
    @Column(name = "NumOfSeats", nullable = false)
    private int NumOfSeats;
    @Column(name = "Photos", nullable = true)
    private int Photos;

    public Room (int RoomID, String Name, int NumOfSeats, int Photos) {
        this.RoomID = RoomID;
        this.Name = Name;
        this.NumOfSeats = NumOfSeats;
        this.Photos =  Photos;
    }
    public int getId() {
        return RoomID;
    }
}
