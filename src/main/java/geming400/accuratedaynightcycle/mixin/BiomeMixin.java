package geming400.accuratedaynightcycle.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Biome.class)
public class BiomeMixin {
    @Unique
    private void t() {

    }

    /**
     * @author Geming400
     * @reason Need to change the returned value
     */
    @Overwrite
    public Biome.Precipitation getPrecipitation(BlockPos pos, int seaLevel) {
        return Biome.Precipitation.RAIN;
    }
}
