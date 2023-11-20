package org.example.payment.model;

/**
 * @System: Wallet
 * @Title: Bill
 * @Version: 1.0.0
 * @Author: HungHa
 * @CreateOn: 2023/11/19
 */
public class Wallet implements IModel {
    private int walletId;
    private long balance;
    private int accountId;

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        synchronized (this) {
            this.balance = balance;
        }
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toDataRaw() {
        return String.format("%s,%s,%s", walletId, balance, accountId);
    }
}
