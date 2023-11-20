package org.example.payment;

import org.example.payment.exception.ExceptionHandler;
import org.example.payment.model.Command;
import org.example.payment.service.*;

import java.util.Arrays;
import java.util.Scanner;

public class App {

    public static final Scanner SCANNER = new Scanner(System.in);

    private static HelpService helpService;
    private static AccountService accountService;
    private static WalletService walletService;
    private static BillService billService;

    private static PaymentService paymentService;

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

        initService();

        run();
    }

    private static void run() {

        System.out.println("!!!Welcome to Bill Payment Service!!!");
        System.out.println("Please input your request or type \"HELP\" to get more information");

        String input;
        Command command;
        String[] argument = new String[0];
        String[] arr;

        while (true) {
            System.out.print("payment>");
            if (SCANNER.hasNextLine()) {
                try {
                    input = SCANNER.nextLine();
                    if (input.isEmpty()) {
                        continue;
                    }

                    try {
                        arr = input.split(" ");
                        command = Command.valueOf(arr[0]);
                        if (arr.length > 1) {
                            argument = Arrays.copyOfRange(arr, 1, arr.length);
                        }
                    } catch (Exception e) {
                        System.out.println("Sorry! We don't support your request.");
                        continue;
                    }

                    switch (command) {
                        case HELP -> helpService.executeCommand(command, argument);
                        case LOGIN, LOGOUT, SIGNUP -> accountService.executeCommand(command, argument);
                        case LIST_BILL, DUE_DATE -> billService.executeCommand(command, argument);
                        case CASH_IN -> walletService.executeCommand(command, argument);
                        case LIST_PAYMENT -> paymentService.executeCommand(command, argument);
                        case EXIT -> {
                            System.out.println("Good bye!");
                            System.exit(0);
                        }
                    }
                } catch (Exception e) {
                    if (e.getMessage() != null && !e.getMessage().isEmpty()) {
                        System.out.println(e.getMessage());
                    } else {
                        System.out.println("Sorry! We got some problems when processing your request");
                    }
                }
            } else {
                break;
            }
        }

        System.out.println("Exit application because cannot read input from console. Good bye!");
    }

    private static void initService() {
        helpService = new HelpService();
        accountService = new AccountService();
        walletService = new WalletService();
        billService = new BillService();
        paymentService = new PaymentService();

        accountService.addPropertyChangeListener(helpService);
        accountService.addPropertyChangeListener(walletService);
        accountService.addPropertyChangeListener(billService);

        walletService.addPropertyChangeListener(billService);

        billService.addPropertyChangeListener(walletService);
        billService.addPropertyChangeListener(paymentService);

    }
}
