package org.example.payment.model;

/**
 * @System: Command
 * @Title: Bill
 * @Version: 1.0.0
 * @Author: HungHa
 * @CreateOn: 2023/11/19
 */
public enum Command {
    HELP("", "Display this help message", false),
    LOGIN("<user_name> <password>", "Log in your payment account", false),
    LOGOUT("", "Log out your payment account", true),
    SIGNUP("", "Sign up a new payment account", false),
    CASH_IN("<cash_amount>", "Deposit cash into your payment account", true),
    LIST_BILL("", "Display a list of your bills", true),
    CREATE_BILL("", "Create a new bill", true),
    UPDATE_BILL("<bill_no>", "Update your bill with bill no", true),
    DELETE_BILL("<bill_no>", "Delete your bill with bill no", true),
    PAY("<list_bill_no>", "Pay your bill with bill no", true),
    DUE_DATE("", "Display a list of your bills by due date", true),
    SEARCH_BILL_BY_PROVIDER("<provider>", "Display a list your bills by provider", true),
    SCHEDULE("<bill_no> <pay_date>", "Schedule your bill payments", true),
    LIST_PAYMENT("", "Display a list of your payment history", true),
    EXIT("", "Exit application", false);

    private final String argument;
    private final String information;

    private final boolean isRequiredLogin;

    Command(String argument, String information, boolean isRequiredLogin) {
        this.argument = argument;
        this.information = information;
        this.isRequiredLogin = isRequiredLogin;
    }

    public boolean isRequiredLogin() {
        return this.isRequiredLogin;
    }

    @Override
    public String toString() {
        return String.format("%-25s %-25s # %s", this.name(), this.argument, this.information);
    }
}
