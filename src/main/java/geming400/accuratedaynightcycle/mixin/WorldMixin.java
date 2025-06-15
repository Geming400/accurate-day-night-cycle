package geming400.accuratedaynightcycle.mixin;

import geming400.accuratedaynightcycle.AccurateDaynightCycle;
import geming400.accuratedaynightcycle.moonphases.MoonPhases;
import net.minecraft.world.World;
import org.shredzone.commons.suncalc.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

import static geming400.accuratedaynightcycle.moonphases.MoonPhases.getMCmoonPhaseOffset;

@Mixin(World.class)
public abstract class WorldMixin {
    @Unique
    private int iter = 0;

    @Unique
    private long lastTime = 0;

    @Unique
    private int localTimeToSeconds(LocalTime time) {
        return time.getHour() * 60 * 60
                        + time.getMinute() * 60
                        + time.getSecond();
    }
    @Unique
    private int localTimeToSeconds(ZonedDateTime time) {
        return localTimeToSeconds(time.toLocalTime());
    }

    @Unique
    private long timeToMcTime(LocalTime time) {
        // This is the simplified formula
        // Original one that I found:
        // 24000 - ((localTimeToSeconds(time) / 86400) * 24000 + 6000
        return timeToMcTime(localTimeToSeconds(time));
    }
    @Unique
    private long timeToMcTime(ZonedDateTime time) {
        // This is the simplified formula
        // Original one that I found:
        // 24000 - ((localTimeToSeconds(time) / 86400) * 24000 + 6000
        return timeToMcTime(localTimeToSeconds(time));
    }
    @Unique
    private long timeToMcTime(long time) {
        // This is the simplified formula
        // Original one that I found:
        // 24000 - ((time / 86400) * 24000 + 6000
        return -(5L * time) / 18  +  18000;
    }

    @Unique
    private boolean isTimeZoneSet() {
        return !(Objects.equals(AccurateDaynightCycle.CONFIG.gmtTimeZone(), "") || Objects.equals(AccurateDaynightCycle.CONFIG.gmtTimeZone(), "auto"));
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

        ZoneId zoneId;
        ZonedDateTime zonedDateTime;

        if (isTimeZoneSet()) {
            zoneId = ZoneOffset.of(AccurateDaynightCycle.CONFIG.gmtTimeZone());
        } else {
            zoneId = ZoneOffset.systemDefault();
        }

        zonedDateTime = ZonedDateTime.now(zoneId);
        zonedDateTime = ZonedDateTime.now(zoneId);

        long MCmoonOffset = getMCmoonPhaseOffset(zonedDateTime);

        if (AccurateDaynightCycle.CONFIG.useTimeLibrary()) {
            lastTime = timeToMcTime(zonedDateTime);
            return lastTime + MCmoonOffset;
        } else {
            SunTimes sunTimes = SunTimes.compute()
                    .on(zonedDateTime)
                    .timezone(zoneId)
                    .at(AccurateDaynightCycle.CONFIG.latitude(), AccurateDaynightCycle.CONFIG.longitude())
                    .execute();

            long mcTime = lastTime;

            if (sunTimes.getNoon() != null) {
                // 43200 = 24H/2
                double timeFactor = (double) localTimeToSeconds(zonedDateTime) / localTimeToSeconds(sunTimes.getNoon());
                mcTime = timeToMcTime(
                        (long) (timeFactor * 43200L)
                );

                //AccurateDaynightCycle.LOGGER.info(String.valueOf(mcTime));
            }

            lastTime = (mcTime + MCmoonOffset);
            return lastTime;
        }
    }
}
