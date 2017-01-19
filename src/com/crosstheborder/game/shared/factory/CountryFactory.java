package com.crosstheborder.game.shared.factory;

import com.crosstheborder.game.shared.component.graphical.countrygraphics.MexicoCountryGraphics;
import com.crosstheborder.game.shared.component.graphical.countrygraphics.USACountryGraphics;
import com.crosstheborder.game.shared.component.physical.MexicoCountryPhysical;
import com.crosstheborder.game.shared.component.physical.USACountryPhysical;
import com.crosstheborder.game.shared.util.enumeration.CrossTheBorderCountryTag;
import com.sstengine.country.Country;

/**
 * @author Oscar de Leeuw
 * @author guillaime
 */
public class CountryFactory {

    MexicoCountryPhysical mexicoCountryPhysical = new MexicoCountryPhysical();
    MexicoCountryGraphics mexicoCountryGraphics = new MexicoCountryGraphics();

    USACountryPhysical usaCountryPhysical = new USACountryPhysical();
    USACountryGraphics usaCountryGraphics = new USACountryGraphics();

    /**
     * @return The Country of Mexico
     */
    public Country createMexico(){
        return new Country(mexicoCountryPhysical, mexicoCountryGraphics, CrossTheBorderCountryTag.MEX);
    }

    /**
     * @return The Country of America
     */
    public Country createUSA(){
        return new Country(usaCountryPhysical, usaCountryGraphics, CrossTheBorderCountryTag.USA);
    }
}
