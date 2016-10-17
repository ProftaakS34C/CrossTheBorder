package crosstheborder.client.controller;


import crosstheborder.client.ClientMain;
import crosstheborder.lib.Lobby;
import crosstheborder.lib.Message;
import crosstheborder.lib.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The controller class of the lobby menu
 *
 * @author Yannic
 * @author Oscar de Leeuw
 */
public class LobbyMenuController {

    @FXML
    private Button startGameButton;
    @FXML
    private ListView<Message> chatListView;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private CheckBox isPrivateCheckBox;
    @FXML
    private PasswordField lobbyPassInputPasswordField;
    @FXML
    private TextField chatInputTextField;
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
        }

        refreshUsersTableView();
        refreshChatListView();
    }

    @FXML
    private void textBoxIsPrivate_OnAction(ActionEvent event){
        if(isPrivateCheckBox.isSelected()){
            if (!lobbyPassInputPasswordField.getText().trim().equals("") && lobbyPassInputPasswordField.getText() != null) {
                lobbyPassInputPasswordField.setVisible(false);
                lobby.setPassword(lobbyPassInputPasswordField.getText());
            }
            else {
                isPrivateCheckBox.setSelected(false);
            }
        }
        else {
            lobbyPassInputPasswordField.setText("");
            lobby.setPassword("");
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
        String chatText = chatInputTextField.getText();

        if (chatText.matches("(<script>alert\\((\\d*|\".*\")\\)</script>)")) {
            Alert popup = new Alert(Alert.AlertType.INFORMATION);
            popup.setContentText(chatText.substring(chatText.indexOf('(') + 1, chatText.lastIndexOf(')')).replace('"', '\u0000'));
            popup.showAndWait();
        } else {
            Message message = new Message(user, chatText);
            lobby.addMessage(message);
        }

        chatInputTextField.clear();
        refreshChatListView();
    }

    /**
     * Leaves the current lobby.
     */
    private void leaveLobby() {
        System.out.println("Leaving lobby...");
        user.leaveLobby();
        instance.showMainMenu();
    }

    private void refreshChatListView() {
        chatListView.getItems().clear();
        for (Message message : lobby.getMessages()) {
            chatListView.getItems().add(message);
        }
    }

    private void refreshUsersTableView() {
        usersTableView.getItems().clear();

        TableColumn nameColumn = usersTableView.getColumns().get(0);
        TableColumn ownerColumn = usersTableView.getColumns().get(1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<User, Boolean>("owner"));

        for (User user : lobby.getUsers()) {
            usersTableView.getItems().add(user);
        }
    }
}
