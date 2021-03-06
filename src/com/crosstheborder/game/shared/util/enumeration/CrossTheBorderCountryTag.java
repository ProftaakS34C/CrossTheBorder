package com.crosstheborder.game.shared.util.enumeration;

import com.sstengine.country.CountryTag;

/**
 * @author guillaime
 */
public enum CrossTheBorderCountryTag implements CountryTag<CrossTheBorderCountryTag> {
    MEX, USA;

    @Override
    public CrossTheBorderCountryTag getTag() {
        return this;
    }
}
