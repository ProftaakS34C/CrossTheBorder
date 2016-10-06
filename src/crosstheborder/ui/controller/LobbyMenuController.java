package crosstheborder.ui.controller;

/**
 * @author Yannic
 *
 */

import crosstheborder.ui.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;

public class LobbyMenuController {
    public LobbyMenuController(){

    }
    @FXML
    private void initialize(){

        //fill the choiceBox with numbers
        int maxPlayers = ClientMain.getInstance().getMaxPlayers();
        for(int i = maxPlayers; i > 0; i--){
            choiceBoxAmountOfPlayers.getItems().add(i);
        }

    }
    @FXML
    private Button startGameButton;
    @FXML
    private ListView chatListView;
    @FXML
    private TableView playersTableView;
    @FXML
    private CheckBox isPrivateCheckBox;
    @FXML
    private PasswordField lobbyPassInputPasswordField;
    @FXML
    private Button chatButton;
    @FXML
    private ChoiceBox choiceBoxAmountOfPlayers;

    @FXML
    private void textBoxIsPrivate_OnAction(ActionEvent event){
        //test implementatie, werkt wel
        if(isPrivateCheckBox.isSelected()){
            if(lobbyPassInputPasswordField.getText() != null){
                lobbyPassInputPasswordField.setVisible(false);
                //set het wachtwoord van de lobby...
            }
            else {
                System.out.println("please specify a password");
            }
        }
        else {
            lobbyPassInputPasswordField.setText("");
            lobbyPassInputPasswordField.setVisible(true);
        }
    }

    @FXML
    private void btnStartGame_OnAction(){
        throw new UnsupportedOperationException();
    }

    @FXML
    private void btnChat_OnAction(){
        //String toSend = tfieldChatInput.getText();
        //ClientMain.getInstance().sendChatmsg(toSend);
        //chatListView.getItems().add(toSend);
        throw new UnsupportedOperationException();
    }

    public TableView getPlayersTableView() {
        //playersTableView.getItems().add(new User("test"));
        return playersTableView;
    }

}
