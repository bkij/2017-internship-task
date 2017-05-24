package pl.codewise.internship.scheduling;

public interface Scheduler {
    /*
     * Timeout in seconds after running
     * the start method
     */
    public Long start(long timeout, Runnable callback);
    public void stop(Long timerId);
}
