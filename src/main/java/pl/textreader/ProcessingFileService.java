package pl.textreader;

import java.io.File;

public class ProcessingFileService {

    private String fullNameOfFile;
    private Reader reader;

    public ProcessingFileService(String fullNameOfFile) {
        this.fullNameOfFile = fullNameOfFile;
    }

    public void startProcessFile() {

        File file = new File(this.fullNameOfFile);

        switch (getFileExtension(this.fullNameOfFile)) {
            case "txt":
                reader = new ReaderCSV();
                break;
            case "xml":
                reader = new ReaderXML();
                break;
            default:
                throw new RuntimeException("Not known extension of file");
        }

        reader.scanFile(file);
    }

    private static String getFileExtension(String fullName) {
        if (fullName != null) {
            String fileName = new File(fullName).getName();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        } else {
            throw new RuntimeException("Empty filename");
        }
    }
}
