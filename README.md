# CF-Firewall

Add the latest CloudFlare IP ranges to your IPTables firewall.

This program <b>does not</b> add the drop rules!! You will need to block traffic to port 80 and 433 yourself, for example:<br>
`iptables -A INPUT -p tcp -m multiport --dports 80,443 -j DROP -m comment --comment "DROP HTTP/S"`

### Dependancies

<i>All non-native dependancies are shaded</i>

* Jsoup

### Building

You can use the `build.sh` script to build <i>and</i> run this software. Alternatively, if you wish to just build the project without running it: `mvn clean install`. 
