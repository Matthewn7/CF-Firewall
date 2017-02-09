package co.uk.matthogan.cffirewall;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

/**
 * CF-Firewall
 * Add the latest CloudFlare IPv4 & IPv6 ranges to your firewall
 *
 * @author Matthew Hogan
 */
public class CFFirewall {

    private static final String CF_IPV4_URL;
    private static final String CF_IPV6_URL;
    private static final String FIREWALL_CMD;

    static {
        CF_IPV4_URL = "https://www.cloudflare.com/ips-v4/";
        CF_IPV6_URL = "https://www.cloudflare.com/ips-v6/";
        FIREWALL_CMD = "iptables -I INPUT -p tcp -m multiport --dports 80,443 -s %s -j ACCEPT -m comment --comment \"CloudFlare IP\"";
    }

    /**
     * @param args ipv4 or ipv6
     */
    public static void main(String... args) throws IOException, InterruptedException {

        if (args.length < 1 || args.length > 1 || (!args[0].equals("ipv4") && !args[0].equals("ipv6"))) {

            System.out.println("Please specify either \"ipv4\" or \"ipv6\"");
            return;
        }

        boolean ipv4 = args[0].equals("ipv4");

        // Download the latest IP ranges from the CloudFlare provided page
        Scanner scanner = new Scanner(new URL(ipv4 ? CF_IPV4_URL : CF_IPV6_URL).openStream(), "UTF-8").useDelimiter("A");

        String ipRanges = Jsoup.parse(scanner.next()).body().text();
        scanner.close();

        for (String ipRange : ipRanges.split(" ")) {

            String formattedCommand = String.format(Locale.ENGLISH, FIREWALL_CMD, ipRange);
            String command = (ipv4 ? formattedCommand : formattedCommand.replace("iptables", "ip6tables"));

            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

            // Output the command for debugging
            System.out.println(command);

            // Fixes a bug where not all of the firewall commands were being executed correctly
            Thread.sleep(100);
        }
    }
}
