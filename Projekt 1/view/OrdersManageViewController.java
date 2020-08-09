package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Food;
import model.Purchase;
import view.aui.OrdersAUI;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class can create orders and allows managing users to
 * increase the in-stock amount of a food. Furthermore the companies
 * empty bottles can be returned.
 * @author Marcel Bienia
 */
public class OrdersManageViewController extends AbstractViewController implements OrdersAUI {

	private MainWindowViewController mainWindowViewController;

	private Purchase selectedOrder;
	private Food selectedFood;

	public static final int NULL_OBJECT = 1;
	public static final int INVALID_DATE = 2;
	public static final int INVALID_VOLUME = 3;
	public static final int SUBSCRIPTION_ITEM = 4;

	@FXML
	private ListView<Purchase> lvOrders;
	@FXML
	private ListView<Food> lvDisplayFoods;
	@FXML
	private ListView<Food> lvEmpties;
	@FXML
	private Text tCurrentSelectedFood;
	@FXML
	public TextField tfSearchFood;
	@FXML
	private DatePicker datePicker;
	@FXML
	public TextField tfIncreaseAmount;

	/**
	 * initialize the list views
	 */
	public void init() {
		setFoodsListView();
		setOrdersListView();
		setEmptiesListView();
		setListViewOnClickListeners();
		setSearchBarListener();
	}

	/**
	 * set the food list view to the current state
	 * of the register food list
	 */
	private void setFoodsListView() {
		if(mainWindowViewController.getMainController().getRegister().getFoods() != null) {
			lvDisplayFoods.setItems(FXCollections.observableArrayList(
					mainWindowViewController.getMainController().getRegister().getFoods()));
		}
	}

	/**
	 * set the orders list view to the current state
	 * of the register orders list
	 */
	private void setOrdersListView() {
		if(mainWindowViewController.getMainController().getRegister().getOrders() != null) {
			lvOrders.setItems(FXCollections.observableArrayList(
					mainWindowViewController.getMainController().getRegister().getOrders()));
		}
	}

	/**
	 * set the empties list view to the current state
	 * of the register empties list
	 */
	private void setEmptiesListView() {
		if(mainWindowViewController.getMainController().getRegister().getEmpties() != null) {
			lvEmpties.setItems(FXCollections.observableArrayList(
					mainWindowViewController.getMainController().getRegister().getEmpties()));
		}
	}

	/**
	 * set the onClick listeners for the list views
	 */
	private void setListViewOnClickListeners() {
		lvOrders.setOnMouseClicked(event -> {
			selectedOrder = lvOrders.getSelectionModel().getSelectedItem();
		});
		lvDisplayFoods.setOnMouseClicked(event -> {
			selectedFood = lvDisplayFoods.getSelectionModel().getSelectedItem();
			updateSelectedFoodTextField();
		});
	}

	private void updateSelectedFoodTextField() {
		int frequency = Collections.frequency(mainWindowViewController
				.getMainController().getRegister().getStock(), selectedFood);
		if(selectedFood.getIsSubscription()) {
			tCurrentSelectedFood.setText(selectedFood.getName() + "(Abo)");
		} else {
			tCurrentSelectedFood.setText(selectedFood.getName() + " (" + frequency + "x)");
		}
	}

	/**
	 * set onChange listener for search field
	 */
	private void setSearchBarListener() {
		tfSearchFood.textProperty().addListener((observable, oldValue, newValue) -> {
			Stream<Food> filteredFoods = mainWindowViewController.getMainController().getRegister().getFoods()
					.stream().filter(food -> food.getName().contains(newValue));
			lvDisplayFoods.setItems(FXCollections
					.observableArrayList(filteredFoods.collect(Collectors.toList())));
		});
	}

	/**
	 * when this button is pressed the user wants to add a new order to the orders list
	 * the parameter of the new order has to be read from the view text fields an then a new order can be added
	 * @param event  - auf Knopfdruck
	 */
    public void onNewOrderButtonAction(ActionEvent event) {
		LocalDate deliveryDate = datePicker.getValue();
		mainWindowViewController.getMainController().getOrdersController().newOrder(selectedFood, deliveryDate);
	}

    /**
     * when this method is called, all empties have been picked up by the Retailer
     * the clear empties list from the register has to be called
     * @param event - auf Knopfdruck
     */
	public void onClearEmptiesButtonAction(ActionEvent event) {
		mainWindowViewController.getMainController().getOrdersController().clearEmpties();
	}

	/**
	 * when this button is called an order has arrived.
	 * all items from this order have to be added to the stock
	 * if an item from this order is unknown to the register, a new food object has to be generated and add to the foods list
	 * @param event - auf Knopfdruck
	 */
	public void onIncreaseItemAmountButtonAction(ActionEvent event) {
		if(selectedFood.getIsSubscription()) {
			showOrderError(SUBSCRIPTION_ITEM);
		} else {
			mainWindowViewController.getMainController().getOrdersController()
					.increaseItemAmount(selectedFood, tfIncreaseAmount.getText());
			updateSelectedFoodTextField();
		}
	}

	public void onRemoveOrderButtonAction(ActionEvent actionEvent) {
		if(selectedOrder != null) {
			mainWindowViewController.getMainController().getRegister().getOrders().remove(selectedOrder);
			refreshOrders();
		}
	}

	/**
	 * refreshes the orders list (lvOrders), after changes have been made
	 * implemented method
	 */
	public void refreshOrders() {
		setOrdersListView();
		mainWindowViewController.setDeliveryDateText();
	}

	/**
	 * refreshes the empties list (lvEmpties), after changes have been made
	 * implemented method
	 */
	public void refreshEmpties() {
		setEmptiesListView();
	}

	/**
	 * method called when order error occurred
	 * implemented method
	 */
	public void showOrderError(final int errorCode) {
		switch(errorCode) {
			case 1:
				handleNullObjectError();
				break;
			case 2:
				handleInvalidDateError();
				break;
			case 3:
				handleOrderVolumeError();
				break;
			case 4:
				handleSubscriptionIncrementError();
				break;
		}
	}

	public void handleNullObjectError() {
		Alert error = new Alert(Alert.AlertType.ERROR);
		error.setTitle("Fehlerhafte Auswahl");
		error.setContentText("Bitte waehle Lebensmittel aus der Liste!");
		error.showAndWait();
	}

	private void handleInvalidDateError() {
		Alert error = new Alert(Alert.AlertType.ERROR);
		error.setTitle("Fehlerhaftes Datum");
		error.setContentText("Bitte waehle ein Datum, dass in der Zukunkt liegt!");
		error.showAndWait();
	}

	private void handleOrderVolumeError() {
		Alert error = new Alert(Alert.AlertType.ERROR);
		error.setTitle("Fehlerhafte Liefersanzahl");
		error.setContentText("Lieferanzahl muss zwischen 1 und 1000 liegen!");
		tfIncreaseAmount.setText("");
		error.showAndWait();
	}

	private void handleSubscriptionIncrementError() {
		Alert error = new Alert(Alert.AlertType.ERROR);
		error.setTitle("Fehlerhaftes Lebensmittel");
		error.setContentText("Bestand abonnierbarer Lebensmittel unveraenderbar!");
		error.showAndWait();
	}

	public void setMainWindowViewController(MainWindowViewController mainWindowViewController) {
		this.mainWindowViewController = mainWindowViewController;
	}
}
