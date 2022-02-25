import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Arrays.*;

/***
 * Lift simulator from CodeWars
 */
public class Dinglemouse {

    public static int[] theLift(final int[][] queues, final int capacity) {

        // make actual queues
        List<Integer>[] queues2 = stream(queues).map(
                                            q -> stream(q).mapToObj(n -> n).collect(Collectors.toList())
                                    )
                                    .toArray(List[]::new);
        // Your code here
        LinkedList<Integer> floors = new LinkedList<>();

        // state machine
        int floor = 0;
        int dir = 1;
        LinkedList<Integer> elevator = new LinkedList<>();
        floors.add(0, 0);
        while(true){

            boolean doorOpened = false;
            // first people has to get out of the elevator
            while(elevator.remove((Integer)floor))
                doorOpened = true;

            // shift direction
            if(floor == 0)
                dir = 1;
            else if(floor == queues.length - 1)
                dir = -1;

            // look at the people at the queue
            Iterator<Integer> ite = queues2[floor].iterator();
            while(ite.hasNext()) {
                int d = ite.next();
                // at least one that is going on the same direction
                if((dir == 1 && d > floor) || (dir == -1 && d < floor)) {
                    doorOpened = true;
                    // allow people in only if capacity
                    if (elevator.size() < capacity) {
                        elevator.add(d);
                        ite.remove();
                    }
                }
            }
            // if the door opened but not twice in the same floor
            if(doorOpened && floors.getLast() != floor)
                floors.add(floor);

            if(elevator.isEmpty() && stream(queues2).allMatch(q -> q.isEmpty() ))
                break;

            // move the elevator
            floor += dir;
        }
        //add last if not opened
        if(floors.getLast() != 0)
            floors.add(0);

        return floors.stream()
                .mapToInt(m -> m)
                .toArray();
    }

}