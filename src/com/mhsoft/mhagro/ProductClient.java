/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagro;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author mh
 */
public class ProductClient {
    private String URL= "MHAgroServer/upload";
    Map response;
    
    public ProductClient(String ip){
        URL = ip+URL ;
    }

    public void sendRequest(String action,Product product,Long productId,String vendeurId){
        
        MultipartRequest  multipartRequest = new MultipartRequest (){   
            @Override
            protected void readResponse(InputStream input) throws IOException {
             Display.getInstance().invokeAndBlock(() -> {
                    JSONParser jp = new JSONParser();
                    try {
                        // this is a relatively simple builtin parser that converts JSON into a Map hierarchy
                         response = jp.parseJSON(new InputStreamReader(input,"UTF-8"));
                         System.out.println(response);
                        } catch (IOException ex) {
                            showError("Failed to parse JSON " + ex);
                    }
                });
            }
            
            @Override
            protected void handleErrorResponseCode(int code, String message) {
                    showError("The server returned the error code: " + code);
            }
            @Override
            protected void handleException(Exception err) {
                    showError("There was a connection error: " + err);
            }
           };
           // Show loading
          try {
            InfiniteProgress ip = new InfiniteProgress();
            Dialog dlg = ip.showInifiniteBlocking();
            multipartRequest.setDisposeOnCompletion(dlg);
            multipartRequest.setUrl(URL);
            multipartRequest.setPost(true);
            multipartRequest.setHttpMethod("POST");
            if(action.equals("create")|| action.equals("edit_avec_photo") ){
              multipartRequest.setFilename("fileUpload", "myPicture.jpg");
            //pour envoyer un fichier image
            //multipartRequest.addData("fileUpload",path,"image/jpeg");
            //pour envoyer n'importe quel type de fichier
            multipartRequest.addData("fileUpload",product.getImage(),"text/plain");
                
            }
            multipartRequest.addArgumentNoEncoding("action",action);
            multipartRequest.addArgumentNoEncoding("productid",""+productId);
            multipartRequest.addArgumentNoEncoding("vendeurid",vendeurId);

            
            if(action.equals("create")|| action.equals("edit_avec_photo") || action.equals("edit_sans_photo")){
                multipartRequest.addArgumentNoEncoding("intitule",product.getIntitule());
                multipartRequest.addArgumentNoEncoding("prix",product.getPrix_unitaire());
                multipartRequest.addArgumentNoEncoding("unite_de_vente",product.getUnite_de_vente());
                multipartRequest.addArgumentNoEncoding("categorie",product.getCategorie());
                multipartRequest.addArgumentNoEncoding("stock",product.getQuantite_stock());
                multipartRequest.addArgumentNoEncoding("description",product.getDescription());
                multipartRequest.addArgumentNoEncoding("publier",product.getPublier());
            }
            
            

            NetworkManager.getInstance().addToQueueAndWait(multipartRequest);
        
        } catch (IOException ex) {
        }
            
    }
    // we show an error either in the main form or in a dialog, for later pages it means we got an error during scrolling so we will use a dialog
    private void showError(final String message) {
        // call serially is used since we are not on the event disaptch thread during error callbacks
        Display.getInstance().callSerially(new Runnable() {
            public void run() {
                    Dialog.show("Error", message,"OK", null);
            }
        });
    }
    
    
}
