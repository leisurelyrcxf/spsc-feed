package org.bullish.market.data;

import lombok.Getter;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author sadtheslayer
 */
public class MarketDataFeed<Q extends Queue<Tick>> {
    @Getter
    private final Q q;
    private final Thread thread;

    @Getter
    private double min = Double.MAX_VALUE;
    @Getter
    private double max = 0.0;
    @Getter
    private double current = -1.0;

    private volatile boolean parked = false;
    private final CountDownLatch stopped = new CountDownLatch(1);

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public MarketDataFeed(Q q) {
        this.q = q;
        this.thread = new Thread(this::consume, "consumer");
    }

    public void start() {
        this.thread.start();
    }

    private void consume() {
        outer:
        while (true) {
            Tick tick = q.poll();
            if (tick == null) {
                do {
                    parked = true;
                    try {
                        tick = q.poll();
                        if (tick != null) {
                            break;
                        }
                        // q.poll() == null -> engine[q.offer()] -> engine[feed.isParked() == true] -> engine[feed.unpark]
                        LockSupport.park();
                        continue outer;
                    } finally {
                        parked = false;
                    }
                } while (false);
            }
            if (tick == MarketDataEngine.EOF) {
                break;
            }
            update(tick);
        }
        stopped.countDown();
    }

    void update(Tick tick) {
        final double price = tick.getPrice();
        if (price > max) {
            max = price;
        }
        if (price < min) {
            min = price;
        }
        current = price;
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void unpark() {
        LockSupport.unpark(thread);
    }

    public boolean isParked() {
        return parked;
    }

    public void waitStopped(long timeoutInSeconds) {
        try {
            if (!stopped.await(timeoutInSeconds, TimeUnit.SECONDS)) {
                throw new RuntimeException("failed to stop");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
