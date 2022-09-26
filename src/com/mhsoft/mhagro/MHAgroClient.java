/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagro;

import ca.weblite.codename1.json.JSONObject;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

/**
 *
 * @author Moussa Housseini
 */
public class MHAgroClient {
    
//    private String URL= "http://localhost:8080/EatServer/api/commande/enregistrer/";
//    private String URL= "MHAgroServer/api/vendeurregistrationapi/registration/";
    private String URL= "MHAgroServer/api/";

    
    Map response;
    
    
    public MHAgroClient(String ip,String api){
        URL = ip+URL+api ;
    }
    
    
    private ConnectionRequest sendRequest(Boolean isPost,String httpMethod,String contenType,JSONObject jsonObject){
        
        final String payload = jsonObject.toString();
        
        ConnectionRequest connectionRequest = new ConnectionRequest(){
            
            @Override
            protected void readResponse(InputStream input) throws IOException {
             Display.getInstance().invokeAndBlock(() -> {
                    JSONParser jp = new JSONParser();
                    try {
                        // this is a relatively simple builtin parser that converts JSON into a Map hierarchy
                         response = jp.parseJSON(new InputStreamReader(input,"UTF-8"));
                        } catch (IOException ex) {
                            showError("Failed to parse JSON " + ex);
                    }
                });
            }
            @Override
            protected void buildRequestBody(OutputStream os)throws IOException{
                    os.write(payload.getBytes("UTF-8"));
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
          
            InfiniteProgress ip = new InfiniteProgress();
            Dialog dlg = ip.showInifiniteBlocking();
            connectionRequest.setDisposeOnCompletion(dlg);
          
            connectionRequest.setUrl(URL);
            connectionRequest.setPost(isPost);
            connectionRequest.setHttpMethod(httpMethod);
            connectionRequest.setContentType(contenType);
            NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
            return connectionRequest ;
    }
        
    
     private Map  getResponse(Boolean isPost,String httpMethod,String contenType,JSONObject jsonObject) throws IOException {
        ConnectionRequest req = sendRequest(isPost,httpMethod,contenType,jsonObject); 
        
        return response ; 
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
    
    public Map registration(JSONObject jsonObject) throws IOException {
        Map res = getResponse(true,"POST","application/json",jsonObject);
        System.out.println(res);
        return res;
    }
    
}

    

