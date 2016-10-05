package crosstheborder.ui.controller;

/**
 * Created by yannic on 03/10/2016.
 */

import crosstheborder.lib.Player;
import crosstheborder.lib.User;
import crosstheborder.ui.ClientMain;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.*;
import org.w3c.dom.traversal.NodeIterator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

public class LobbyMenuController {
    public LobbyMenuController(){

    }
    @FXML
    public void initialize(){

        int maxPlayers = ClientMain.getInstance().getMaxPlayers();
        for(int i = maxPlayers; i > 0; i--){
            choiceBoxAmountOfPlayers.getItems().add(i);
        }

    }
    @FXML
    private Button btnStartGame;
    @FXML
    private ListView listViewChatView;
    @FXML
    private TableView tablePlayers;
    @FXML
    private CheckBox checkBoxIsPrivate;
    @FXML
    private PasswordField tfieldPassInput;
    @FXML
    private Button btnChat;
    @FXML
    private ChoiceBox choiceBoxAmountOfPlayers;

    @FXML
    private void textBoxIsPrivate_OnAction(ActionEvent event){
        //test implementatie, werkt wel
        if(checkBoxIsPrivate.isSelected()){
            if(tfieldPassInput.getText() != null){
                tfieldPassInput.setVisible(false);
                //set het wachtwoord van de lobby...
            }
            else {
                System.out.println("please specify a password");
            }
        }
        else {
            tfieldPassInput.setText("");
            tfieldPassInput.setVisible(true);
        }
    }

    @FXML
    private void btnStartGame_OnAction(){
        throw new NotImplementedException();
    }

    @FXML
    private void btnChat_OnAction(){
        //String toSend = tfieldChatInput.getText();
        //ClientMain.getInstance().sendChatmsg(toSend);
        //listViewChatView.getItems().add(toSend);
        throw new NotImplementedException();
    }

    public TableView getTablePlayers() {
        //tablePlayers.getItems().add(new User("test"));
        return tablePlayers;
    }

}
