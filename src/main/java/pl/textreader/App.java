package pl.textreader;

public class App {
    public static void main(String[] args) {
        try {
            new ProcessingFileService("filesToRead/dane-osoby.xml").startProcessFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
