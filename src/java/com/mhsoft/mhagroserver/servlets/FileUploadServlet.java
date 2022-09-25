/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.servlets;

/**
 *
 * @author mh
 */
import com.mhsoft.mhagroserver.entities.Product;
import com.mhsoft.mhagroserver.entities.Vendeur;
import com.mhsoft.mhagroserver.gerer.GererProduct;
import com.mhsoft.mhagroserver.gerer.GererVendeur;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.primefaces.json.JSONObject;

/**
 * File upload servlet example
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    @EJB
    private GererVendeur gererVendeur;

    @EJB
    private GererProduct gererProduct;
    
    String intitule ;
    String prix ;
    String unite_de_vente ;
    String categorie ;
    String stock ;
    String description ;
    String publier ;
    
    
    private final static Logger LOGGER = Logger.getLogger(FileUploadServlet.class.getCanonicalName());
    private static final long serialVersionUID = 7908187011456392847L;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        String vendeurid = request.getParameter("vendeurid"); ;
        
        if(action.matches("create")||action.matches("edit_avec_photo")||action.matches("edit_sans_photo")){
             intitule = request.getParameter("intitule");
             prix = request.getParameter("prix");
             unite_de_vente = request.getParameter("unite_de_vente");
             categorie = request.getParameter("categorie");
             stock = request.getParameter("stock");
             description = request.getParameter("description");
             publier = request.getParameter("publier");
        }
        
        
        if(action.matches("create")){

        final Part filePart = request.getPart("fileUpload");
        final String fileName = getFileName(filePart);

        final PrintWriter writer = response.getWriter();
        
        InputStream is = null;
        is = filePart.getInputStream();
        int i = is.available();
        byte[] b = new byte[i];
        is.read(b);        
        
        Date currentTime = Calendar.getInstance().getTime();

        Product product = new Product();
        product.setTitre_image_produit(currentTime+fileName);
        product.setImage_binaire_produit(b);
        product.setIntitule(intitule);
        product.setPrix_unitaire(prix);
        product.setCategorie(categorie);
        product.setUnite_de_vente(unite_de_vente);
        product.setQuantite_stock_produit(stock);
        product.setDescription(description);
        product.setPublier(publier);
        product.setDate_creation(currentTime);
        product.setDate_derniere_miseajour(currentTime);

//        Vendeur vendeur = new Vendeur();
//        vendeur.setIntitule("Niaco");
//        vendeur.setTelephone("699185307");
//        vendeur.setPassword("moussah");
//        vendeur.setPays("Cameroun");
//        vendeur.setRegion("Centre");
//        vendeur.setVille("Yaounde");
//        vendeur.setEtat_abonnement(true);
//        vendeur.setDate_creation(currentTime);
//        vendeur.setDate_derniere_miseajour(currentTime);
//        gererVendeur.Nouveau(vendeur);  

//        Vendeur vendeur = gererVendeur.RechercheId(1L);
        
        Vendeur vendeur = gererVendeur.RechercheId(Long.parseLong(vendeurid));
        product.setVendeur(vendeur);
        
        gererProduct.Nouveau(product);
        JSONObject jo = new JSONObject();
        jo.put("response","enregistre");
        writer.write(jo.toString());
        if (writer != null) {
            writer.close();
        }
      }
        
      if(action.matches("edit_avec_photo")){

        final Part filePart = request.getPart("fileUpload");
        final String fileName = getFileName(filePart);
        String productid = request.getParameter("productid");
        
        final PrintWriter writer = response.getWriter();
        
        InputStream is = null;
        is = filePart.getInputStream();
        int i = is.available();
        byte[] b = new byte[i];
        is.read(b);        
        
        Date currentTime = Calendar.getInstance().getTime();

        Product product = gererProduct.RechercheId(Long.parseLong(productid));
        product.setTitre_image_produit(currentTime+fileName);
        product.setImage_binaire_produit(b);
        product.setIntitule(intitule);
        product.setPrix_unitaire(prix);
        product.setCategorie(categorie);
        product.setUnite_de_vente(unite_de_vente);
        product.setQuantite_stock_produit(stock);
        product.setDescription(description);
        product.setPublier(publier);
        product.setDate_derniere_miseajour(currentTime);

        gererProduct.Miseajour(product);
        JSONObject jo = new JSONObject();
        jo.put("response","mis a jour avec succes");
        writer.write(jo.toString());
        if (writer != null) {
            writer.close();
        }
      }
      
      if(action.matches("edit_sans_photo")){

        String productid = request.getParameter("productid");

        final PrintWriter writer = response.getWriter();
        
       
        
        Date currentTime = Calendar.getInstance().getTime();

        Product product = gererProduct.RechercheId(Long.parseLong(productid));
        
        product.setIntitule(intitule);
        product.setPrix_unitaire(prix);
        product.setCategorie(categorie);
        product.setUnite_de_vente(unite_de_vente);
        product.setQuantite_stock_produit(stock);
        product.setDescription(description);
        product.setPublier(publier);
        product.setDate_derniere_miseajour(currentTime);

        gererProduct.Miseajour(product);
        JSONObject jo = new JSONObject();
        jo.put("response","mis a jour sans photo avec succes");
        writer.write(jo.toString());
        if (writer != null) {
            writer.close();
        }
      }
      
       if(action.matches("delete")){

        String productid = request.getParameter("productid");
        final PrintWriter writer = response.getWriter();
        Product product = gererProduct.RechercheId(Long.parseLong(productid));
        gererProduct.Supprimer(product);
        JSONObject jo = new JSONObject();
        jo.put("response","Supprime avec succes");
        writer.write(jo.toString());
        if (writer != null) {
            writer.close();
        }
      }
      
      
    }
    
    

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet that uploads files to a user-defined destination";
    }
}
