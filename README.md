# CF-Firewall

Add the latest CloudFlare IP ranges to your IPTables firewall.

This program <b>does not</b> add the drop rules!! You will need to drop traffic to port 80 and 433 yourself, for example:<br>
`iptables -A INPUT -p tcp -m multiport --dports 80,443 -j DROP -m comment --comment "DROP HTTP/S"`

### Dependancies

* [Jsoup](https://jsoup.org/) - [(Maven)](https://mvnrepository.com/artifact/org.jsoup/jsoup/1.8.3)

<i>All non-native dependancies are shaded when you build the project.</i><br>
<i>[Shaded JAR Download](https://github.com/Matthewn7/CF-Firewall/releases/tag/1.0-SNAPSHOT)</i>

### Building

You will need to install [Apache Maven](https://maven.apache.org/) to build this project. You can use the `build.sh` script to build <i>and</i> run this software, the script will also create a bin directory in the project root, inside will be the compiled jar. Alternatively, if you wish to just build the project without running it: `mvn clean install`

### TODO

* CloudFlare IPv6 firewall rules
