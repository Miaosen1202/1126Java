package com.wdcloud.lms;

import com.csvreader.CsvReader;

import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;

public class CsvReaderTest {

    public static void main(String[] args) throws Exception {
        String file = "/home/zhangzhen/tmp/sis/accounts.csv";
        CsvReader csvReader = new CsvReader(new FileReader(Paths.get(file).toFile(), Charset.forName("UTF-8")));
        csvReader.setDelimiter(';');
        if (csvReader.readHeaders()) {
            String[] headers = csvReader.getHeaders();
            System.out.println("headers: " + headers.length + "n," + Arrays.toString(headers));
        }
        while (csvReader.readRecord()) {
            System.out.println(csvReader.getRawRecord());

            System.out.println(csvReader.get("account_id"));
            System.out.println("---------------");
        }

    }

}
