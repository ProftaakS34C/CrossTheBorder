package crosstheborder.client.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yannic 11/10/2016.
 */
public class CreateLobbyDialog {

    private boolean nameOK = false;
    private boolean playerAmountOK = false;
    private Dialog<List<String>> dialog;
    private Node doneButton;
    public CreateLobbyDialog(){
        dialog = new Dialog<>();
        //Set button types
        ButtonType okButton = new ButtonType("done", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        //add the inputs
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField lobbyNameInput = new TextField();
        TextField playerAmountInput = new TextField();

        gridPane.add(new Label("lobby naam"), 0, 0);
        gridPane.add(lobbyNameInput, 1, 0);
        gridPane.add(new Label("aantal spelers"), 0, 1);
        gridPane.add(playerAmountInput, 1, 1);

        //only enable the okButton if input is valid
        doneButton = dialog.getDialogPane().lookupButton(okButton);
        doneButton.setDisable(true);



        lobbyNameInput.textProperty().addListener((observable, oldValue, newValue) ->{
            nameOK = !newValue.trim().isEmpty();
            enableButtonIfInputOk();
        });
        playerAmountInput.textProperty().addListener((observable, oldVal, newVal) ->{
            playerAmountOK = checkIfInt(newVal);
            enableButtonIfInputOk();
        });

        dialog.getDialogPane().setContent(gridPane);

        Platform.runLater(()-> lobbyNameInput.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            List<String> returnList = new ArrayList<>();
            if(dialogButton == okButton){
                returnList.add(lobbyNameInput.getText());
                returnList.add(playerAmountInput.getText());
            }
            return returnList;
        });

    }

    private void enableButtonIfInputOk(){
        if(nameOK && playerAmountOK){
            doneButton.setDisable(false);
        }
        else {
            doneButton.setDisable(true);
        }
    }
    /**
     * This methods checks if a string can be converted to a integer
     * @param toCheck The String value to check
     * @return true if able to convert and false if not.
     */
    private boolean checkIfInt(String toCheck){
        try{
            Integer.parseInt(toCheck);
            return true;
        }catch (NumberFormatException x){
            return false;
        }
    }
    /**
     * Sets the title of this dialog
     * @param title String to be set as title
     */
    public void setTitle(String title){
        dialog.setTitle(title);
    }

    /**
     * Sets the header text of this dialog
     * @param text String to be set as header
     */
    public void setHeaderText(String text){
        dialog.setHeaderText(text);
    }

    /**
     * this method acts as the showAndWait Method of this pseudo dialog
     * @return an optional List of Strings, with 2 strings.
     */
    public Optional<List<String>> showAndWait(){
        return dialog.showAndWait();
        //todo: test this class
    }
}
