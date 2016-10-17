package crosstheborder.client.controller;

/**
 * @author Yannic
 */

import crosstheborder.client.ClientMain;
import crosstheborder.lib.Lobby;
import crosstheborder.lib.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * The controller class of the lobby menu
 */
public class LobbyMenuController {


    int maxPlayers;
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
    private Lobby lobby;

    @FXML
    private void initialize(){
        //todo: only if the user is owner of the lobby should the start button be set visible.
    }

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     */
    public void setUp(){
        if(instance.getUser().isOwnerOfLobby()){
            leaveLobbyButton.setVisible(false);
            lobby = instance.getUser().getLobby();
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
                lobby.setPassword(lobbyPassInputPasswordField.getText());
                System.out.println("set password");
            }
            else {
                System.out.println("please specify a password");
            }
        }
        else {
            lobbyPassInputPasswordField.setText("");
            lobby.setPassword("");
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
        String message = chatInputTextField.getText();
        Message chatMessage = new Message(instance.getUser().getName(), message);
        lobby.addMessage(chatMessage);
        refreshChatLog();
    }

    private void refreshChatLog() {
        chatListView.getItems().clear();
        for (Message message : lobby.getMessages()) {
            chatListView.getItems().add(message);
        }
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
