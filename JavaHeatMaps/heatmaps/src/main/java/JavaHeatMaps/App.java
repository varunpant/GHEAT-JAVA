package JavaHeatMaps;

import gheat.datasources.*;
import gheat.graphics.ThemeManager;
import org.eclipse.jetty.server.Server;

import java.net.URL;

/**
 * ________    ___ ______________   ________________
 * /  _____/   /   |   \_   _____/  /  _  \__    ___/
 * /   \  ___  /    ~    \    __)_  /  /_\  \|    |
 * \    \_\  \ \    Y    /        \/    |    \    |
 * \______  /  \___|_  /_______  /\____|__  /____|
 * \/         \/        \/         \/
 */
public class App {
    public static DataManager dataManager = null;
    static URL classpathResource = ClassLoader.getSystemClassLoader().getResource("//");

    public static void main(String[] args) throws Exception {
        if (dataManager == null) {

            ThemeManager.init(classpathResource.getPath() + "res/etc/");
            HeatMapDataSource dataSource = getFileDataSource();
            // HeatMapDataSource dataSource = getQuadTreeDataSource();
            // HeatMapDataSource dataSource = getPostGisDataSource();

            dataManager = new DataManager(dataSource);
            System.out.println("======================================= Initialised =======================================");
        }

        Server server = new Server(8080);
        server.setHandler(new TileHandler());

        server.start();
        server.join();

    }

    /*
   Gets PostGIS data source.
   * */
    private static PostGisDataSource getPostGisDataSource() {
        //In this query aliases(longitude,latitude,weight) must remain as shown, the actual table name ,geometry column name and weight column name can change .
        String query = "SELECT ST_X(geom) as longitude," +
                "ST_Y(geom) as latitude" +
                "weight as weight from spatialTable where geom @ ST_MakeEnvelope(?,?,?,?,4326)";

        return new PostGisDataSource(query);
    }

    /*
   Gets File data source.
   * */
    private static FileDataSource getFileDataSource() {
        return new FileDataSource(classpathResource.getPath() + "points.txt", 2, 1, 0);
    }

    /*
   Gets quad tree data source. Takes indexes of longitude,latitude and weight columns in the csv file.
   * */
    private static QuadTreeDataSource getQuadTreeDataSource() {
        return new QuadTreeDataSource(classpathResource.getPath() + "points.txt", 2, 1, 0);
    }
}
