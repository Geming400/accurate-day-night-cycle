package geming400.accuratedaynightcycle;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.ExcludeFromScreen;
import io.wispforest.owo.config.annotation.Modmenu;

@SuppressWarnings("unused")
@Modmenu(modId = AccurateDaynightCycle.MOD_ID)
@Config(name = "adnc-configs", wrapperName = "AccurateDaynightCycleConfigs")
public class ConfigsModel {
    //public boolean useTimeLibrary = true; // TODO: Remove that on publish
    public boolean useTimeLibrary = false;
    public int calculateEachXticks = 20;
    public String maxmindDBlink = "https://github.com/P3TERX/GeoLite.mmdb/releases/latest/download/GeoLite2-City.mmdb";
    public String gmtTimeZone = "auto";

    @ExcludeFromScreen
    public String ipAddress = "DO NOT MODIFY - NOT SET";

    @ExcludeFromScreen
    public double latitude = 0f;

    @ExcludeFromScreen
    public double longitude = 0f;
}
