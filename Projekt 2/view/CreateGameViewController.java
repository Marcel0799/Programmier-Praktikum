package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import model.ColourType;
import model.Player;
import model.PlayerType;
import view.aui.PlayerAUI;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author Marcel Bienia, Marc Fischer
 */
public class CreateGameViewController implements PlayerAUI {

    //create Player
    @FXML
    private TextField textFieldPlayerName;
    @FXML
    private RadioButton checkBoxHumanPlayer;
    @FXML
    private RadioButton checkBoxAiEasy;
    @FXML
    private RadioButton checkBoxAiMedium;
    @FXML
    private RadioButton checkBoxAiHard;

    //create Game
    @FXML
    private CheckBox checkBoxModeDynasty;
    @FXML
    private CheckBox checkBoxModeBigDuel;
    @FXML
    private CheckBox checkBoxModeHarmonie;
    @FXML
    private CheckBox checkBoxModeCastleMiddle;
    @FXML
    private CheckBox checkBoxMixCards;
    @FXML
    private Button btBlue;
    @FXML
    private Button btYellow;
    @FXML
    private Button btGreen;
    @FXML
    private Button btRed;
    @FXML
    private TextField tfCSVFile;

    @FXML
    private ListView<String> lvPlayerList;

    private MainMenuViewController mainMenuViewController;
    private ToggleGroup group;
    private ColourType chosenColorType;

    private AudioClip mediaPlayer;

    /**
     * constructor
     */
    public CreateGameViewController() {
        group = new ToggleGroup();
        //System.out.println("CreateGameController was created");
    }

    /**
     * user wants to add an player to the next game
     *
     * @param actionEvent is the triggered event when the button is pressed
     */
    public void onCreatePlayerButtonAction(ActionEvent actionEvent) {
        String playerName = textFieldPlayerName.getText();
        PlayerType playerType = null;
        if (checkBoxHumanPlayer.isSelected()) {
            playerType = PlayerType.HUMAN;
        } else if (checkBoxAiEasy.isSelected()) {
            playerType = PlayerType.AI_NOOB;
        } else if (checkBoxAiMedium.isSelected()) {
            playerType = PlayerType.AI_MEDIUM;
        } else if (checkBoxAiHard.isSelected()) {
            playerType = PlayerType.AI_CHALLENGER;
        }

        boolean playerCreated = mainMenuViewController.getMainController().getPlayerController().createPlayer(playerName, playerType, chosenColorType);
        if(playerCreated) {
            disableButton(chosenColorType);
        }
        chosenColorType = null; //Damit nicht doppelt ausgewählt werden kann!
    }

    /**
     * user wants to create an Game
     *
     * @param actionEvent is the triggered event wenn the button is pressed
     */
    public void onCreateGameButtonAction(ActionEvent actionEvent) {
        LinkedList<Player> players = mainMenuViewController.getMainController().getPlayerController().getPlayers();
        boolean shuffleCards = checkBoxMixCards.isSelected();
        String csvImportPath = tfCSVFile.getText();
        boolean[] settings = new boolean[]{
                checkBoxModeDynasty.isSelected(),
                checkBoxModeBigDuel.isSelected(),
                checkBoxModeHarmonie.isSelected(),
                checkBoxModeCastleMiddle.isSelected(),
                true, //GameLog
                false //Hints
        };
        boolean wasCreated = mainMenuViewController.getMainController().getGameController().createGame(players, shuffleCards, csvImportPath, settings);

        if (wasCreated) {
            mainMenuViewController.loadGameView();
            textFieldPlayerName.setText("");

            checkBoxHumanPlayer.setSelected(true);
            checkBoxAiEasy.setSelected(false);
            checkBoxAiMedium.setSelected(false);
            checkBoxAiHard.setSelected(false);

            checkBoxModeDynasty.setSelected(false);
            checkBoxModeHarmonie.setSelected(false);
            checkBoxModeCastleMiddle.setSelected(false);
            checkBoxModeBigDuel.setSelected(false);

            btBlue.setDisable(false);
            btYellow.setDisable(false);
            btGreen.setDisable(false);
            btRed.setDisable(false);

            tfCSVFile.setText("");
            lvPlayerList.getItems().clear();
            mainMenuViewController.getMainController().getPlayerController().setPlayers(null);

            mainMenuViewController.getMainController().getGameController().aiGame();
        }
    }

    /**
     * user wants to leave the creating view an go back to the Main Menu
     *
     * @param actionEvent
     */
    public void onBackButtonAction(ActionEvent actionEvent) {
        LinkedList<Player> players = mainMenuViewController.getMainController().getPlayerController().getPlayers();
        String path = getClass().getResource("/view/resources/sound/Cansel1.wav").toExternalForm();
        Media media = new Media(path);
        mediaPlayer = new AudioClip(media.getSource());
        mediaPlayer.play();
        mediaPlayer.setVolume(0.15);
        if(players != null) {
            if(!players.isEmpty()) {
                players.clear();
                refreshPlayerList();
            }
        }
        textFieldPlayerName.clear();
        btBlue.setDisable(false);
        btYellow.setDisable(false);
        btGreen.setDisable(false);
        btRed.setDisable(false);
        //When game controller exits the current game is finished (set to null)
        mainMenuViewController.getMainController().getKingDomino().setCurrentGame(null);
        mainMenuViewController.returnToMainMenu();
    }

    /**
     * selects that the player a human player ist
     */

    public void onCheckBoxHumanPlayerRadioButtonAction() {
        checkBoxHumanPlayer.setToggleGroup(group);
        checkBoxHumanPlayer.setSelected(true);
        checkBoxAiEasy.setSelected(false);
        checkBoxAiMedium.setSelected(false);
        checkBoxAiHard.setSelected(false);
    }

    /**
     * selects only "easy AI" button
     */
    public void onCheckBoxAiEasyRadioButtonAction() {
        checkBoxAiEasy.setToggleGroup(group);
        checkBoxAiEasy.setSelected(true);
        checkBoxHumanPlayer.setSelected(false);
        checkBoxAiMedium.setSelected(false);
        checkBoxAiHard.setSelected(false);
    }

    /**
     * selects only "medium AI" button
     */
    public void onCheckBoxAiMediumRadioButtonAction() {
        checkBoxAiMedium.setToggleGroup(group);
        checkBoxAiMedium.setSelected(true);
        checkBoxAiEasy.setSelected(false);
        checkBoxAiHard.setSelected(false);
        checkBoxHumanPlayer.setSelected(false);
    }

    /**
     * selects only "hard AI" button
     */
    public void onCheckBoxAiHardRadioButtonAction() {
        checkBoxAiHard.setToggleGroup(group);
        checkBoxAiHard.setSelected(true);
        checkBoxAiEasy.setSelected(false);
        checkBoxAiMedium.setSelected(false);
        checkBoxHumanPlayer.setSelected(false);
    }
    /**
     * shows error massage (Alert)
     * @param msg - the massage
     */

    public void showPlayerControllerError(String msg) {
        String path = getClass().getResource("/view/resources/sound/Failure1.wav").toExternalForm();
        Media media = new Media(path);
        mediaPlayer = new AudioClip(media.getSource());
        mediaPlayer.play();
        mediaPlayer.setVolume(0.15);
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setTitle(msg);
        error.setContentText(msg);
        error.showAndWait();
    }
    /**
     * refreshes the "Playerlist" Listview in the GUI
     */
    @Override
    public void refreshPlayerList() {
        LinkedList<Player> players = mainMenuViewController.getMainController().getPlayerController().getPlayers();
        if(players != null) {
            lvPlayerList.getItems().clear();
            for (Player player : players) {
                lvPlayerList.getItems().add(player.toString());
            }
        } else {
            lvPlayerList.getItems().clear();
        }

    }

    /**
     * sets the MainMenuViewController
     *
     * @param mmvc - MainMenuViewController
     */
    public void setMainMenuViewController(MainMenuViewController mmvc) {
        this.mainMenuViewController = mmvc;
        checkBoxHumanPlayer.setSelected(true);
    }

    public void chooseCSVImport(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
        try {
            File file = new File(mainMenuViewController.getMainController().getIOController().getDataPath()).getCanonicalFile();
            mainMenuViewController.getMainController().getIOController().checkFolder(file.toString());
            fileChooser.setInitialDirectory(file);
            String filename = fileChooser.showOpenDialog(mainMenuViewController.getStage()).getPath();
            tfCSVFile.setText(filename);
        } catch (Exception e) {
        }
    }

    /**
     * Sets the default csv-save path into the GUI.
     */
    public void getDefaultSavePath() {
        String path = mainMenuViewController.getMainController().getIOController().getDataPath().concat("\\card_stack_import.csv");
        File file = new File(path);

        if (file.exists()) {
            try {
                tfCSVFile.setText(file.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        textFieldPlayerName.requestFocus();
    }

    /**
     * this method means only assigning a color to only one player
     * @param actionEvent on click
     */
    public void selectColour(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        String id = btn.getId();

        if (id.equals("btBlue")) {
            chosenColorType = ColourType.BLUE;
        } else if (id.equals("btYellow")) {
            chosenColorType = ColourType.YELLOW;
        } else if (id.equals("btGreen")) {
            chosenColorType = ColourType.GREEN;
        } else if (id.equals("btRed")) {
            chosenColorType = ColourType.RED;
        }
    }

    /**
     * Disable the choosen color buttons.
     */
    private void disableButton(ColourType colourType) {
        if(colourType == ColourType.BLUE) {
            btBlue.setDisable(true);
        } else if(colourType == ColourType.YELLOW) {
            btYellow.setDisable(true);
        } else if(colourType == ColourType.GREEN) {
            btGreen.setDisable(true);
        } else if(colourType == ColourType.RED) {
            btRed.setDisable(true);
        }
    }

    /**
     * Enabless the choosen color buttons.
     */
    private void enableButton(ColourType colourType) {
        if(colourType == ColourType.BLUE) {
            btBlue.setDisable(false);
        } else if(colourType == ColourType.YELLOW) {
            btYellow.setDisable(false);
        } else if(colourType == ColourType.GREEN) {
            btGreen.setDisable(false);
        } else if(colourType == ColourType.RED) {
            btRed.setDisable(false);
        }
        chosenColorType = null;
    }

    /**
     * Removes the selected Player from the playerlist
     * @param actionEvent
     */
    public void onRemovePlayerButtonAction(ActionEvent actionEvent) {
        String playerStr = lvPlayerList.getSelectionModel().getSelectedItem();
        Player selectedPlayer = null;
        for (Player player: mainMenuViewController.getMainController().getPlayerController().getPlayers()) {
            if(player.toString().equals(playerStr)) {
                selectedPlayer = player;
            }
        }
        if(mainMenuViewController.getMainController().getPlayerController().removePlayer(selectedPlayer)) {
            enableButton(selectedPlayer.getColourType());
        }

        refreshPlayerList();
    }
}
