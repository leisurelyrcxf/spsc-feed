package org.bullish.market.data;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;

/**
 * @author sadtheslayer
 */
@AllArgsConstructor
public class Tick {
    @Getter
    private final String market;
    @Getter
    private final double price;
}