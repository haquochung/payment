package org.example.payment.helpers;

import org.example.payment.model.Bill;
import org.example.payment.model.BillState;
import org.example.payment.model.Payment;

import java.util.*;

public class BillHelper extends DataHelper {
    private static final int BILL_NO_COLUMN_INDEX = 0;
    private static final int TYPE_COLUMN_INDEX = 1;
    private static final int AMOUNT_COLUMN_INDEX = 2;
    private static final int DUE_DATE_COLUMN_INDEX = 3;
    private static final int STATE_COLUMN_INDEX = 4;
    private static final int PROVIDER_COLUMN_INDEX = 5;
    private static final int ACCOUNT_ID_COLUMN_INDEX = 6;

    private static final String[] COLUMNS = new String[]{"Bill No.", "Type", "Amount", "Due Date", "State", "PROVIDER", "Account Id"};

    private static final String FILE_NAME = "bill.csv";

    private final Map<Integer, Bill> cache = new HashMap<>();

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

        Bill bill = new Bill();
        bill.setBillNo(Integer.parseInt(properties.get(BILL_NO_COLUMN_INDEX)));
        bill.setType(properties.get(TYPE_COLUMN_INDEX));
        bill.setAmount(Long.parseLong(properties.get(AMOUNT_COLUMN_INDEX)));
        bill.setDueDate(properties.get(DUE_DATE_COLUMN_INDEX));
        bill.setState(BillState.valueOf(properties.get(STATE_COLUMN_INDEX)));
        bill.setProvider(properties.get(PROVIDER_COLUMN_INDEX));
        bill.setAccountId(Integer.parseInt(properties.get(ACCOUNT_ID_COLUMN_INDEX)));
        cache.put(bill.getBillNo(), bill);
    }

    public Bill findByBillNo(int billNo) {
        if (cache.containsKey(billNo)) {
            return cache.get(billNo);
        }

        return null;
    }

    public void printBillList(long accountId) throws Exception {
        printBillList(accountId, false);
    }

    public void printBillList(long accountId, boolean isSortByDueDate) throws Exception {
        getBillList();

        List<Bill> billList = new ArrayList<>(cache.values().stream().filter(bill ->
                bill.getAccountId() == accountId && bill.getState() == BillState.NOT_PAID).toList());

        if (billList.isEmpty()) {
            System.out.println("No bills were found for this account");
        }

        if (isSortByDueDate) {
            billList.sort((b1, b2) -> {
                Date dd1 = new Date(b1.getDueDate());
                Date dd2 = new Date(b2.getDueDate());
                if (dd1.after(dd2)) {
                    return 1;
                } else if (dd1.before(dd2)) {
                    return -1;
                }
                return 0;
            });
        }

        System.out.printf("%-10s %-15s %-10s %-15s %-10s %-10s%n",
                COLUMNS[BILL_NO_COLUMN_INDEX], COLUMNS[TYPE_COLUMN_INDEX], COLUMNS[AMOUNT_COLUMN_INDEX],
                COLUMNS[DUE_DATE_COLUMN_INDEX], COLUMNS[STATE_COLUMN_INDEX], COLUMNS[PROVIDER_COLUMN_INDEX]);
        for (Bill bill : billList) {
            System.out.printf("%-10s %-15s %-10s %-15s %-10s %-10s%n",
                    bill.getBillNo(), bill.getType(), bill.getAmount(), bill.getDueDate(), bill.getState(),
                    bill.getProvider());
        }
    }

    private void getBillList() throws Exception {
        cache.clear();
        loadData();
    }

    public void createBill(Bill bill) throws Exception {
        int currentBillNo = Collections.max(cache.values().stream().map(Bill::getBillNo).toList());
        bill.setBillNo(currentBillNo + 1);

        synchronized (cache) {
            cache.put(bill.getBillNo(), bill);
            saveData(cache.values());
        }
    }

    public void updateBill(Bill bill) throws Exception {
        synchronized (cache) {
            cache.put(bill.getBillNo(), bill);
            saveData(cache.values());
        }
    }

    public void deleteBill(int billNo) throws Exception {
        synchronized (cache) {
            cache.remove(billNo);
            saveData(cache.values());
        }
    }
}
