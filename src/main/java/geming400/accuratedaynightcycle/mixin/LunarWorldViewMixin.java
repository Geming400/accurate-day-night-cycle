package geming400.accuratedaynightcycle.mixin;

import geming400.accuratedaynightcycle.AccurateDaynightCycle;
import net.minecraft.world.dimension.DimensionType;
import org.shredzone.commons.suncalc.MoonIllumination;
import org.shredzone.commons.suncalc.MoonPhase;
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

        MoonIllumination moonIllumination = MoonIllumination.compute()
                .on(zonedDateTime)
                .at(AccurateDaynightCycle.CONFIG.latitude(), AccurateDaynightCycle.CONFIG.longitude())
                .execute();

        return MoonPhase.Phase.FULL_MOON.ordinal();
    }
}
