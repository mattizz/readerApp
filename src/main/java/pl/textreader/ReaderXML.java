package pl.textreader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ReaderXML implements Reader {

    private ConnectionDB connectionDB;

    @Override
    public void scanFile(File file) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            this.connectionDB = new ConnectionDB();
            this.connectionDB.tryConnect();

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList personList = document.getElementsByTagName("person");

            for (int i = 0; i < personList.getLength(); i++) {

                Node person = personList.item(i); //Person

                if (person.getNodeType() == Node.ELEMENT_NODE) {

                    Element personElement = (Element) person;

                    String name = personElement.getElementsByTagName("name").item(0).getTextContent();
                    String surname = personElement.getElementsByTagName("surname").item(0).getTextContent();
                    Node ageTag = personElement.getElementsByTagName("age").item(0);
                    String age = Validator.checkIfAgeExist(ageTag);
                    String city = personElement.getElementsByTagName("city").item(0).getTextContent();

                    this.connectionDB.insertIntoCustomerTable(name, surname, age, city);

                    Node contact = personElement.getElementsByTagName("contacts").item(0);
                    int numberOfContacts = contact.getChildNodes().getLength();

                    for (int k = 0; k < numberOfContacts; k++) {

                        Node singleContact = contact.getChildNodes().item(k);

                        if (singleContact.getNodeType() == Node.ELEMENT_NODE) {

                            String type = singleContact.getNodeName();

                            int typeOfContact = Validator.setTypeOfContact(type);

                            String contactValue = singleContact.getTextContent();

                            this.connectionDB.insertIntoContactsTable(typeOfContact, contactValue);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
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
