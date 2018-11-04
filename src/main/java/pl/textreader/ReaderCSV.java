package pl.textreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class ReaderCSV implements Reader {

    private ConnectionDB connectionDB;

    @Override
    public void scanFile(File file) {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            this.connectionDB = new ConnectionDB();
            this.connectionDB.tryConnect();

            for (String line; (line = br.readLine()) != null; ) {

                String[] values = line.split(",");

                String name = values[0];
                String surname = values[1];
                String age = values[2];
                String city = values[3];

                this.connectionDB.insertIntoCustomerTable(name, surname, age, city);

                for (int i = 4; i < values.length; i++) {
                    int typeOfContact = new Validator().findTypeOfField(values[i]);
                    String contact = values[i];

                    this.connectionDB.insertIntoContactsTable(typeOfContact, contact);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connectionDB.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
