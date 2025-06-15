package geming400.accuratedaynightcycle.moonphases;

import geming400.accuratedaynightcycle.AccurateDaynightCycle;
import org.shredzone.commons.suncalc.MoonIllumination;
import org.shredzone.commons.suncalc.MoonPhase;

import java.time.ZonedDateTime;

public class MoonPhases {
    public static MinecraftMoonPhases getMoonPhase(ZonedDateTime dateTime) {
        MoonIllumination moonIllumination = MoonIllumination.compute()
                .on(dateTime)
                .timezone(dateTime.getZone())
                .at(AccurateDaynightCycle.CONFIG.latitude(), AccurateDaynightCycle.CONFIG.longitude())
                .execute();

        return MinecraftMoonPhases.fromSuncalcMoonPhase(
                MoonPhase.Phase.toPhase(moonIllumination.getPhase())
        );
    }

    public static long getMCmoonPhaseOffset(ZonedDateTime dateTime) {
        final MinecraftMoonPhases minecraftMoonPhase = getMoonPhase(dateTime);

        final int minecraftMoonPhaseLenght = 24000;
        int minecraftMoonPhaseFactor = 0;

        if (minecraftMoonPhase != null) {
            minecraftMoonPhaseFactor = minecraftMoonPhase.ordinal();
        }

        return minecraftMoonPhaseFactor * minecraftMoonPhaseLenght;
    }
}
