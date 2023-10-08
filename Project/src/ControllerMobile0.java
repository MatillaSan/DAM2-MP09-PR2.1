import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ControllerMobile0 implements Initializable{

    @FXML
    ListView<String> listView = new ListView<String>();

    ObservableList<String> items =FXCollections.observableArrayList (
        "Personatges", "Jocs", "Consoles"
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        listView.setItems(items);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            ((ControllerMobile1) UtilsViews.getController("Mobile1")).setType(newValue);

            try {
                ((ControllerMobile1) UtilsViews.getController("Mobile1")).showList();
            } catch (Exception e) {
                System.out.println("ControllerMobile0: Error showing list.");
            }
            
            Main.setMobileView("Mobile1");
            UtilsViews.setView("Mobile1");

        });

    }
    
}
