package gHeat;


import org.eclipse.jetty.server.Server;

public class App {


    public static PointManager pointManager = null;

    public static void main(String[] args) throws Exception {

        if (pointManager == null) {
            GHeat.init();
            pointManager = new PointManager();
            pointManager.LoadPointsFromFile("E:\\GIS\\GHeat.Net\\points.txt");
        }

        Server server = new Server(8080);
        server.setHandler(new TileHandler());

        server.start();
        server.join();
    }
}

