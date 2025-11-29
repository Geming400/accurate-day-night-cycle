package geming400.accuratedaynightcycle.devui;

import geming400.accuratedaynightcycle.AccurateDaynightCycle;
import io.wispforest.owo.ui.base.BaseUIModelScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.DiscreteSliderComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Sizing;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class QuaternionSlidersUI extends BaseUIModelScreen<FlowLayout> {
    public DiscreteSliderComponent slider = null;

    public QuaternionSlidersUI() {
        super(FlowLayout.class, DataSource.asset(Identifier.of(AccurateDaynightCycle.MOD_ID, "dev_ui_model")));
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        slider = rootComponent.childById(DiscreteSliderComponent.class, "random-value-slider");
    }
}
