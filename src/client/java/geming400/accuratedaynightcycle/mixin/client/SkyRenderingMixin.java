package geming400.accuratedaynightcycle.mixin.client;

import geming400.accuratedaynightcycle.AccurateDaynightCycleClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SkyRendering.class)
abstract class SkyRenderingMixin {
    /**
     * @author h
     * @reason h
     */
    @Overwrite
    public void renderGlowingSky(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, float angleRadians, int color) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
        float f = MathHelper.sin(angleRadians) < 0.0F ? 180.0F : 0.0F;
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getSunriseSunset());
        float g = ColorHelper.getAlphaFloat(color);
        vertexConsumer.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(color);
        int i = ColorHelper.zeroAlpha(color);
        int j = 16;

        for (int k = 0; k <= 16; k++) {
            float h = k * (float) (Math.PI * 2) / 16.0F;
            float l = MathHelper.sin(h);
            float m = MathHelper.cos(h);
            vertexConsumer.vertex(matrix4f, l * 120.0F, m * 120.0F, -m * 40.0F * g).color(i);
        }

        matrices.pop();
    }

    /**
     * @author Geming400
     * @reason testing stuff
     */
    @Overwrite
    private void renderSun(float alpha, VertexConsumerProvider vertexConsumers, MatrixStack matrices) {
        if (AccurateDaynightCycleClient.quaternions  == null ) { return; }
        if (AccurateDaynightCycleClient.quaternions.slider  == null ) { return; }

        /*
        Random rng = Random.create();

        int num = (int) (AccurateDaynightCycleClient.quaternions.slider.value() *
                        (AccurateDaynightCycleClient.quaternions.slider.max() - AccurateDaynightCycleClient.quaternions.slider.min()));

        AccurateDaynightCycle.LOGGER.info("num = {}", num);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getCelestial(Identifier.ofVanilla("textures/environment/sun.png")));
        int color = ColorHelper.getWhite(alpha);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        /*vertexConsumer.vertex(matrix4f,
                        (float) AccurateDaynightCycleClient.quaternions.sliders1.get("x").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders1.get("y").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders1.get("z").value() * 360)
                .texture(0.0F, 0.0F).color(color);
        vertexConsumer.vertex(matrix4f,
                        (float) AccurateDaynightCycleClient.quaternions.sliders2.get("x").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders2.get("y").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders2.get("z").value() * 360)
                .texture(1.0F, 0.0F).color(color);
        vertexConsumer.vertex(matrix4f,
                        (float) AccurateDaynightCycleClient.quaternions.sliders3.get("x").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders3.get("y").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders3.get("z").value() * 360)
                .texture(1.0F, 1.0F).color(color);
        vertexConsumer.vertex(matrix4f,
                        (float) AccurateDaynightCycleClient.quaternions.sliders4.get("x").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders4.get("y").value() * 360,
                        (float) AccurateDaynightCycleClient.quaternions.sliders4.get("z").value() * 360)
                .texture(0.0F, 1.0F).color(color);* /

        vertexConsumer.vertex(matrix4f, getRandom(rng, num), getRandom(rng, num), getRandom(rng, num)).texture(0.0F, 0.0F).color(color);
        vertexConsumer.vertex(matrix4f, getRandom(rng, num), getRandom(rng, num), getRandom(rng, num)).texture(1.0F, 0.0F).color(color);
        vertexConsumer.vertex(matrix4f, getRandom(rng, num), getRandom(rng, num), getRandom(rng, num)).texture(1.0F, 1.0F).color(color);
        vertexConsumer.vertex(matrix4f, getRandom(rng, num), getRandom(rng, num), getRandom(rng, num)).texture(0.0F, 1.0F).color(color);
*/
    }
}
