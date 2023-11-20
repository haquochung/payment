package org.example.payment.model;

/**
 * @System: Payment System
 * @Title: Payment Model
 * @Version: 1.0.0
 * @Author: HungHa
 * @CreateOn: 2023/11/19
 */
public class Payment implements IModel {
    private int paymentNo;
    private long amount;
    private String paymentDate;
    private String state;
    private int billNo;

    private int accountId;

    public int getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(int paymentNo) {
        this.paymentNo = paymentNo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toDataRaw() {
        return String.format("%s,%s,%s,%s,%s,%s",
                paymentNo, amount, paymentDate, state, billNo, accountId);
    }
}
