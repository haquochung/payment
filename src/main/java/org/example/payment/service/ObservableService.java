package org.example.payment.service;

import org.example.payment.model.Account;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ObservableService {
    public static final String LOGIN_STATE = "login state";
    public static final String WITHDRAW_STATE = "withdraw state";
    public static final String PAYMENT_STATE = "payment state";

    protected Account account;

    protected PropertyChangeSupport support;

    public ObservableService() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void updateLoginState(Account account) {
        support.firePropertyChange(LOGIN_STATE, this.account, account);
        this.account = account;
    }
}
