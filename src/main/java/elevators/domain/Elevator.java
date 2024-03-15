package elevators.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Elevator{
    private int id;
    private int currentFloor;
    private int nextFloor;
}
