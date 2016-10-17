package crosstheborder.client.controller;


import crosstheborder.client.ClientMain;
import crosstheborder.lib.Message;
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
    @FXML
    private Button addAiButton;
    @FXML
    private TextField mapNameInputTextField;
    private ClientMain instance;

    @FXML
    private void initialize(){
        //todo: only if the user is owner of the lobby should the start button be set visible
    }

    /**
     * This method is used for first time setup of the controller, if the initialize method cannot be used.
     */
    public void setUp(){

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
                instance.getUser().getLobby().setPassword(lobbyPassInputPasswordField.getText());
                System.out.println("set password");
            }
            else {
                isPrivateCheckBox.setSelected(false);
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
        //TODO POLISH if user is owner of lobby popup a dialog and remove everyone else from the lobby.
        instance.leaveLobby();
    }

    @FXML
    private void btnStartGame_OnAction(){
        //TODO POLISH change into choicebox with all available maps
        String mapName = mapNameInputTextField.getText();

        instance.getLobby().startGame(mapName);
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
        instance.getLobby().addMessage(message);
        refreshChatLog();
    }

    private void refreshChatLog() {
        chatListView.getItems().clear();
        for (Message message : instance.getLobby().getMessages()) {
            chatListView.getItems().add(message);
        }
    }

    public TableView getPlayersTableView() {
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
