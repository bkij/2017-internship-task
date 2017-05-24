package pl.codewise.internship.scheduling;

import java.util.concurrent.atomic.AtomicLong;

public class LongTimerIdSupplier implements TimerIdSupplier {
    /*
     * Long-based identification supplier for tasks
     *
     * For implementation simplicity IDs are not re-used
     * That means after 2^63 - 1 tasks are executed you're out of luck
     *
     * If you think that may happen use another implementation
     * TimerIdException will be thrown when no more IDs are available
     */
    private AtomicLong currentId = new AtomicLong(0);

    @Override
    public TimerId nextId() {
        long id = currentId.incrementAndGet();
        if(id == 0) {
            /* Reached the max number of IDs
             *
             * Consider using another TimerId implementation
             * if you think this may happen
             */
            throw new TimerIdException("Maximum ID reached");
        }
        return new LongTimerId(id);}
}
