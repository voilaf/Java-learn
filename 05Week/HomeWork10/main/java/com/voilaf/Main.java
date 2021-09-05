package com.voilaf;

import com.voilaf.jdbc.BatchJdbc;
import com.voilaf.jdbc.DataSource;
import com.voilaf.jdbc.OriginJdbc;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("BatchJdbc start");
        BatchJdbc  batchJdbc = new BatchJdbc();
        batchJdbc.batchInsert();
        batchJdbc.close();
        System.out.println("BatchJdbc end");

        System.out.println("Hikari start");
        DataSource dataSource = new DataSource();
        dataSource.select();
        dataSource.close();
        System.out.println("Hikari end");

        System.out.println("OriginJdbc start");
        OriginJdbc originJdbc = new OriginJdbc();
        originJdbc.insert();
        originJdbc.update();
        originJdbc.select();
        originJdbc.delete();
        originJdbc.close();
        System.out.println("OriginJdbc end");
    }
}
