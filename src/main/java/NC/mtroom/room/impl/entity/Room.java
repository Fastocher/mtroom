package NC.mtroom.room.impl.entity;


import NC.mtroom.user.impl.entity.History;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


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

    @OneToMany(mappedBy = "roomID")
    private List<History> histories;


   // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private List<Room> roomID;

    private String name;

    private long num_of_seats;

    private String photos;

    private String location;


}
