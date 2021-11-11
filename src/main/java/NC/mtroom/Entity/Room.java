package NC.mtroom.Entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Room")
@Getter
@Setter
@NoArgsConstructor
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomID;

    private String name;

    private long num_of_seats;

    private String photos;

    private String location;

}
