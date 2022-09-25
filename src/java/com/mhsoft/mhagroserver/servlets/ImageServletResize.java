/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.servlets;




import com.mhsoft.mhagroserver.entities.Product;
import com.mhsoft.mhagroserver.gerer.GererProduct;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Moussa Housseini
 */

//http://localhost:8080/EatServer/ImageServletResize?id=1&width=807&height=603

@WebServlet(name ="ImageServletResize", urlPatterns = {"/ImageServletResize"})
public class ImageServletResize extends HttpServlet {
   
    @EJB
    private GererProduct gererproduct;
    private static final Logger logger = Logger.getLogger(ImageServletResize.class.getCanonicalName());

    // Constants --------------------------   public class ImageServlet extends HttpServlet {--------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
                                                          // Properties ---------------------------------------------------------------------------------    
    private final String UPLOAD_DIR = "/upload/img/";
     int thumbWidth = 1000;
    int thumbHeight = 400;

   
    @Override
    public void init() throws ServletException {
      
    }

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        // Get requested image by path info.
       int thumbHeight = Integer.parseInt(request.getParameter("height"));
       int thumbWidth = Integer.parseInt(request.getParameter("width"));
       String requestedImage = request.getParameter("id"); //request.getPathInfo();
        // Check if file name is actually supplied to the request URI.
        System.out.println("HEIGTH  "+thumbHeight);
        System.out.println("WIDTH  "+thumbWidth);
        System.out.println("ID  "+requestedImage);

        if (requestedImage == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
//        Product p = productBean.find(Integer.parseInt(requestedImage));
        Product img = gererproduct.RechercheId(Long.parseLong(requestedImage));
       
       
        
        if ((img == null) || (img.getImage_binaire_produit()== null)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        } else {
   

    ByteInputStream byteInputStream = new ByteInputStream();
    byteInputStream.setBuf(img.getImage_binaire_produit());
    Image image = ImageIO.read(byteInputStream);
    BufferedImage thumb = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = thumb.createGraphics();
    graphics2D.setBackground(Color.WHITE);
    graphics2D.setPaint(Color.WHITE); 
    graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
    response.setContentType("image/jpeg");
    ImageIO.write(thumb, "JPG", response.getOutputStream());


        }
    }

   
}

