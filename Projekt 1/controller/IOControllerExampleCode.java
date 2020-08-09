    /**
     * save History/Trancations from an employee to the file.
     *
     * @param file     to save the history
     * @param employee its transaction history
     * @throws java.io.IOException - wenn die Methode fehlschlÃ¤gt
     */
    public void saveHistory(File file, Employee employee) throws IOException {
        Collection<Transaction> transactions = employee.getTransactions();
        String sep = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        int guthaben = 0;
        try (PrintWriter writer = new PrintWriter(file)) {
            stringBuilder.append(DATUM);
            stringBuilder.append(DOT);
            stringBuilder.append(PRODUKT);
            stringBuilder.append(DOT);
            stringBuilder.append(PREIS);
            stringBuilder.append(DOT);
            stringBuilder.append(GUTHABEN).append(sep);
            for (Transaction transaction : transactions) {
                if (transaction.getIsPurchase()) {
                    stringBuilder.append(transaction.getTransactionDate());
                    stringBuilder.append(DOT);
                    stringBuilder.append(((Purchase) transaction).getFood().getName());
                    stringBuilder.append(DOT);
                    stringBuilder.append(transaction.getValue());
                    stringBuilder.append(DOT);
                    guthaben -= ((Purchase) transaction).getFood().getPrice();
                    stringBuilder.append(guthaben).append(sep);

                } else {
                    guthaben = buildString(sep, stringBuilder, guthaben, transaction);

                }
            }
            writer.write(stringBuilder.toString());

        }


    }

    private int buildString(String sep, StringBuilder stringBuilder, int guthaben, Transaction transaction) {
        String desc;
        Charge transakt = (Charge) transaction;
        desc = transakt.getDescription();
        if (desc.contains(RETURN)) {
            guthaben += transaction.getValue();
            stringBuilder.append(transaction.getTransactionDate());
            stringBuilder.append(DOT);
            stringBuilder.append(RUECKGABGE);
            stringBuilder.append(DOT);
            stringBuilder.append(transaction.getValue());
            stringBuilder.append(DOT);
            stringBuilder.append(guthaben).append(sep);
        } else if (desc.contains(CHARGE) || desc.contains(PFAND)) {
            guthaben += transaction.getValue();
            stringBuilder.append(transaction.getTransactionDate());
            stringBuilder.append(DOT);
            stringBuilder.append(AUFLADUNG);
            stringBuilder.append(DOT);
            stringBuilder.append(transaction.getValue());
            stringBuilder.append(DOT);
            stringBuilder.append(guthaben).append(sep);
        } else if (desc.contains(GUTHABEN)) {
            guthaben += transaction.getValue();
            stringBuilder.append(transaction.getTransactionDate());
            stringBuilder.append(DOT);
            stringBuilder.append(AUFLADUNG);
            stringBuilder.append(DOT);
            stringBuilder.append(transaction.getValue());
            stringBuilder.append(DOT);
            stringBuilder.append(guthaben).append(sep);
        }
        else
        {
            throw new IllegalArgumentException(WEDER_RETURN_NOCH_CHARGE);
        }
        return guthaben;
    }