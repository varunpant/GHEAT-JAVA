package JavaHeatMaps;

import gheat.datasources.DataManager;
import gheat.datasources.DataSource;
import gheat.datasources.PostGisDataSource;
import gheat.graphics.ThemeManager;
import org.eclipse.jetty.server.Server;

import java.net.URL;

/**
 * Hello world!
 */
public class App {
    public static DataManager dataManager = null;

    public static void main(String[] args) throws Exception {
        if (dataManager == null) {
            URL classpathResource = Thread.currentThread().getContextClassLoader().getResource("");

            ThemeManager.init(classpathResource.getPath() + "res/etc/");
            // DataSource dataSource = new FileDataSource(classpathResource.getPath() + "points.txt");
            DataSource dataSource = new PostGisDataSource("jdbc:postgresql://localhost/Crime", "postgres", "postgres");
            dataManager = new DataManager(dataSource);


            System.out.println("======================================= Initialised =======================================");
        }

        Server server = new Server(8080);
        server.setHandler(new TileHandler());

        server.start();
        server.join();
    }
}
