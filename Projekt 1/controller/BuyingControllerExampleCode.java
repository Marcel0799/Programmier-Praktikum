    /**
     * void method
     * This method is required to add a transaction to an employee. Then that is added as a purchase to the transaction.
     * The algorithm checks how often the given food object is available in the stock.
     * If there are enough food objects in the stock, the object is removed from the warehouse and handed over to the
     * employee as a purchase using the addTransaction method and then passed to the register using the add method.
     * Finally, refreshProducts is called and the stock is updated.
     * @param cartItems items list
     */
    public void buyFood(Collection<Tuple<Food, Integer>> cartItems) {
        Collection<Transaction> transactions = mainController.getRegister().getCurrentEmployee().getTransactions();
        int kontostand = berechneKontostand(transactions);
        if (cartItems.size() <= 0) {
            throw new IllegalArgumentException("Invalid number of elements");
        }
        Register register = mainController.getRegister();
        Collection<Food> stock = register.getStock();
        Food currentFood;
        int currentAmount;
        int counter;
        int kaufPreisInsgesamt = 0;
        for (Tuple<Food, Integer> item : cartItems) {
            currentFood = item.getFirst();
            currentAmount = item.getSecond();
            counter = Collections.frequency(stock, item.getFirst());
            if (currentAmount > counter) {
                buyingAUI.showBuyingError("Menge nicht ausreichend von " + currentFood.getName());
            }
            kaufPreisInsgesamt = kaufPreisInsgesamt + currentFood.getPrice() * currentAmount;
        }        
        if(kontostand - kaufPreisInsgesamt < mainController.getRegister().getDebtLimit()) {
            buyingAUI.showBuyingError("sie haben nicht genug guthaben auf ihren Konto");
            throw new IllegalArgumentException();
        }       
        for (Tuple<Food, Integer> item : cartItems) {
            currentFood = item.getFirst();
            currentAmount = item.getSecond();
            for (int i = 0; i < currentAmount; i++) {
                register.buyFood(currentFood);
            }
        }
        buyingAUI.refreshProducts();
    }
