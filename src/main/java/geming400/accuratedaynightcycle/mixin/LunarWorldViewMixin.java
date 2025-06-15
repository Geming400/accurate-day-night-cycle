package geming400.accuratedaynightcycle.mixin;

import geming400.accuratedaynightcycle.AccurateDaynightCycle;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Mixin(DimensionType.class)
public class LunarWorldViewMixin {
    @Unique
    private boolean isTimeZoneSet() {
        return Objects.equals(AccurateDaynightCycle.CONFIG.gmtTimeZone(), "") || Objects.equals(AccurateDaynightCycle.CONFIG.gmtTimeZone(), "auto");
    }

    /**
     * @author Geming400
     * @reason need to change the returned value
     */
    @Overwrite
    public int getMoonPhase(long time) {
        ZonedDateTime zonedDateTime;

        if (isTimeZoneSet()) {
            zonedDateTime = ZonedDateTime.now();
        } else {
            zonedDateTime = ZonedDateTime.now(ZoneOffset.of(AccurateDaynightCycle.CONFIG.gmtTimeZone()));
        }

        //return MoonPhase.Phase.NEW_MOON.ordinal();
        return geming400.accuratedaynightcycle.moonphases.MoonPhases.getMoonPhase(zonedDateTime).ordinal();
    }
}
