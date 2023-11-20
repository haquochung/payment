package org.example.payment.model;

import java.io.Serializable;

/**
 * @System: Payment System
 * @Title: Bill
 * @Version: 1.0.0
 * @Author: HungHa
 * @CreateOn: 2023/11/19
 */
public class Bill implements Serializable, Cloneable, IModel {
    private int billNo;
    private String type;
    private long amount;
    private String dueDate;
    private BillState state;
    private String provider;
    private int accountId;

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public BillState getState() {
        return state;
    }

    public void setState(BillState billState) {
        this.state = billState;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bill other = (Bill) obj;
        return billNo == other.billNo;
    }

    @Override
    public Bill clone() {
        try {
            return (Bill) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toDataRaw() {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                billNo, type, amount, dueDate, state, provider, accountId);
    }
}
