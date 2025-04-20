package geming400.accuratedaynightcycle.ip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class IpGeolocator {
    public static class IpGeolocation {
        public Double latitude;
        public Double longitude;

        IpGeolocation(Double lat, Double lon) {
            latitude = lat;
            longitude = lon;
        }
    }

    public static IpGeolocation getCoordinatesFromIp(InetAddress ip, String dbPath) throws IOException, GeoIp2Exception {
        DatabaseReader reader = new DatabaseReader.Builder(new File(dbPath)).build();

        CityResponse response = reader.city(ip);

        return new IpGeolocation(
                response.getLocation().getLatitude(),
                response.getLocation().getLongitude()
        );
    }
}
