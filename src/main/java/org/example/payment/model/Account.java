package org.example.payment.model;

import java.io.Serializable;

/**
 * @System: Payment System
 * @Title: Account
 * @Version: 1.0.0
 * @Author: HungHa
 * @CreateOn: 2023/11/19
 */
public class Account implements Serializable, Cloneable, IModel {
    private int accountId;
    private String accountName;
    private String password;
    private String displayName;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        return accountId == other.accountId;
    }

    @Override
    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


    @Override
    public String toDataRaw() {
        return String.format("%s,%s,%s,%s", accountId, accountName, password, displayName);
    }
}
