package gHeat;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GHeat {


    public static final int SIZE = 256; // # size of (square) tile; NB: changing this will break gmerc calls!
    public static final int MAX_ZOOM = 31; // # this depends on Google API; 0 is furthest out as of recent ver.

    public static final String DOTS_FOLDER = "dots";

    public static final String COLOR_SCHMES_FOLDER = "color-schemes";

    private static Map<String, BufferedImage> dotsList;

    private static Map<String, BufferedImage> colorSchemeList;

    private static Object _instanceLocker = new Object();

    public static void init() throws IOException {
        String directory = "C:\\Users\\va008pa\\Desktop\\gHeat\\res\\etc";
        dotsList = new HashMap<String, BufferedImage>();
        colorSchemeList = new HashMap<String, BufferedImage>();

        for (File file : new File(directory, DOTS_FOLDER).listFiles()) {
            if (file.getName().toLowerCase().endsWith(".png"))
                dotsList.put(file.getName(), ImageIO.read(file));
        }

        for (File file : new File(directory, COLOR_SCHMES_FOLDER).listFiles()) {
            if (file.getName().toLowerCase().endsWith(".png"))
                colorSchemeList.put(file.getName(), ImageIO.read(file));
        }

    }

    public static BufferedImage GetTile(PointManager pm, String colorScheme, int zoom, int x, int y, Boolean newMethod, Boolean changeOpacityWithZoom, int defaultOpacity) throws Exception {
        //Do a little error checking
        if (colorScheme == "") throw new Exception("A color scheme is required");
        if (pm == null) throw new Exception("No point manager has been specified");

        return Tile.Generate(GetColorScheme(colorScheme),
                GetDot(zoom),
                zoom,
                x,
                y,
                pm.GetPointsForTile(x, y, GetDot(zoom), zoom, newMethod), changeOpacityWithZoom, defaultOpacity);
    }

    public static BufferedImage GetTile(PointManager pm, String colorScheme, int zoom, int x, int y) throws Exception {
        return GetTile(pm, colorScheme, zoom, x, y, false, true, 0);
    }

    private static BufferedImage GetDot(int zoom) {
        return dotsList.get("dot" + zoom + ".png");
    }
    public static BufferedImage GetColorScheme(String schemeName) throws Exception {
        if (!colorSchemeList.containsKey(schemeName + ".png"))
            throw new Exception("Color scheme '" + schemeName + " could not be found");
        return colorSchemeList.get(schemeName + ".png");
    }


}
