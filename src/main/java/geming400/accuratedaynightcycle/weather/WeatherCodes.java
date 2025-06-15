package geming400.accuratedaynightcycle.weather;

import geming400.accuratedaynightcycle.range.NullRange;
import geming400.accuratedaynightcycle.range.Range;

@SuppressWarnings("unused")
public final class WeatherCodes {
    final public static Range CLEAR_SKY = new Range(0, 3);
    final public static Range FOGGY = new Range(45, 48);
    final public static Range DRIZZLY = new Range(51, 57);
    final public static Range RAINY = new Range(61, 67);
    final public static Range SNOWY = new Range(71, 77);
    final public static Range RAIN_SHOWERY = new Range(80, 82);
    final public static Range THUNDERSTORMY = new Range(95, 99);

    public static Range toMinecraftWeatherCode(Range weatherCode) {
        if (WeatherCodes.RAINY.contains(weatherCode)) {
            return WeatherCodes.RAINY;
        } else if (weatherCode.in(new Range(0, 99))) {
            return weatherCode;
        }

        return new NullRange();
    }
}