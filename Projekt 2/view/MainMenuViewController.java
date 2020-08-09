package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import view.aui.MainAUI;

/**
 * @author Marcel Bienia
 */
public class MainMenuViewController implements MainAUI {

    //used to show other views
    private Stage primaryStage;

    //used to return to this view
    private Scene sceneOfThisMMVC;

    //Controller
    private final MainController mainController;
    private CreateGameViewController createGameViewController;
    private HighscoreViewController highscoreViewController;
    private LoadGameViewController loadGameViewController;
    private GameViewController gameViewController;

    //Views
    private Scene createGameScene;
    private Scene highscoreScene;
    private Scene loadGameScene;
    private Scene gameScene;

    private AudioClip mediaPlayer;


    /**
     * constructor
     */
    public MainMenuViewController() {
        loadViews();
        mainController = new MainController();
        setAUIs();
    }

    /**
     * Method for loading the views
     */
    private void loadViews() {
        loadCreateGameViewController();
        loadHighscoreViewController();
        loadLoadGameViewController();
        loadGameBoardViewController();
    }

    /**
     * Method to set all AUIs
     */
    private void setAUIs() {
        mainController.setMainAUI(this);
        mainController.getGameController().setGameAUI(gameViewController);
        mainController.getGameController().setPlayerAUI(createGameViewController);
        mainController.getIOController().setLoadGamesAUI(loadGameViewController);
        mainController.getPlayerController().setPlayerAUI(createGameViewController);
        mainController.getPlayerController().setGameAUI(gameViewController);
        mainController.getAIController().setGameAUI(gameViewController);
        mainController.getGridController().setGameAUI(gameViewController);
    }

    /**
     * load the CreateGameViewController
     */
    private void loadCreateGameViewController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/SpielEinrichtenView.fxml"));
            createGameScene = new Scene(fxmlLoader.load());
            createGameViewController = fxmlLoader.getController();
            createGameViewController.setMainMenuViewController(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load the HighScoreViewController
     */
    private void loadHighscoreViewController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/highcore.fxml"));
            highscoreScene = new Scene(fxmlLoader.load());
            highscoreViewController = fxmlLoader.getController();
            highscoreViewController.setMainMenuViewController(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load the LoadGameViewController
     */
    private void loadLoadGameViewController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/Load_Game.fxml"));
            loadGameScene = new Scene(fxmlLoader.load());
            loadGameViewController = fxmlLoader.getController();
            loadGameViewController.setMainMenuViewController(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load the GameBoardViewController
     */
    private void loadGameBoardViewController() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/GameGUI.fxml"));
            gameScene = new Scene(fxmlLoader.load());
            gameViewController = fxmlLoader.getController();
            gameViewController.setMainMenuViewController(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * sets the curent stage, so the mmvc can show new windows
     * @param primaryStage is the stage
     * @param scene is the scene of this MMVC so other views can return
     */
    public void setStageAndScene(Stage primaryStage,Scene scene) {
        this.primaryStage = primaryStage;
        this.sceneOfThisMMVC = scene;
    }

    /**
     * getter
     * @return primaryStage
     */
    public Stage getStage() {
        return primaryStage;
    }

    /**
     * getter for mainController
     * @return MainController
     */
    public MainController getMainController() {
        return mainController;
    }

    /**
     * user wants to create a new game, the create game View should appear
     * @param actionEvent is the event when the button is pressed
     */
    public void onNewGameButtonAction(ActionEvent actionEvent) {
        //loadCreateGameViewController();
        try {
            String path = getClass().getResource("/view/resources/sound/Decision2.wav").toExternalForm();
            Media media = new Media(path);
            mediaPlayer = new AudioClip(media.getSource());

            mediaPlayer.setVolume(0.15);
            mediaPlayer.play();

            primaryStage.setScene(createGameScene);
            primaryStage.show();
            createGameViewController.getDefaultSavePath();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * user wants to load an old game, the loadGameView should appear
     * @param actionEvent - on Button click
     */
    public void onLoadGameButtonAction(ActionEvent actionEvent) {
        loadGameViewController.loadExistingGames();
        try {
            primaryStage.setScene(loadGameScene);
            primaryStage.show();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * user wants to see the highscore list, the highscore View should appear
     * @param actionEvent is the event when the button is pressed
     */
    public void onShowHighscoreButtonAction(ActionEvent actionEvent) {
        try {
            primaryStage.setScene(highscoreScene);
            primaryStage.show();
            highscoreViewController.refreshHighscore();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * the user wants to leave the game, the game should terminate
     * @param actionEvent - on Button click
     */
    public void onEndGameButtonAction(ActionEvent actionEvent) {
        //System.out.println("End Button was Pressed");
        //TODO should the current game be saved before the game ends
        System.exit(0);
    }

    /**
     * return to the main menu screen
     */
    public void returnToMainMenu() {
        try {
            primaryStage.setScene(sceneOfThisMMVC);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * load the GameView
     */
    public void loadGameView() {
        try {
            gameViewController.setSceneLoader(gameScene);
            primaryStage.setScene(gameScene);
            primaryStage.show();

            gameViewController.startGame();
            gameViewController.setTipsActive(false);
            gameViewController.refreshCurrentPlayer();
            gameViewController.refreshChoice();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
