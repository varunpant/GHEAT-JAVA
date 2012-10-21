package JavaHeatMaps;

import gheat.datasources.DataManager;
import gheat.datasources.DataSource;
import gheat.datasources.FileDataSource;
import gheat.graphics.ThemeManager;
import org.eclipse.jetty.server.Server;

import java.net.URL;

/**
 *   ________    ___ ______________   ________________
 /  _____/   /   |   \_   _____/  /  _  \__    ___/
 /   \  ___  /    ~    \    __)_  /  /_\  \|    |
 \    \_\  \ \    Y    /        \/    |    \    |
 \______  /  \___|_  /_______  /\____|__  /____|
 \/         \/        \/         \/
 */
public class App {
    public static DataManager dataManager = null;
    final static String query = "SELECT ST_AsText(\"wkb_geometry\") as geom ,\"offences\" as weight FROM crimedata WHERE \"wkb_geometry\" @ ST_MakeEnvelope(?,?,?,?,4326)";

    public static void main(String[] args) throws Exception {
        if (dataManager == null) {
            URL classpathResource = Thread.currentThread().getContextClassLoader().getResource("");

            ThemeManager.init(classpathResource.getPath() + "res/etc/");

             DataSource dataSource = new FileDataSource(classpathResource.getPath() + "points.txt");
         //  DataSource dataSource = new PostGisDataSource("jdbc:postgresql://localhost/Crime", "postgres", "postgres", query);
            dataManager = new DataManager(dataSource);


            System.out.println("======================================= Initialised =======================================");
        }

        Server server = new Server(8080);
        server.setHandler(new TileHandler());

        server.start();
        server.join();
    }
}
