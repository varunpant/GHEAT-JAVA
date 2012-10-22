package gheat.datasources;

import gheat.DataPoint;
import gheat.PointLatLng;
import gheat.Projections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostGisDataSource implements HeatMapDataSource {


    PreparedStatement pst = null;
    ResultSet rs = null;
    List<PointLatLng> llList;
    Connection con = null;

    static String query = null;

    public PostGisDataSource(String query) {
        llList = new ArrayList<PointLatLng>();

        this.query = query;
    }

    @Override
    public PointLatLng[] GetList(DataPoint tlb, DataPoint lrb, int zoom, Projections _projection) {

        List<PointLatLng> llList = null;

        PointLatLng ptlb;
        PointLatLng plrb;

        ptlb = _projection.fromPixelToLatLng(tlb, zoom);
        plrb = _projection.fromPixelToLatLng(lrb, zoom);


        llList = getData(plrb.getLongitude(), plrb.getLatitude(), ptlb.getLongitude(), ptlb.getLatitude());


        return llList.toArray(new PointLatLng[llList.size()]);
    }

    private List<PointLatLng> getData(double llx, double lly, double ulx, double uly) {
        try {

            con = DBPool.getConnection();

            String stm = query;

            pst = con.prepareStatement(stm);
            pst.setDouble(1, llx);
            pst.setDouble(2, lly);
            pst.setDouble(3, ulx);
            pst.setDouble(4, uly);

            System.out.println(pst);
            rs = pst.executeQuery();
            while (rs.next()) {
                String wkt = rs.getString("geom");
                String[] points = wkt.replace("POINT(", "").replace(")", "").split(" ");   //:!
                double offenses = rs.getDouble("weight");

                double longitude = Double.parseDouble(points[0]);//x
                double latitude = Double.parseDouble(points[1]); //y

                PointLatLng pt = new PointLatLng(latitude, longitude, offenses);
                llList.add(pt);
            }
        } catch (Exception ex) {
            Logger lgr = Logger.getLogger(PostGisDataSource.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            return llList;
        }
    }
}
