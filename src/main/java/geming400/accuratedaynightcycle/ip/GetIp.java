package geming400.accuratedaynightcycle.ip;

import geming400.accuratedaynightcycle.requests.Requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@SuppressWarnings("unused")
public class GetIp {
    public static InetAddress getPublicIp() throws IOException, URISyntaxException {
        return InetAddress.getByName(Requests.get("https://api.ipify.org"));
    }

    public static String getLocalIp() throws UnknownHostException {
        InetAddress localIP = InetAddress.getLocalHost();
        return localIP.getHostAddress();
    }
}
