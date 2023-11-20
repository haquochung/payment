package org.example.payment.service;

import org.example.payment.App;
import org.example.payment.helpers.WalletHelper;
import org.example.payment.model.Account;
import org.example.payment.model.Bill;
import org.example.payment.model.Command;
import org.example.payment.model.Wallet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;

public class WalletService extends ObservableService implements IService, PropertyChangeListener {
    WalletHelper walletHelper;

    public WalletService() {
        walletHelper = new WalletHelper();
    }

    @Override
    public void executeCommand(Command command, String... args) throws Exception {
        if (account == null) {
            System.out.println("Sorry! We don't support your request.");
            return;
        }
        switch (command) {
            case CASH_IN -> deposit(args);
            default -> throw new Exception();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(LOGIN_STATE)) {
            this.account = (Account) evt.getNewValue();
        } else if (evt.getPropertyName().equals(Command.PAY.name())) {
            withdraw((Bill) evt.getNewValue());
        }
    }

    private void deposit(String... args) {
        try {
            long balance = 0;
            if (args.length > 0) {
                try {
                    balance = Long.parseLong(args[0]);
                } catch (Exception e) {
                    System.out.println("Sorry! Your balance is invalid");
                    return;
                }
            }
            if (balance == 0) {
                System.out.println("Sorry! Your balance is invalid");
                return;
            }

            List<Wallet> walletList = walletHelper.findWalletByAccount(account.getAccountId());
            if (walletList.isEmpty()) {
                System.out.println("Sorry! Your wallet not found");
                return;
            }

            int walletId;
            if (walletList.size() > 1) {
                System.out.println("Please select the wallet from the list below you want to deposit into");
                walletHelper.printWalletList(walletList);
                String input;
                while (true) {
                    System.out.print("Wallet: ");
                    if (App.SCANNER.hasNextLine()) {
                        try {
                            input = App.SCANNER.nextLine();
                            if (Objects.equals(input, "EXIT")) {
                                return;
                            }
                            walletId = Integer.parseInt(input);
                            if (walletHelper.findWalletById(walletId) != null) {
                                break;
                            } else {
                                System.out.println("Sorry! Wallet Id Not Found. Type \"EXIT\" to exit this session");
                            }
                        } catch (Exception ignored) {
                            System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                        }
                    }
                }
            } else {
                walletId = walletList.get(0).getWalletId();
            }

            walletHelper.updateBalance(walletId, balance);
        } catch (Exception ignored) {
        }
    }

    private void withdraw(Bill bill) {
        //TODO Something
    }
}
