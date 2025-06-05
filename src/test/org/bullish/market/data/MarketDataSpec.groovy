package org.bullish.market.data

import spock.lang.Specification

import java.util.concurrent.ConcurrentLinkedQueue

class MarketDataSpec extends Specification {

    def "test case 1"() {
        given:
        Queue<Tick> q = new ConcurrentLinkedQueue<>()
        MarketDataFeed marketDataFeed = new MarketDataFeed(q)
        MarketDataEngine marketDataEngine = new MarketDataEngine(marketDataFeed)

        when:
        marketDataEngine.start()
        marketDataFeed.start()
        try {
            Thread.sleep(0, 10)
        } catch (InterruptedException e) {
            throw new RuntimeException(e)
        }
        marketDataEngine.stop()
        marketDataFeed.waitStopped(10)
        marketDataEngine.waitStopped(10)

        then:
        marketDataFeed.getMax() == marketDataEngine.getPrice()
        marketDataFeed.getMin() == 100.0f
        marketDataFeed.getCurrent() == marketDataEngine.getPrice()
    }
}
