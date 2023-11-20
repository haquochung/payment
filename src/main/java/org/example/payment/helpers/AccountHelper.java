package org.example.payment.helpers;

import org.example.payment.model.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AccountHelper extends DataHelper {
    private static final int ID_COLUMN_INDEX = 0;
    private static final int NAME_COLUMN_INDEX = 1;
    private static final int PASSWORD_COLUMN_INDEX = 2;
    private static final int DISPLAY_NAME_COLUMN_INDEX = 3;

    private static final String[] COLUMNS = new String[]{"Account Id", "Account Name", "Password", "Display Name"};

    private static final String FILE_NAME = "account.csv";
    private final Map<Integer, Account> cache = new HashMap<>();

    public AccountHelper() {
        try {
            getAccountList();
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

        Account account = new Account();
        account.setAccountId(Integer.parseInt(properties.get(ID_COLUMN_INDEX)));
        account.setAccountName(properties.get(NAME_COLUMN_INDEX));
        account.setPassword(properties.get(PASSWORD_COLUMN_INDEX));
        account.setDisplayName(properties.get(DISPLAY_NAME_COLUMN_INDEX));
        cache.put(account.getAccountId(), account);
    }

    private void getAccountList() throws Exception {
        cache.clear();
        loadData();
    }

    public Account findAccount(String name, String password) {
        return cache.values().stream().filter((account) -> Objects.equals(account.getAccountName(), name)
                && Objects.equals(account.getPassword(), password)).findFirst().orElse(null);
    }
}
