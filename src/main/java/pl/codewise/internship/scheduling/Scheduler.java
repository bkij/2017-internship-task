package pl.codewise.internship.scheduling;

public interface Scheduler {
    /*
     * ExpirationTime in seconds after running
     * the start method
     */
    public TimerId start(long expirationTime, Runnable callback);
    public void stop(TimerId timerId);
}
