package org.example.payment.service;

import org.example.payment.helpers.PaymentHelper;
import org.example.payment.model.Account;
import org.example.payment.model.Command;

import java.beans.PropertyChangeEvent;

import static org.example.payment.service.ObservableService.LOGIN_STATE;
import static org.example.payment.service.ObservableService.PAYMENT_STATE;

public class PaymentService extends AbstractService {
    PaymentHelper paymentHelper;

    public PaymentService() {
        paymentHelper = new PaymentHelper();
    }

    @Override
    public void executeCommand(Command command, String... args) throws Exception {
        if (account == null) {
            System.out.println("Sorry! We don't support your request.");
            return;
        }
        switch (command) {
            case LIST_PAYMENT -> paymentHelper.printPaymentList(account.getAccountId());
            case PAY -> payBill(args);
            default -> throw new Exception();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(LOGIN_STATE)) {
            this.account = (Account) evt.getNewValue();
        } else if (evt.getPropertyName().equals(PAYMENT_STATE)) {
            payBill((String) evt.getNewValue());
        }
    }

    public synchronized void payBill(String... args) {
        //TODO create bill pay
    }
}