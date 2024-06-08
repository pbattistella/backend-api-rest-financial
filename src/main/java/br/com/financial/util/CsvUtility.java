package br.com.financial.util;

import br.com.financial.model.Account;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvUtility {

    private static final String TYPE = "text/csv";
    private static final String UTF8 = "UTF-8";

    public static boolean hasCsvFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Account> csvToAccountList(InputStream input) {
        try (var bReader = new BufferedReader(new InputStreamReader(input, UTF8));
            var csvParser = new CSVParser(bReader,
                    CSVFormat.DEFAULT
                            .withFirstRecordAsHeader()
                            .withIgnoreHeaderCase()
                            .withTrim());) {
            var lsitAccounts = new ArrayList<Account>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (var record : csvRecords) {
                var account = new Account();
                account.setDescription(record.get(0));
                account.setAccountType(AccountTypeEnum.valueOf(record.get(1)));
                account.setStatus(StatusEnum.valueOf(record.get(2)));
                account.setExpirationDate(new Date(record.get(3)));
                account.setPaymentDate(new Date(record.get(4)));
                account.setPaymentValue(Double.parseDouble(record.get(5)));
                System.out.println(account);
                lsitAccounts.add(account);
            }

            return lsitAccounts;

        } catch (IOException e) {
            throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
        }
    }
}
