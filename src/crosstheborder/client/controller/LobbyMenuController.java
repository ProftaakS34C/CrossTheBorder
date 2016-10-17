package crosstheborder.client.controller;


import crosstheborder.client.ClientMain;
import crosstheborder.lib.Lobby;
import crosstheborder.lib.Message;
import crosstheborder.lib.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * @author Yannic
 * The controller class of the lobby menu
 */
public class LobbyMenuController {

    int maxPlayers;
    @FXML
    private Button startGameButton;
    @FXML
    private ListView<Message> chatListView;
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
    @FXML
    private Button addAiButton;
    @FXML
    private TextField mapNameInputTextField;

    private ClientMain instance;
    private Lobby lobby;
    private User user;

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     * Sets the main class this controller uses for functions
     *
     * @param instance the ClientMain class
     */
    public void setUp(ClientMain instance) {
        this.instance = instance;
        this.user = instance.getUser();
        this.lobby = this.user.getLobby();

        if (!instance.getUser().isOwnerOfLobby()) {
            startGameButton.setVisible(false);
            lobbyPassInputPasswordField.setVisible(false);
            isPrivateCheckBox.setVisible(false);
            choiceBoxAmountOfPlayers.setVisible(false);
        }
    }

    @FXML
    private void textBoxIsPrivate_OnAction(ActionEvent event){
        if(isPrivateCheckBox.isSelected()){
            if (!lobbyPassInputPasswordField.getText().trim().equals("") && lobbyPassInputPasswordField.getText() != null) {
                lobbyPassInputPasswordField.setVisible(false);
                lobby.setPassword(lobbyPassInputPasswordField.getText());
                System.out.println("set password");
            }
            else {
                isPrivateCheckBox.setSelected(false);
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
        leaveLobby();
    }

    @FXML
    private void btnStartGame_OnAction(){
        //TODO POLISH change into choicebox with all available maps
        String mapName = mapNameInputTextField.getText();

        lobby.startGame(mapName);
        instance.showGameScreen();
    }

    @FXML
    private void addAiButton_OnAction() {
        //instance.getLobby().addAi
        //todo: implement adding of AI
        //instance.getLobby().addUser(new User("fakeAI"));
        throw new UnsupportedOperationException();
    }

    @FXML
    private void btnChat_OnAction(){
        Message message = new Message(instance.getUser().getName(), chatInputTextField.getText());
        lobby.addMessage(message);
        refreshChatLog();
    }

    /**
     * Leaves the current lobby.
     */
    private void leaveLobby() {
        System.out.println("Leaving lobby...");
        user.leaveLobby();
        instance.showMainMenu();
    }

    private void refreshChatLog() {
        chatListView.getItems().clear();
        for (Message message : lobby.getMessages()) {
            chatListView.getItems().add(message);
        }
    }
}
