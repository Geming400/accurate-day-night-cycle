package geming400.accuratedaynightcycle.mixin;

import geming400.accuratedaynightcycle.AccurateDaynightCycle;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.shredzone.commons.suncalc.MoonIllumination;
import org.shredzone.commons.suncalc.SunTimes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Mixin(World.class)
public abstract class WorldMixin {
    /*
        a mc day ∈ [0, 1200] ticks
    */
    @Unique
    final double mcDayLenghtFactor = (double) 180 / 432;

    @Unique
    int iter = 0;

    @Unique
    long lastTime = 0;

    @Unique
    private int localTimeToSecond(LocalTime time) {
        return time.getHour() * 60 * 60
                + time.getMinute() * 60
                + time.getSecond();
    }

    @Unique
    private long timeToTick(LocalTime time) {
        /*
        localTimeToSecond(time) ∈ [0, 86400]
         */
        return (long) -(localTimeToSecond(time) * mcDayLenghtFactor);
    }

    @Unique
    private boolean isTimeZoneSet() {
        return Objects.equals(AccurateDaynightCycle.CONFIG.gmtTimeZone(), "") || Objects.equals(AccurateDaynightCycle.CONFIG.gmtTimeZone(), "auto");
    }

    /**
     * @author geming400
     * @reason need to change the returned value
     */
    @Overwrite
    public long getTimeOfDay() {
        if (iter < AccurateDaynightCycle.CONFIG.calculateEachXticks()) {
            iter++;
            return lastTime;
        }

        iter = 0;

        AccurateDaynightCycle.LOGGER.debug("Calculating sun pos");

        if (AccurateDaynightCycle.CONFIG.useTimeLibrary()) {
            LocalTime localTime;
            if (isTimeZoneSet()) {
                localTime = LocalTime.now();
            } else {
                localTime = LocalTime.now(ZoneOffset.of(AccurateDaynightCycle.CONFIG.gmtTimeZone()));
            }

            lastTime = timeToTick(localTime);

            return lastTime;
        } else {
            ZonedDateTime zonedDateTime;

            if (isTimeZoneSet()) {
                zonedDateTime = ZonedDateTime.now();
            } else {
                zonedDateTime = ZonedDateTime.now(ZoneOffset.of(AccurateDaynightCycle.CONFIG.gmtTimeZone()));
            }

            SunTimes sunTimes = SunTimes.compute()
                    .on(zonedDateTime)
                    .at(AccurateDaynightCycle.CONFIG.latitude(), AccurateDaynightCycle.CONFIG.longitude())
                    .execute();



            return lastTime;
        }
    }
}
