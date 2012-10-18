package JavaHeatMaps;

import gheat.HeatMap;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class TileHandler extends AbstractHandler {


    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("image/png");

        // response.setHeader("Age","2824");
        // response.setHeader("Cache-Control","max-age=3600");
        //  response.setHeader("Last-Modified","Tue, 18 Sep 2012 02:20:00 GMT");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        int x = Integer.parseInt(request.getParameter("x"));
        int y = Integer.parseInt(request.getParameter("y"));
        int z = Integer.parseInt(request.getParameter("zoom"));
        String cs = request.getParameter("colorScheme");

        BufferedImage tile = null;
        try {
            tile = HeatMap.GetTile(App.dataManager, cs, z, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputStream os = response.getOutputStream();
        //response.reset();
        ImageIO.write(tile, "png", os);
        os.close();


    }


}
