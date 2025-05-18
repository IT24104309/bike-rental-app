package lk.sliit.bike_rental_api.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Bike {

    private String bikeId;
    private String type;
    private String status;


}
