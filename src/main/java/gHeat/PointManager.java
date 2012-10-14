package gHeat;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PointManager {

    List<PointLatLng> _pointList;
    private Projections _projection = new MercatorProjection();
    WeightHandler _weightHandler;

    public PointManager() {
        this(null);
    }


    public PointManager(WeightHandler weightHandle) {
        _pointList = new ArrayList<PointLatLng>();
        _weightHandler = weightHandle;
    }

    public void LoadPointsFromFile(String source) {
        String[] item;
        String[] lines = readAllTextFileLines(source);
        for (String line : lines) {
            item = line.split(",");
            _pointList.add(new PointLatLng(Double.parseDouble(item[1]), Double.parseDouble(item[2])));
        }
    }

    private static String[] readAllTextFileLines(String fileName) {
        StringBuilder sb = new StringBuilder();

        try {
            String textLine;

            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((textLine = br.readLine()) != null) {
                sb.append(textLine);
                sb.append('\n');
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (sb.length() == 0)
                sb.append("\n");
        }
        return sb.toString().split("\n");
    }


    public DataPoint[] GetPointsForTile(int x, int y, BufferedImage dot, int zoom, boolean newMethod) throws InterruptedException {
        List<DataPoint> points = new ArrayList<DataPoint>();
        Size maxTileSize;
        DataPoint adjustedDataPoint;
        DataPoint pixelCoordinate;
        DataPoint mapDataPoint;


        //Top Left Bounds
        DataPoint tlb = _projection.fromTileXYToPixel(new DataPoint(x, y));

        maxTileSize = new Size(GHeat.SIZE, GHeat.SIZE);
        //Lower right bounds
        DataPoint lrb = new DataPoint((tlb.getX() + maxTileSize.getWidth()) + dot.getWidth(), (tlb.getY() + maxTileSize.getHeight()) + dot.getWidth());

        //pad the Top left bounds
        tlb = new DataPoint(tlb.getX() - dot.getWidth(), tlb.getY() - dot.getHeight());


        //Go throught the list and convert the points to pixel cooridents
        for (PointLatLng llDataPoint : GetList(tlb, lrb, zoom, newMethod)) {
            //Now go through the list and turn it into pixel points
            pixelCoordinate = _projection.fromLatLngToPixel(llDataPoint.getLatitude(), llDataPoint.getLongitude(), zoom);

            //Make sure the weight and data is still pointing after the conversion
            pixelCoordinate.setData(llDataPoint.getData());
            pixelCoordinate.setWeight(llDataPoint.getWeight());

            mapDataPoint = _projection.fromPixelToTileXY(pixelCoordinate);
            mapDataPoint.setData(pixelCoordinate.getData());

            //Adjust the point to the specific tile
            adjustedDataPoint = AdjustMapPixelsToTilePixels(new DataPoint(x, y), pixelCoordinate);

            //Make sure the weight and data is still pointing after the conversion
            adjustedDataPoint.setData(pixelCoordinate.getData());
            adjustedDataPoint.setWeight(pixelCoordinate.getWeight());

            //Add the point to the list
            points.add(adjustedDataPoint);
        }

        return points.toArray(new DataPoint[points.size()]);
    }

    protected PointLatLng[] GetList(DataPoint tlb, DataPoint lrb, int zoom, boolean newMethod) throws InterruptedException {

        List<PointLatLng> llList = null;

        PointLatLng ptlb;
        PointLatLng plrb;

        ptlb = _projection.fromPixelToLatLng(tlb, zoom);
        plrb = _projection.fromPixelToLatLng(lrb, zoom);

        //Find all of the points that belong in the expanded tile
        // Some points may appear in more than one tile depending where they appear
        if (newMethod) {
            ListSearch ls = new ListSearch(_pointList, ptlb, plrb);
            llList = ls.GetMatchingPoints();
        } else {
            llList = new ArrayList<PointLatLng>();
            for (PointLatLng point : _pointList) {
                if (point.getLatitude() <= ptlb.getLatitude() &&
                        point.getLongitude() >= ptlb.getLongitude()
                        && point.getLatitude() >= plrb.getLatitude() &&
                        point.getLongitude() <= plrb.getLongitude()) {
                    llList.add(point);
                }
            }

        }

        return llList.toArray(new PointLatLng[llList.size()]);
    }

    public static DataPoint AdjustMapPixelsToTilePixels(DataPoint tileXYPoint, DataPoint mapPixelPoint) {
        return new DataPoint(mapPixelPoint.getX() - (tileXYPoint.getX() * GHeat.SIZE), mapPixelPoint.getY() - (tileXYPoint.getY() * GHeat.SIZE));
    }


}
