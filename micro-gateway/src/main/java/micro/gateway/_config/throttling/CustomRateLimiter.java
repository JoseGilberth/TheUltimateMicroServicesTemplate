package micro.gateway._config.throttling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CustomRateLimiter {

	private Semaphore semaphore;
	private int maxPermits;
	private TimeUnit timePeriod;
	private int period;
	private ScheduledExecutorService scheduler;

	public static CustomRateLimiter create(int permits, int period, TimeUnit timePeriod) {
		CustomRateLimiter limiter = new CustomRateLimiter(permits, period, timePeriod);
		limiter.schedulePermitReplenishment();
		return limiter;
	}

	private CustomRateLimiter(int permits, int period, TimeUnit timePeriod) {
		this.semaphore = new Semaphore(permits);
		this.maxPermits = permits;
		this.period = period;
		this.timePeriod = timePeriod;
	}

	public boolean tryAcquire() {
		return semaphore.tryAcquire();
	}

	public void stop() {
		scheduler.shutdownNow();
	}

	public void schedulePermitReplenishment() {
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(() -> {
			semaphore.release(maxPermits - semaphore.availablePermits());
		}, 0, period, timePeriod);
	}

	public TimeUnit getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimeUnit timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public int getMaxPermits() {
		return maxPermits;
	}

	public void setMaxPermits(int maxPermits) {
		this.maxPermits = maxPermits;
	}

	public ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	public void setScheduler(ScheduledExecutorService scheduler) {
		this.scheduler = scheduler;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
