    /**
     * this method add a Subscription for the current employee
     *
     * @param action - on Click
     */
    @FXML
    public void onAddButtonAction(ActionEvent action) {
        if (!(toOperateEmployee == null)) {
            Food selectedFood = allSubscriptions.getSelectionModel().getSelectedItem();
            for (Purchase purchase : subscriptions) {
                if(purchase.getFood().equals(selectedFood)) {
                    showEmployeeError("Abonnement bereits vorhanden!");
                    return;
                }
            }
            toOperateEmployee.addTransaction(new Purchase(selectedFood, false, LocalDateTime.now()));
            refreshSubscriptions();
        }
        refreshEmployees();
    }

    /**
     * remove a Subscription
     *
     * @param action - on Click
     */
    @FXML
    public void onRemoveButtonAction(ActionEvent action) {
        if (!(toOperateEmployee == null)) {
            Food selectedFood = employeeSubscriptions.getSelectionModel().getSelectedItem();
            for (Purchase purchase : subscriptions) {
                if(purchase.getFood().equals(selectedFood)) {
                    purchase.setReturned(true);
                    subscriptions.remove(purchase);
                    break;
                }
            }
            refreshSubscriptions();
        }
        refreshEmployees();
    }

    /**
     * HistoryViewController shows the history for an employee
     *
     * @param action - on Click
     */
    @FXML
    public void onHistoryButtonAction(ActionEvent action) {
        try {
            if (!(toOperateEmployee == null)) {
                String[] nameSaver = toOperateEmployee.getName().split(" ", 3);
                String toSaveAs = "";
                for (String namePart : nameSaver) {
                    if (!namePart.isEmpty()) {
                        if (toSaveAs.isEmpty()) {
                            toSaveAs = namePart;
                        } else {
                            toSaveAs = toSaveAs + "_" + namePart;
                        }
                    }
                }
                File file = new File("./savedHistories/" + toSaveAs + ".csv");
                mainWindowViewController.getMainController().getIOController().saveHistory(file, toOperateEmployee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onEmployeeListButtonAction(ActionEvent action){
        File file = new File("./employeeList/" + "Mitarbeiterliste" + ".csv");
        Collection<Employee> employees = mainWindowViewController.getMainController().getRegister().getEmployees();
        String sep = System.getProperty("line.separator");
        StringBuilder builder = new StringBuilder();
        char dot = ',';
        try (PrintWriter writer = new PrintWriter(file)) {
            builder.append("ID");
            builder.append(dot);
            builder.append("Name");
            builder.append(dot);
            builder.append("Admin").append(sep);
            for(Employee employee : employees){
                builder.append(employee.getEmployeeID());
                builder.append(dot);
                builder.append(employee.getName());
                builder.append(dot);
                if(employee.getManaging()){
                    builder.append("Ja").append(sep);
                }
                else {
                    builder.append("Nein").append(sep);
                }

            }
            writer.write(builder.toString());
        }
        catch(FileNotFoundException f) {
            f.getStackTrace();
        }
    }

    /**
     * @see view.aui.EmployeeManageAUI#refreshEmployees()
     */
    public void refreshEmployees() {
        if (!(toOperateEmployee == null)) {                      //falls er nicht gerade gelÃ¶scht wurde
            currentPerson.setText(toOperateEmployee.getName());
            setNameOld.setText(toOperateEmployee.getName());
            setNameNew.setText("");
            charge.setText("");
            employeeID.setText("");
            if (toOperateEmployee.getManaging()) {
                adminTrue.setSelected(true);
                adminFalse.setSelected(false);
            } else {
                adminTrue.setSelected(false);
                adminFalse.setSelected(true);
            }
            Iterator<Transaction> transIt = toOperateEmployee.getTransactions().iterator();
            int flaschen = 0;
            int chargeSum = 0;
            while (transIt.hasNext()) {
                Transaction currentT = transIt.next();
                if (currentT.getIsPurchase()) {
                    Purchase currentP = (Purchase) currentT;
                    chargeSum -= currentP.getFood().getPrice();
                    if (!currentP.getReturned() && currentP.getFood().getCashReturn() != 0) {
                        flaschen += 1;
                    }
                } else {
                    chargeSum += currentT.getValue();
                }
            }
            refund.setText(String.valueOf(flaschen));
            double chargeText = chargeSum;
            chargeText = chargeText / 100;
            currentCharge.setText(String.valueOf(chargeText).concat(" \u20ac"));
        } else {
            currentPerson.setText("");
            setNameOld.setText("");
            employeeID.setText("");
            adminFalse.setSelected(false);
            adminTrue.setSelected(false);
            refund.setText("");
            currentCharge.setText("");
        }
    }
