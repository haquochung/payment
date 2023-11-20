package org.example.payment.helpers;

import org.example.payment.model.Wallet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletHelper extends DataHelper {
    private static final int ID_COLUMN_INDEX = 0;
    private static final int BALANCE_COLUMN_INDEX = 1;
    private static final int ACCOUNT_ID_COLUMN_INDEX = 2;

    private static final String[] COLUMNS = new String[]{"Wallet Id", "Balance", "Account Id"};

    private static final String FILE_NAME = "wallet.csv";
    private final Map<Integer, Wallet> cache = new HashMap<>();

    public WalletHelper() {
        try {
            getWalletList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }

    @Override
    protected String getFileName() {
        return FILE_NAME;
    }

    @Override
    protected void convertToObject(List<String> properties) throws Exception {
        int columnCount = getColumns().length;
        if (properties.size() != columnCount) {
            throw new Exception();
        }

        Wallet wallet = new Wallet();
        wallet.setWalletId(Integer.parseInt(properties.get(ID_COLUMN_INDEX)));
        wallet.setBalance(Long.parseLong(properties.get(BALANCE_COLUMN_INDEX)));
        wallet.setAccountId(Integer.parseInt(properties.get(ACCOUNT_ID_COLUMN_INDEX)));
        cache.put(wallet.getWalletId(), wallet);
    }

    public void printWalletList(List<Wallet> walletList) {
        if (walletList.isEmpty()) {
            System.out.println("Sorry! We don't support your request.");
            return;
        }

        System.out.printf("%-10s %-15s%n", COLUMNS[ID_COLUMN_INDEX], COLUMNS[BALANCE_COLUMN_INDEX]);
        for (Wallet wallet : walletList) {
            System.out.printf("%-10s %-15s%n", wallet.getWalletId(), wallet.getBalance());
        }
    }

    private void getWalletList() throws Exception {
        cache.clear();
        loadData();
    }

    public Wallet findWalletById(int walletId) {
        if (cache.containsKey(walletId)) {
            return cache.get(walletId);
        }
        return null;
    }

    public List<Wallet> findWalletByAccount(int accountId) {
        return cache.values().stream().filter(wallet -> wallet.getAccountId() == accountId).toList();
    }

    public void updateBalance(int walletId, long balance) throws Exception {
        if (!cache.containsKey(walletId)) {
            System.out.println("Sorry! Your wallet not found");
            return;
        }

        Wallet wallet = cache.get(walletId);
        long newBalance = wallet.getBalance() + balance;
        wallet.setBalance(newBalance);

        saveData(cache.values());

        System.out.printf("Your Wallet %s available balance: %s%n", walletId, newBalance);
    }
}
