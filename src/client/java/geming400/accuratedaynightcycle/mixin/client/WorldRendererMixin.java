package geming400.accuratedaynightcycle.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldRenderer.class)
abstract class WorldRendererMixin {
    @Shadow @Final private DefaultFramebufferSet framebufferSet;

    @Shadow @Final private SkyRendering skyRendering;

    @Shadow private @Nullable ClientWorld world;

    @Shadow @Final private BufferBuilderStorage bufferBuilders;

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract boolean isSkyDark(float tickProgress);

    @Shadow protected abstract boolean hasBlindnessOrDarkness(Camera camera);

    /**
     * @author h
     * @reason h
     */
    @Overwrite
    private void renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, Fog fog) {
        //MatrixStack matrices = new MatrixStack();
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        if (cameraSubmersionType != CameraSubmersionType.POWDER_SNOW && cameraSubmersionType != CameraSubmersionType.LAVA && !this.hasBlindnessOrDarkness(camera)) {
            DimensionEffects dimensionEffects = this.world.getDimensionEffects();
            DimensionEffects.SkyType skyType = dimensionEffects.getSkyType();
            if (skyType != DimensionEffects.SkyType.NONE) {
                FramePass framePass = frameGraphBuilder.createPass("sky");
                this.framebufferSet.mainFramebuffer = framePass.transfer(this.framebufferSet.mainFramebuffer);
                framePass.setRenderer(() -> {
                    RenderSystem.setShaderFog(fog);
                    if (skyType == DimensionEffects.SkyType.END) {
                        this.skyRendering.renderEndSky();
                    } else {
                        MatrixStack matrixStack = new MatrixStack();
                        float g = this.world.getSkyAngleRadians(tickProgress);
                        float h = this.world.getSkyAngle(tickProgress);
                        float i = 1.0F - this.world.getRainGradient(tickProgress);
                        float j = this.world.getStarBrightness(tickProgress) * i;
                        int k = dimensionEffects.getSkyColor(h);
                        int l = this.world.getMoonPhase();
                        int m = this.world.getSkyColor(this.client.gameRenderer.getCamera().getPos(), tickProgress);
                        float n = ColorHelper.getRedFloat(m);
                        float o = ColorHelper.getGreenFloat(m);
                        float p = ColorHelper.getBlueFloat(m);
                        this.skyRendering.renderTopSky(n, o, p);
                        VertexConsumerProvider.Immediate immediate = this.bufferBuilders.getEntityVertexConsumers();
                        if (dimensionEffects.isSunRisingOrSetting(h)) {
                            this.skyRendering.renderGlowingSky(matrixStack, immediate, g, k);
                        }

                        this.skyRendering.renderCelestialBodies(matrixStack, immediate, h, l, i, j, fog);
                        immediate.draw();
                        if (this.isSkyDark(tickProgress)) {
                            this.skyRendering.renderSkyDark();
                        }
                    }
                });
            }
        }
    }
}
