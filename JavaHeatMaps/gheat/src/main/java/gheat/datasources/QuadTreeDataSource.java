package gheat.datasources;

import gheat.DataPoint;
import gheat.PointLatLng;
import gheat.Projections;
import gheat.datasources.QuadTree.QuadTree;

import java.io.BufferedReader;
import java.io.FileReader;

public class QuadTreeDataSource implements HeatMapDataSource {

    QuadTree qt = new QuadTree(-180.000000, -90.000000, 180.000000, 90.000000);

    public QuadTreeDataSource(String filePath) {
        LoadPointsFromFile(filePath);
    }



    @Override
    public PointLatLng[] GetList(DataPoint tlb, DataPoint lrb, int zoom, Projections _projection) {


        PointLatLng ptlb;
        PointLatLng plrb;

        ptlb = _projection.fromPixelToLatLng( lrb, zoom);
        plrb = _projection.fromPixelToLatLng(tlb , zoom);


        PointLatLng[] list = qt.searchIntersect(plrb.getLongitude(),ptlb.getLatitude(),
                ptlb.getLongitude(),plrb.getLatitude());


        return list;
    }






    @Override
    public void close() {
        qt.clear();
    }

    private void LoadPointsFromFile(String source) {
        String[] item;
        String[] lines = readAllTextFileLines(source);
        for (String line : lines) {
            item = line.split(",");
            qt.set(Double.parseDouble(item[2]), Double.parseDouble(item[1]), Double.parseDouble(item[0]));

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


}
