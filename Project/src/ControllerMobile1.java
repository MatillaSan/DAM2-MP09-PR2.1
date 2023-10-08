import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControllerMobile1 implements Initializable{

    private String type;

    String typesList[] = { "Personatges", "Jocs", "Consoles" };

    @FXML
    private VBox mPane;

    @FXML
    private Label title;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      // Obtenir una referència a AppData que gestiona les dades
      AppData appData = AppData.getInstance();
  
      // Demanar les dades
      for (String t : typesList)
        appData.load(t, (result) -> {
            if (result == null) {
              System.out.println("ControllerMobile1: Error loading data.");
            }
        });

      backButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Main.setMobileView("Mobile0");
            UtilsViews.setView("Mobile0");
          }
      });

    }

    public void showList() throws Exception {

      // Obtenir una referència a l'ojecte AppData que gestiona les dades
      AppData appData = AppData.getInstance();

      // Obtenir les dades de l'opció seleccionada
      JSONArray dades = appData.getData(type);
      
      // Funció ‘showList’, modificar com es mostren les dades

      // Carregar la plantilla
      URL resource = this.getClass().getResource("assets/template_list_item.fxml");

      // Esborrar la llista actual
      mPane.getChildren().clear();

      title.setText(type);

      // Carregar la llista amb les dades
      for (int i = 0; i < dades.length(); i++) {
        JSONObject consoleObject = dades.getJSONObject(i);

        if (consoleObject.has("nom")) {
          String nom = consoleObject.getString("nom");
          String imatge = "assets/images/" + consoleObject.getString("imatge");
          FXMLLoader loader = new FXMLLoader(resource);
          Parent itemTemplate = loader.load();
          ControllerListItem itemController = loader.getController();
          itemController.setText(nom);
          itemController.setImage(imatge);

          final int index = i;
          itemTemplate.setOnMouseClicked(event -> {
            ((ControllerMobile2) UtilsViews.getController("Mobile2")).setTypeAndIndex(type, index);
            ((ControllerMobile2) UtilsViews.getController("Mobile2")).showInfo();
            
            Main.setMobileView("Mobile2");
            UtilsViews.setView("Mobile2");
          });

          mPane.getChildren().add(itemTemplate);
        }
      }
    }

    public void setType(String newType) {
      type = newType;
    }
    
}
