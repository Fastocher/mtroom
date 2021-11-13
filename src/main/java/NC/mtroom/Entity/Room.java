package NC.mtroom.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Room")
@Getter
@Setter
@NoArgsConstructor
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;

    @OneToMany(mappedBy = "roomID")
    private List<Equipment> equipment;

   // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<Room> roomID;

    private String name;

    private long num_of_seats;

    private String photos;

    private String location;


}
