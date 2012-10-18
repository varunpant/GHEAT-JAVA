package gheat.datasources;

import gheat.DataPoint;
import gheat.PointLatLng;
import gheat.Projections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostGisDataSource implements DataSource {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    List<PointLatLng> llList;

    String url = null;
    String user = null;
    String password = null;


    public PostGisDataSource(String url, String user, String password) {
        llList = new ArrayList<PointLatLng>();
        this.url = url;
        this.password = password;
        this.user = user;
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
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);

            String stm = "SELECT ST_AsText(\"wkb_geometry\") as geom ,\"offences\" FROM crimedata WHERE \"wkb_geometry\" @ ST_MakeEnvelope(?,?,?,?,4326)";

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
                double offenses = rs.getDouble("offences");

                double longitude = Double.parseDouble(points[0]);//x
                double latitude = Double.parseDouble(points[1]); //y

                PointLatLng pt = new PointLatLng(latitude, longitude, offenses);
                llList.add(pt);
            }
        } catch (Exception ex) {
            Logger lgr = Logger.getLogger(PostGisDataSource.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(PostGisDataSource.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }

            return llList;
        }
    }
}
