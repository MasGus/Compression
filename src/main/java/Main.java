/**
 * Created by Masha on 29.03.2015.
 */

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;


public class Main {
    private final static int CHUNK_BYTE_SIZE = 4*1024;
    private final static String FILE_NAME = "Tiger.txt";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchProviderException{
        ArrayList<FileMetaData> fileMetadataList = new ArrayList<FileMetaData>();
        searchFiles("C:\\Users\\Masha", fileMetadataList);
        //searchFiles("C:\\Users\\Masha\\Desktop\\ЕМС\\Новая папка", fileMetadataList);

        chunksHashes(fileMetadataList);

        System.out.println("All files in directory: ");
        for(int i = 0; i < fileMetadataList.size(); i++) {
            System.out.println("Path: " + fileMetadataList.get(i).getfileName() + " Size: " + fileMetadataList.get(i).fileSize);
        }
    }

    //Создание списка файлов выбранной директории
    public static void searchFiles(String path, ArrayList<FileMetaData> fileMetadataList) {
        File dir = new File(path);
        File listDir[] = dir.listFiles();
        for (int i = 0; i < listDir.length; i++) {
            try {
                if (listDir[i].isDirectory()) {
                    searchFiles(listDir[i].getAbsolutePath(), fileMetadataList);
                } else {
                    FileMetaData fileMetaData = new FileMetaData();
                    fileMetaData.setFileName(listDir[i].getAbsolutePath());
                    fileMetaData.setFileSize(listDir[i].length());
                    fileMetadataList.add(fileMetaData);
                }
            } catch (Exception e) {

            }

        }
    }

    // Вычисление и запись хешей фиксированных хешей каждого файла из списка файлов
    public static void chunksHashes(ArrayList<FileMetaData> fileMetadataList) throws IOException, NoSuchAlgorithmException, NoSuchProviderException {
        File file = new File(FILE_NAME);
        FileWriter writer = new FileWriter(file);
        for (int i = 0; i < fileMetadataList.size(); i++){
            File inputFile = new File(fileMetadataList.get(i).getfileName());
            FileInputStream inputStream;
            int fileSize = (int) inputFile.length();
            int read = 0;
            int readLength = CHUNK_BYTE_SIZE;
            byte[] byteChunkPart;
            try {
                inputStream = new FileInputStream(inputFile);
                while (fileSize > 0) {
                    if (fileSize <= CHUNK_BYTE_SIZE) {
                        readLength = fileSize;
                    }
                    byteChunkPart = new byte[readLength];
                    read = inputStream.read(byteChunkPart, 0, readLength);
                    fileSize -= read;
                    writer.write(HashFunctions.getTigerHashBytes(byteChunkPart) + "\r\n");
                }
                inputStream.close();
                writer.flush();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
