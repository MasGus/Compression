/**
 * Created by Masha on 30.03.2015.
 */
public class FileMetaData {
    public long fileSize;
    public String fileName;

    public String getfileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}