package org.example.payment.helpers;

import org.example.payment.model.IModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public abstract class DataHelper {

    private static final String DELIMITER = ",";
    private static final String DB_PATH = "db";

    public DataHelper() {
    }

    protected abstract String[] getColumns();

    protected abstract String getFileName();

    protected abstract void convertToObject(List<String> properties) throws Exception;

    protected boolean hasHeader() {
        return true;
    }

    private void encrypt(String content) throws InvalidKeyException {
        //TODO encrypt data to file
    }

    private BufferedReader decrypt(String fileName) {
        //TODO decrypt data from file
        return null;
    }

    void loadData() throws Exception {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(DB_PATH, getFileName()))) {
            Scanner scanner = new Scanner(br);
            String data;
            boolean ignoredHeader = hasHeader();
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                if (ignoredHeader) {
                    ignoredHeader = false;
                    continue;
                }
                if (data != null) {
                    //convert string array to list
                    convertToObject(Arrays.asList(data.split(DELIMITER)));
                }
            }
        } catch (IOException ioe) {
            System.out.println("Sorry! Some problems when get data");
        }
    }

    void saveData(Collection<?> data) throws Exception {
        File csvFile = Paths.get(DB_PATH, getFileName()).toFile();
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            pw.println(String.join(",", getColumns()));
            data.forEach(e -> pw.println(((IModel) e).toDataRaw()));
        } catch (FileNotFoundException e) {
            throw new Exception();
        }
    }
}
