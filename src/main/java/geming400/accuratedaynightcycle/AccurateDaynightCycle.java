package geming400.accuratedaynightcycle;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import geming400.accuratedaynightcycle.ip.GetIp;
import geming400.accuratedaynightcycle.ip.IpGeolocator;
import geming400.accuratedaynightcycle.requests.Requests;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class AccurateDaynightCycle implements ModInitializer {
	public static final String MOD_ID = "accurate-day-night-cycle";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final geming400.accuratedaynightcycle.AccurateDaynightCycleConfigs CONFIG = geming400.accuratedaynightcycle.AccurateDaynightCycleConfigs.createAndLoad();

	@Override
	public void onInitialize() {
		if (Objects.equals(CONFIG.ipAddress(), "DO NOT MODIFY - NOT SET") && !CONFIG.useTimeLibrary()) { // First time launching the game
            try {
				InetAddress ip = GetIp.getPublicIp();

                LOGGER.info("Device's ip: {}", ip.toString());
                CONFIG.ipAddress(ip.toString()); // set the ip address field in the configs to the device's public IP

				final String dbFileName = "GeoLite-city.mmdb";
				final String dbPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), dbFileName).toString();

				LOGGER.debug("Writing db file to {}", dbPath);

				String path = new File(dbPath).getParent();
				Files.createDirectories(Paths.get(path));

				Requests.getWrite(CONFIG.maxmindDBlink(), dbPath);

				IpGeolocator.IpGeolocation location = IpGeolocator.getCoordinatesFromIp(ip, dbPath);

				CONFIG.latitude(location.latitude);
				CONFIG.longitude(location.longitude);
            } catch (IOException | URISyntaxException | GeoIp2Exception e) {
                throw new RuntimeException(e);
            }
        }
	}
}