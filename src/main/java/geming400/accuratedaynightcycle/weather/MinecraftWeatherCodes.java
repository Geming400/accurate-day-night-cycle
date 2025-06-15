package geming400.accuratedaynightcycle.weather;

import geming400.accuratedaynightcycle.range.NullRange;
import geming400.accuratedaynightcycle.range.Range;
import geming400.accuratedaynightcycle.range.RangeUnion;
import geming400.accuratedaynightcycle.range.RangeUnionBuilder;

@SuppressWarnings("unused")
public final class MinecraftWeatherCodes {
    private static final RangeUnion rainyRange = new RangeUnionBuilder()
            .add(WeatherCodes.RAINY)
            .add(WeatherCodes.RAIN_SHOWERY)
            .get();

    final public static Range CLEAR_SKY = WeatherCodes.CLEAR_SKY;
    final public static Range FOGGY = new Range(45, 57);
    final public static RangeUnion RAINY = rainyRange;
    final public static Range SNOWY = WeatherCodes.SNOWY;
    final public static Range THUNDERSTORMY = WeatherCodes.THUNDERSTORMY;

    public static Range toWeatherCode(Range minecraftWeatherCode) {
        if (minecraftWeatherCode == MinecraftWeatherCodes.CLEAR_SKY) {
            return WeatherCodes.CLEAR_SKY;
        } else if (minecraftWeatherCode == MinecraftWeatherCodes.FOGGY) {
            return WeatherCodes.FOGGY;
        } else if (MinecraftWeatherCodes.RAINY.contains(minecraftWeatherCode)) {
            return WeatherCodes.RAINY;
        } else if (minecraftWeatherCode == MinecraftWeatherCodes.SNOWY) {
            return WeatherCodes.SNOWY;
        } else if (minecraftWeatherCode == MinecraftWeatherCodes.THUNDERSTORMY) {
            return WeatherCodes.THUNDERSTORMY;
        }

        return new NullRange();
    }
}
