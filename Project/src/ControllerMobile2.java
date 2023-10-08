import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ControllerMobile2 implements Initializable{

    private String type;
    private int index;

    @FXML
    private Button backButton;

    @FXML
    private AnchorPane info;

    @FXML
    private Label title1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Main.setMobileView("Mobile1");
            UtilsViews.setView("Mobile1");
          }
      });
    }

    void showInfo() {
      // Obtenir una referència a l'ojecte AppData que gestiona les dades
      AppData appData = AppData.getInstance();
    
      // Obtenir les dades de l'opció seleccionada
      JSONObject dades = appData.getItemData(type, index);
    
      // Carregar la plantilla
      URL resource = null;
      switch (type) {
        case "Consoles": resource = this.getClass().getResource("assets/template_info_consola.fxml"); break;
        case "Jocs": resource = this.getClass().getResource("assets/template_info_joc.fxml"); break;
        case "Personatges": resource = this.getClass().getResource("assets/template_info_personatge.fxml"); break;
        default: resource = this.getClass().getResource("assets/template_info_item.fxml"); break;
      }
      
      // Esborrar la informació actual
      info.getChildren().clear();

      // Carregar la llista amb les dades
      try {
        FXMLLoader loader = new FXMLLoader(resource);
        Parent itemTemplate = loader.load();
        ControllerInfoItem itemController = loader.getController();
        itemController.setImage("assets/images/" + dades.getString("imatge"));
        title1.setText(dades.getString("nom"));
        itemController.setTitle(dades.getString("nom"));
        switch (type) {
          case "Consoles": 
            itemController.setText1(dades.getString("procesador"));
            itemController.setText2(dades.getString("data"));
            itemController.setText3(((Integer)dades.getInt("venudes")).toString());
            itemController.setColorBox(dades.getString("color"));
            break;
          case "Jocs": 
            itemController.setText1(dades.getString("tipus"));
            itemController.setText2(((Integer)dades.getInt("any")).toString());
            itemController.setText2(dades.getString("descripcio"));
            
            break;
          case "Personatges": 
            itemController.setText1(dades.getString("nom_del_videojoc"));
            itemController.setColorBox(dades.getString("color"));
            break;
        }

        // Afegeix la informació a la vista
        info.getChildren().add(itemTemplate);

        // Estableix que la mida de itemTemplaate s'ajusti a la mida de info
        AnchorPane.setTopAnchor(itemTemplate, 0.0);
        AnchorPane.setRightAnchor(itemTemplate, 0.0);
        AnchorPane.setBottomAnchor(itemTemplate, 0.0);
        AnchorPane.setLeftAnchor(itemTemplate, 0.0);

      } catch (Exception e) {
        System.out.println("ControllerDesktop: Error showing info.");
        System.out.println(e);
      }
    }

    public void setTypeAndIndex(String newType, int newIndex) {
        type = newType;
        index = newIndex;
    }
    
}
