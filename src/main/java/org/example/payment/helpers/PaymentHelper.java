package org.example.payment.helpers;

import org.example.payment.model.Payment;
import org.example.payment.model.Wallet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentHelper extends DataHelper {
    private static final int PAYMENT_NO_COLUMN_INDEX = 0;
    private static final int AMOUNT_COLUMN_INDEX = 1;
    private static final int PAYMENT_DATE_COLUMN_INDEX = 2;
    private static final int STATE_COLUMN_INDEX = 3;
    private static final int BILL_NO_COLUMN_INDEX = 4;

    private static final String[] COLUMNS = new String[]{"Payment No.", "Amount", "Payment Date", "State", "Payment No."};

    private static final String FILE_NAME = "payment.csv";

    private final Map<Integer, Payment> cache = new HashMap<>();

    @Override
    protected String[] getColumns() {
        return COLUMNS;
    }

    @Override
    protected String getFileName() {
        return FILE_NAME;
    }

    @Override
    protected void convertToObject(List<String> properties) throws Exception {
        int columnCount = getColumns().length;
        if (properties.size() != columnCount) {
            throw new Exception();
        }

        Payment payment = new Payment();
        payment.setPaymentNo(Integer.parseInt(properties.get(PAYMENT_NO_COLUMN_INDEX)));
        payment.setAmount(Long.parseLong(properties.get(AMOUNT_COLUMN_INDEX)));
        payment.setPaymentDate(properties.get(PAYMENT_DATE_COLUMN_INDEX));
        payment.setState(properties.get(STATE_COLUMN_INDEX));
        payment.setBillNo(Integer.parseInt(properties.get(BILL_NO_COLUMN_INDEX)));
        cache.put(payment.getPaymentNo(), payment);
    }

    public Payment findPaymentByPaymentNo(int paymentNo) {
        if (cache.containsKey(paymentNo)) {
            return cache.get(paymentNo);
        }
        return null;
    }

    public void printPaymentList(long accountId) throws Exception {
        getPaymentList();

        List<Payment> listPayment = cache.values().stream().filter(payment -> payment.getAccountId() == accountId).toList();

        if (listPayment.isEmpty()) {
            System.out.println("No payments were found for this account");
        }

        System.out.printf("%-10s %-15s %-10s %-15s %-10s%n",
                COLUMNS[PAYMENT_NO_COLUMN_INDEX], COLUMNS[AMOUNT_COLUMN_INDEX], COLUMNS[PAYMENT_DATE_COLUMN_INDEX],
                COLUMNS[STATE_COLUMN_INDEX], COLUMNS[BILL_NO_COLUMN_INDEX]);
        for (Payment payment : listPayment) {
            System.out.printf("%-10s %-15s %-10s %-15s %-10s%n",
                    payment.getPaymentNo(), payment.getAmount(), payment.getPaymentDate(), payment.getState(), payment.getBillNo());
        }
    }

    private void getPaymentList() throws Exception {
        cache.clear();
        loadData();
    }

    public void addPayment(int billNo, long amount, String state, int accountId) throws Exception {
        int currentPaymentNo = Collections.max(cache.values().stream().map(Payment::getPaymentNo).toList());
        Payment payment = new Payment();
        payment.setPaymentNo(currentPaymentNo + 1);
        payment.setAmount(amount);
        payment.setState(state);
        payment.setBillNo(billNo);
        payment.setAccountId(accountId);

        cache.put(payment.getPaymentNo(), payment);

        saveData(cache.values());
    }
}
