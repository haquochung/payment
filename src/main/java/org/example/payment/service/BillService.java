package org.example.payment.service;

import org.example.payment.App;
import org.example.payment.helpers.BillHelper;
import org.example.payment.model.Account;
import org.example.payment.model.Bill;
import org.example.payment.model.Command;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.example.payment.model.Command.PAY;

public class BillService extends ObservableService implements IService, PropertyChangeListener {
    BillHelper billHelper;

    public BillService() {
        billHelper = new BillHelper();
    }

    @Override
    public void executeCommand(Command command, String... args) throws Exception {
        if (account == null) {
            System.out.println("Sorry! We don't support your request.");
            return;
        }
        switch (command) {
            case LIST_BILL -> billHelper.printBillList(account.getAccountId());
            case CREATE_BILL -> createBill();
            case PAY -> payBill(args);
            case DUE_DATE -> billHelper.printBillList(account.getAccountId(), true);
            default -> throw new Exception();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(LOGIN_STATE)) {
            this.account = (Account) evt.getNewValue();
        } else if (evt.getPropertyName().equals(WITHDRAW_STATE)) {
            createPayment((String) evt.getNewValue());
        }
    }

    private void createBill() throws Exception {
        System.out.println("Please fill in the information below to create bill");
        System.out.println("Type \"EXIT\" to exit this session");

        Bill bill = new Bill();
        String input;
        while (true) {
            System.out.print("Type:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    bill.setType(input);
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }
        
        while (true) {
            System.out.print("Amount:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    bill.setAmount(Long.parseLong(input));
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }

        while (true) {
            System.out.print("Due Date:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    new Date(input); //try convert to date
                    bill.setDueDate(input);
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }

        while (true) {
            System.out.print("Provider:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    bill.setProvider(input);
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }

        bill.setAccountId(account.getAccountId());

        billHelper.createBill(bill);
    }

    private void updateBill(String... args) throws Exception {
        if (args.length == 0) {
            System.out.println("Please input Bill No you want to update");
            return;
        }

        Bill bill;
        try {
            int billNo = Integer.parseInt(args[0]);
            bill = billHelper.findByBillNo(billNo);
            if (bill == null) {
                System.out.println("Sorry! Not found a bill with such id");
                return;
            }
        } catch (Exception e) {
            System.out.println("Sorry! Bill No is invalid. Bill No must be number");
            return;
        }

        System.out.println("Please fill in the information below to update bill");
        System.out.println("Type \"EXIT\" to exit this session");

        String input;
        while (true) {
            System.out.print("Type:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    bill.setType(input);
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }

        while (true) {
            System.out.print("Amount:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    bill.setAmount(Long.parseLong(input));
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }

        while (true) {
            System.out.print("Due Date:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    new Date(input); //try convert to date
                    bill.setDueDate(input);
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }

        while (true) {
            System.out.print("Provider:");
            if (App.SCANNER.hasNextLine()) {
                try {
                    input = App.SCANNER.nextLine();
                    if (Objects.equals(input, "EXIT")) {
                        return;
                    }
                    bill.setProvider(input);
                    break;
                } catch (Exception ignored) {
                    System.out.println("Your input is invalid. Type \"EXIT\" to exit this session");
                }
            }
        }

        billHelper.updateBill(bill);
    }

    private void deleteBill(String... args) {
        if (args.length == 0) {
            System.out.println("Please input Bill No you want to delete");
            return;
        }

    }

    private void payBill(String... args) {
        if (args.length == 0) {
            System.out.println("Please input Bill No you want to pay");
            return;
        }

        List<Bill> billList = new ArrayList<>();
        try {
            for (String arg : args) {
                int billNo = Integer.parseInt(arg);
                Bill bill = billHelper.findByBillNo(billNo);
                if (bill == null) {
                    System.out.println("Sorry! Not found a bill with such id");
                    return;
                }
                billList.add(bill);
            }
        } catch (Exception e) {
            System.out.println("Sorry! Bill No is invalid. Bill No must be number");
            return;
        }

        billList.sort((b1, b2) -> {
            Date dd1 = new Date(b1.getDueDate());
            Date dd2 = new Date(b2.getDueDate());
            if (dd1.after(dd2)) {
                return 1;
            } else if (dd1.before(dd2)) {
                return -1;
            }
            return 0;
        });

        for (Bill bill : billList) {
            support.firePropertyChange(Command.PAY.name(), null, bill);
        }
    }

    private void createPayment(String... args) {
        support.firePropertyChange(PAYMENT_STATE, null, args);
    }
}
