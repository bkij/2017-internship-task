package pl.codewise.internship;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.codewise.internship.scheduling.*;

import java.util.concurrent.atomic.AtomicLong;

public class InternshipTaskTest {
    private AtomicLong counter;
    private Scheduler scheduler;

    @Before
    public void initCounter() {
        counter = new AtomicLong(0);
        scheduler = new CachedThreadPoolScheduler(new LongTimerIdSupplier());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkInvalidArgumentThrows() {
        scheduler.start(-1, () -> {});
    }

    @Test
    public void checkOneTaskCallsBack() throws InterruptedException {
        scheduler.start(2, () -> counter.incrementAndGet());
        Thread.sleep(2500);
        Assert.assertEquals(1, counter.get());
    }

    @Test
    public void checkNoCallbackAfterStop() throws InterruptedException {
        TimerId timerId = scheduler.start(5, () -> counter.incrementAndGet());
        Thread.sleep(500);
        scheduler.stop(timerId);
        Assert.assertEquals(0, counter.get());
    }

    @Test
    public void checkMultipleCallbacks() throws InterruptedException {
        for(int i = 0; i < 20; i++) {
            scheduler.start(1, () -> counter.incrementAndGet());
        }
        Thread.sleep(1200);
        Assert.assertEquals(20, counter.get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkInvalidTimerIdThrows() {
        scheduler.start(5, () -> {});
        scheduler.stop(new LongTimerId(10L));
    }
}
