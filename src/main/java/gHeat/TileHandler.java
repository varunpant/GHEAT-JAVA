package gHeat;


import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TileHandler extends AbstractHandler {

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("image/png");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        int x = Integer.parseInt(request.getParameter("x"));
        int y = Integer.parseInt(request.getParameter("y"));
        int z = Integer.parseInt(request.getParameter("zoom"));
        String cs = request.getParameter("colorScheme");

        try {
            BufferedImage tile = GHeat.GetTile(App.pointManager, cs, z, x, y,false,false,100);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(tile, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            response.setContentLength(imageInByte.length);
            response.getOutputStream().write(imageInByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
