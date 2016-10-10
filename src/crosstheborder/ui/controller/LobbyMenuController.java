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

/**
 * The controller class of the lobby menu
 */
public class LobbyMenuController {
    @FXML
    private void initialize(){
        //todo: only if the user is owner of the lobby should the start button be set visible
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

    private ClientMain instance;
    int maxPlayers;

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

    /**
     * Sets the main class this controller uses for functions
     * @param instance the ClientMain class
     */
    public void setInstance(ClientMain instance){
        this.instance = instance;
        int maxPlayers = instance.getMaxPlayers();
        for(int i = maxPlayers; i > 0; i--){
            choiceBoxAmountOfPlayers.getItems().add(i);
        }
    }

}
