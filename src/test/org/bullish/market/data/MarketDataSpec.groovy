package org.bullish.market.data

import spock.lang.Specification

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicBoolean

class MarketDataSpec extends Specification {

    static class TestData extends Tick {
        double max, min, current

        TestData(String market, double price, max, min, current) {
            super(market, price)
            this.max = max
            this.min = min
            this.current = current
        }
    }

    def "test case 1"() {
        given:
        Queue<TestData> testDatas = new ConcurrentLinkedQueue<>()
        AtomicBoolean testFailed = new AtomicBoolean()
        Queue<Tick> q = new ConcurrentLinkedQueue<>()
        MarketDataFeed marketDataFeed = new MarketDataFeed(q) {
            @Override
            void update(Tick tick) {
                super.update(tick)

                TestData testData = testDatas.poll()
                if (testData == null) {
                    return
                }
                if (testData.getMax() != getMax()) {
                    testFailed.set(true)
                }
                if (testData.getMin() != getMin()) {
                    testFailed.set(true)
                }
                if (testData.getCurrent() != getCurrent()) {
                    testFailed.set(true)
                }
                System.out.println("matches")
            }
        }

        double minPrice = Double.MAX_VALUE
        double maxPrice = 0.0
        MarketDataEngine marketDataEngine = new MarketDataEngine(marketDataFeed) {
            @Override
            Tick createTick() {
                double price = ThreadLocalRandom.current().nextDouble(0, 100)
                if (price > maxPrice) {
                    maxPrice = price
                }
                if (price < minPrice) {
                    minPrice = price
                }
                testDatas.offer(new TestData("usd", price, maxPrice, minPrice, price))
                return new Tick("usd", price)
            }
        }

        when:
        marketDataEngine.start()
        marketDataFeed.start()
        try {
            Thread.sleep(100)
        } catch (InterruptedException e) {
            throw new RuntimeException(e)
        }
        marketDataEngine.stop()
        marketDataFeed.waitStopped(10)
        marketDataEngine.waitStopped(10)

        then:
        !testFailed.get()
    }
}
