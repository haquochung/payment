package org.example.payment.service;

import org.example.payment.helpers.AccountHelper;
import org.example.payment.model.Account;
import org.example.payment.model.Command;


public class AccountService extends ObservableService implements IService {

    private final AccountHelper accountHelper;

    public AccountService() {
        accountHelper = new AccountHelper();
    }

    @Override
    public void executeCommand(Command command, String... args) throws Exception {
        switch (command) {
            case LOGIN -> login(args);
            case LOGOUT -> logout();
            default -> throw new Exception();
        }
    }

    private void login(String... args) {
        Account account = null;
        try {
            String name = "";
            String password = "";
            if (args.length > 1) {
                name = args[0];
                password = args[1];
            } else {
                System.out.println("Please input name and password to login!");
            }
            account = accountHelper.findAccount(name, password);
        } catch (Exception ignored) {
        }

        if (account == null) {
            System.out.println("Sorry! Not found an account with such name.");
        } else {
            System.out.printf("Welcome %s%n", account.getDisplayName());
            updateLoginState(account);
        }
    }

    private void logout() throws Exception {
        String displayName = "";
        if (account != null) {
            displayName = account.getDisplayName();
        }
        try {
            updateLoginState(null);
        } catch (Exception e) {
            throw new Exception();
        }
        System.out.printf("Goodbye %s%n", displayName);
    }
}
