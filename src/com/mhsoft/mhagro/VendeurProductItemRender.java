/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagro;

import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.openGallery;
import static com.codename1.ui.CN1Constants.GALLERY_IMAGE;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;

/**
 *
 * @author Moussa Housseini
 */
public class VendeurProductItemRender {
    
    private String productImagePath  ;
    private String action ;

    
    public VendeurProductItemRender(){
    }
    
     Component renderItem(String ip,EncodedImage placeholder,VendeurProduct vendeurproduct,Vendeur vendeur){
         BorderLayout bl = new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
         Container container = new Container(bl);
         container.setUIID("ItemRenderContainer");

         Label name = new Label(vendeurproduct.getIntitule());
         URLImage photo = URLImage.createToStorage(placeholder,vendeurproduct.getTitre_image_produit(),ip+"MHAgroServer/ImageServletResize?id="+vendeurproduct.getId()+"&width="+Display.getInstance().getDisplayWidth()+"&height="+Display.getInstance().getDisplayWidth() / 4 * 3, URLImage.RESIZE_SCALE_TO_FILL);
         Label imagelabel = new Label(photo);
         Label priceLabel = new Label(vendeurproduct.getPrix_unitaire()+"FCFA/"+vendeurproduct.getUnite_de_vente(),"ItemRenderPriceLabel");
         
         Style s = UIManager.getInstance().getComponentStyle("Title");
         
         FontImage editIcon = FontImage.createMaterial(FontImage.MATERIAL_EDIT, s);
         FontImage deleteIcon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, s);
         
         Button btnEdit = new Button("Modifier",editIcon);
         btnEdit.setUIID("ItemRenderButton");
         btnEdit.addActionListener(e -> {
             
             editproductForm(ip,btnEdit,vendeurproduct,photo,vendeur);
                  
                    });
         
         Button btnDelete = new Button("Supprimer",deleteIcon);
         btnDelete.setUIID("ItemRenderButton");
         btnDelete.addActionListener(e -> {
                    if( Dialog.show("Info", "Voulez-vous supprimer","OK", "Cancel")){
                        ProductClient productclient = new ProductClient(ip);
                        productclient.sendRequest("delete",null,vendeurproduct.getId(),vendeur.getId());
                        
                    }
                    });
           
         Container southContainer = new Container(new GridLayout(1,2));

         southContainer.add(btnEdit);
         southContainer.add(btnDelete);
         
        Container box = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Button title = new Button(vendeurproduct.getIntitule());
        title.setUIID("ItemRenderTitleLabel");
//        Label highlights = new Label(product.getTitle());
        TextArea details = new TextArea(vendeurproduct.getDescription());
        details.setUIID("ItemRenderDescriptionLabel");
//        highlights.setUIID("DishBody");
        
        box.addComponent(title);
        box.addComponent(LayeredLayout.encloseIn(imagelabel,BorderLayout.south(priceLabel)));
        
        box.setLeadComponent(title);
        
        title.addActionListener((e) -> {
            if(details.getParent() != null) {
//                box.removeComponent(highlights);
                     box.removeComponent(details);
            } else {
                
//                box.addComponent(highlights);
                box.addComponent(details);

            }
            container.getParent().animateLayout(300);
        });
        
         
         container.add(BorderLayout.CENTER,box);
         container.add(BorderLayout.SOUTH,southContainer);
         return container ; 
     }
     
    public void editproductForm(String ip,Button button,VendeurProduct vendeurproduct,URLImage photo,Vendeur vendeur){
       Form editproductform = new Form("Editer Produit",new BoxLayout(BoxLayout.Y_AXIS));
      
       editproductform.getToolbar().setBackCommand(new Command(""){
                              public void actionPerformed(ActionEvent evt){
                                button.getComponentForm().showBack();
                                
                            }
                     });
       
       Label productImageLabel = new Label("Image Produit","ProductCreateLabel");
       Button productImage = new Button();
       Image img = photo.fill(256, 256);
       productImage.setIcon(img);
       productImage.setUIID("ProductImage");
       productImagePath = null ;
       productImage.addActionListener((evt)->{
           if(Dialog.show("Appareil photo ou gallerie","Voulez-vous utiliser l'appareil photo ou la gallerie", "Appareil photo", "Gallerie")){
            productImagePath    = Capture.capturePhoto(1024, -1);
                if(productImagePath == null){
                    return ;
                }
               if(productImagePath != null){
                try{
//               Image img = Image.createImage(productImagePath).scaledSmallerRatio(256,256);
               Image imge = Image.createImage(productImagePath).fill(256,256);
               productImage.setIcon(imge);
               editproductform.revalidate();
           }catch(IOException ex){
               Log.p("Erreur Photo"+ex,Log.ERROR);
               return ;
           }
           }
           }else {
                openGallery(ee -> {
                    productImagePath = (String)ee.getSource() ;
                    if(productImagePath !=  null) {
                        try {
                            Image imge = Image.createImage(productImagePath).fill(256,256);
                            productImage.setIcon(imge);
                            editproductform.revalidate();
                            } catch(IOException err) {
                            ToastBar.showErrorMessage("An error occured while loading the image: " + err);
                            Log.e(err);
                        }
                    }
                }, GALLERY_IMAGE);
            }
          
          
       } );
       
       Label  productTitleLabel = new Label("Intitule Produit","ProductCreateLabel");
       TextField productTitle = new TextField();
       productTitle.setHint("Exples: Macabo,tomate,mais");
       productTitle.setText(vendeurproduct.getIntitule());
       
       Label productPriceLabel = new Label("Prix unitaire produit","ProductCreateLabel");
       TextField productPrice = new TextField();
       productPrice.setConstraint(TextArea.NUMERIC);
       productPrice.setHint("Exples: 1000,2000,5000");
       productPrice.setText(vendeurproduct.getPrix_unitaire());

       
       Label productUnitySaleLabel = new Label("Unite de vente produit","ProductCreateLabel");
       TextField productUnitySale = new TextField();
       productUnitySale.setHint("Exples: Kg,litre");
       productUnitySale.setText(vendeurproduct.getUnite_de_vente());
       
       Label productCategoryLabel = new Label("Categorie produit","ProductCreateLabel");
       Picker productCategory = new Picker();
       productCategory.setStrings("Fruits","Legumes","Tubercules","Cereales");
       productCategory.setSelectedString(vendeurproduct.getCategorie());
       
       Label productTotalQuantityLabel = new Label("Quantite totale stock produit","ProductCreateLabel");
       TextField productTotalQuantity = new TextField();
       productTotalQuantity.setHint("Exples: 25sacs,10tonnes,300poulets");
       productTotalQuantity.setText(vendeurproduct.getQuantite_stock_produit());


       
       Label productDescriptionLabel = new Label("Description produit","ProductCreateLabel");
       TextArea productDescription = new TextArea(5,Display.getInstance().getDisplayWidth()-3);
       productDescription.setText(vendeurproduct.getDescription());
       
       Label productOnlineLabel = new Label("Publier le produit","ProductCreateLabel");
       Picker productOnline = new Picker();
       productOnline.setStrings("Oui","Non");
       productOnline.setSelectedString(vendeurproduct.getPublier());

       
       Button saveProductButton = new Button("Enregistrer");
       
       saveProductButton.addActionListener(e -> {
           if (productImage.getIcon() == null) {
                 if(productImagePath ==  null){
                     Dialog.show("Erreur","selection image du produit","OK",null);
                   } 
            }else{
                if(productCategory.getSelectedString() == " "){
                    Dialog.show("Error","selection categorie produit","OK",null);
                }else{
                    
                    ProductClient productclient = new ProductClient(ip);
                    Product product = new Product();
                    if(productImagePath ==  null){
                      action ="edit_sans_photo";
                      product.setImage(productImagePath);
                    } 
                    if(productImagePath !=  null){
                      action ="edit_avec_photo";
                      product.setImage(productImagePath);
                    }                                   
                    
                    product.setIntitule(productTitle.getText());
                    product.setPrix_unitaire(productPrice.getText());
                    product.setUnite_de_vente(productUnitySale.getText());
                    product.setCategorie(productCategory.getSelectedString());
                    product.setQuantite_stock(productTotalQuantity.getText());
                    product.setDescription(productDescription.getText());
                    product.setPublier(productOnline.getText());
                    productclient.sendRequest(action,product,vendeurproduct.getId(),vendeur.getId());
                    Dialog.show("Info","modifier avec succes","OK",null);
                    
                    
                }
            }
            
            });
        
       editproductform.add(productImageLabel);
       editproductform.add(productImage);
       editproductform.add(productTitleLabel);
       editproductform.add(productTitle);
       editproductform.add(productPriceLabel);
       editproductform.add(productPrice);
       editproductform.add(productUnitySaleLabel);
       editproductform.add(productUnitySale);
       editproductform.add(productCategoryLabel);
       editproductform.add(productCategory);
       editproductform.add(productTotalQuantityLabel);
       editproductform.add(productTotalQuantity);
       editproductform.add(productDescriptionLabel);
       editproductform.add(productDescription);
       editproductform.add(productOnlineLabel);
       editproductform.add(productOnline);
       editproductform.add(saveProductButton);
       
       Validator validator = new Validator();
                  validator.setShowErrorMessageForFocusedComponent(true);
                  validator.setValidationFailureHighlightMode(Validator.HighlightMode.UIID_AND_EMBLEM);
                  validator.setErrorMessageUIID("ProductValidationLabel");
                  
        validator.addConstraint(productTitle,new LengthConstraint(1,"Saisir intitule produit")).
                  addConstraint(productPrice,new LengthConstraint(1,"Saisir prix du produit")). 
                  addConstraint(productUnitySale,new LengthConstraint(1,"Saisir unite de vente du produit")). 
                  addConstraint(productDescription,new LengthConstraint(1,"Saisir la description du produit")). 
                  addConstraint(productTotalQuantity, new LengthConstraint(1,"Saisir quantite total en stock du produit"));               
        validator.addSubmitButtons(saveProductButton);


       
       editproductform.show();
   }
}

