package pl.codewise.internship.scheduling;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CachedThreadPoolScheduler implements Scheduler {
    private ConcurrentHashMap<TimerId, Future> tasks;
    private ExecutorService executorService;
    private TimerIdSupplier timerIdSupplier;

    public CachedThreadPoolScheduler(TimerIdSupplier timerIdSupplier) {
        this.tasks = new ConcurrentHashMap<>();
        this.executorService = Executors.newCachedThreadPool();
        this.timerIdSupplier = timerIdSupplier;
    }

    @Override
    public TimerId start(long expirationTime, Runnable callback) {
        checkValidity(expirationTime, callback);
        TimerId taskId = timerIdSupplier.nextId();
        Future task = executorService.submit(() -> {
            try {
                Thread.sleep(expirationTime);
            } catch(InterruptedException ex) {
                return;
            }
            executorService.submit(callback);
        });
        tasks.put(taskId, task);
        return taskId;
    }

    @Override
    public void stop(TimerId timerId) {
        Optional<Future> maybeTask = Optional.ofNullable(tasks.get(timerId));
        Future task = maybeTask.orElseThrow(() -> new IllegalArgumentException("No such TimerID"));
        if(!task.cancel(true)) {
            //TODO: Log failure?
        }
        tasks.remove(timerId);
    }

    private void checkValidity(long expirationTime, Runnable callback) {
        if(expirationTime < 0) {
            throw new IllegalArgumentException("Negative expiration time");
        }
        if(callback == null) {
            throw new IllegalArgumentException("Null callback argument");
        }
    }
}
