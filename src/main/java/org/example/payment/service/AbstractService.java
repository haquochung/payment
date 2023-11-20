package org.example.payment.service;

import org.example.payment.model.Account;

import java.beans.PropertyChangeListener;

public abstract class AbstractService implements IService, PropertyChangeListener {
    protected Account account;
}
