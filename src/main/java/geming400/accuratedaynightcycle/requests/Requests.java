package geming400.accuratedaynightcycle.requests;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@SuppressWarnings("unused")
public class Requests {
    public static String get(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        return br.readLine();
    }

    public static String get(URI uri) throws IOException {
        URL _url = uri.toURL();

        return get(_url);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static String get(String url) throws IOException, URISyntaxException {
        URL _url = new URI(url).toURL();

        return get(_url);
    }

    // WRITE

    public static void getWrite(URL url, String path) throws IOException {
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(path);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public static void getWrite(URI uri, String path) throws IOException {
        getWrite(uri.toURL(), path);
    }

    public static void getWrite(String url, String path) throws IOException, URISyntaxException {
        getWrite(new URI(url).toURL(), path);
    }
}
