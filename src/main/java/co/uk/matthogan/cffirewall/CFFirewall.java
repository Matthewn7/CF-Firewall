package co.uk.matthogan.cffirewall;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

/**
 * CF-Firewall
 * Add the latest CloudFlare IP ranges to your IPTables firewall
 *
 * @author Matthew Hogan
 */
public class CFFirewall {

    private static final String CF_IPV4_URL;
    private static final String FIREWALL_CMD;

    static {
        CF_IPV4_URL = "https://www.cloudflare.com/ips-v4/";
        FIREWALL_CMD  =  "iptables -I INPUT -p tcp -m multiport --dports 80,443 -s %s -j ACCEPT -m comment --comment \"CloudFlare IP\"";
    }

    /**
     * @param args CLI args
     */
    public static void main(String... args) throws IOException, InterruptedException {

        // Download the latest IP ranges from the CloudFlare provided page
        Scanner scanner = new Scanner(new URL(CF_IPV4_URL).openStream(), "UTF-8").useDelimiter("A");
        String text = Jsoup.parse(scanner.next()).body().text();
        scanner.close();

        for (String ipRange : text.split("\\r?\\n")) {

            String command = String.format(Locale.ENGLISH, FIREWALL_CMD, ipRange);
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

            // Output the command for debugging
            System.out.println(command);

            // Fixes a bug where not all of the firewall commands were being executed correctly
            Thread.sleep(100);
        }
    }
}
