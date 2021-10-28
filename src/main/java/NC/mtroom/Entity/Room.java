package NC.mtroom.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Room")
//@Data
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomID;

    //@Column(name = "Name", length = 128, nullable = false)
    private String name;

    //@Column(name = "NumOfSeats", nullable = false)
    private long num_of_seats;

  //  @Column(name = "Photos", nullable = true)
    private String photos;

//    public long getRoomID() {
//        return roomID;
//    }
//
//    public void setRoomID(long roomID) {
//        this.roomID = roomID;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public long getNumOfSeats() {
//        return numOfSeats;
//    }
//
//    public void setNumOfSeats(long numOfSeats) {
//        this.numOfSeats = numOfSeats;
//    }
//
//    public String getPhotos() {
//        return photos;
//    }
//
//    public void setPhotos(String photos) {
//        this.photos = photos;
//    }
}
