package crosstheborder.client.controller;

/**
 * @author Yannic
 */

import crosstheborder.client.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;

/**
 * The controller class of the lobby menu
 */
public class LobbyMenuController {


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
    private TextField chatInputTextField;
    @FXML
    private ChoiceBox choiceBoxAmountOfPlayers;
    @FXML
    private Button leaveLobbyButton;

    private ClientMain instance;
    int maxPlayers;

    @FXML
    private void initialize(){
        //todo: only if the user is owner of the lobby should the start button be set visible
    }

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     */
    public void setUp(){
        if(instance.getUser().isOwnerOfLobby()){
            leaveLobbyButton.setVisible(false);
        }
        else {
            startGameButton.setVisible(false);
            lobbyPassInputPasswordField.setVisible(false);
            isPrivateCheckBox.setVisible(false);
            choiceBoxAmountOfPlayers.setVisible(false);
        }
    }

    @FXML
    private void textBoxIsPrivate_OnAction(ActionEvent event){
        //test implementatie, werkt wel
        if(isPrivateCheckBox.isSelected()){
            if(lobbyPassInputPasswordField.getText() != null){
                lobbyPassInputPasswordField.setVisible(false);
                instance.getUser().getLobby().setPassword(lobbyPassInputPasswordField.getText());
                System.out.println("set password");
            }
            else {
                System.out.println("please specify a password");
            }
        }
        else {
            lobbyPassInputPasswordField.setText("");
            instance.getUser().getLobby().setPassword("");
            System.out.println("removed password");
            lobbyPassInputPasswordField.setVisible(true);
        }
    }

    @FXML
    private void leaveLobbyButton_OnAction(){
        //do necessary actions for leaving lobby
        //TODO if user is owner of lobby popup a dialog and remove everyone else from the lobby.

        System.out.println("leaving lobby...");
        instance.getUser().setLobby(null);
        instance.showMainMenu();
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

    }



}
