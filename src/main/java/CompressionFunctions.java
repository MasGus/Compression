import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;

import java.io.*;


/**
 * Created by Masha on 04.10.2015.
 */
public class CompressionFunctions {

    public static void main(String[] args) throws IOException {
        xz("MD5.txt.tar");
    }

    public static void bzip2(String fileName) throws IOException{
        File inputFile = new File (fileName);
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.err.println ("Cannot read file " + inputFile.getPath());
            System.exit (1);
        }

        File outputFile = new File (fileName + ".bz2");
        if (outputFile.exists()) {
            System.err.println ("File " + outputFile.getPath() + " already exists");
            System.exit (1);
        }

        InputStream fileInputStream = new BufferedInputStream (new FileInputStream (inputFile));
        OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream (outputFile), 524288);
        BZip2CompressorOutputStream outputStream = new BZip2CompressorOutputStream (fileOutputStream);

        byte[] buffer = new byte [524288];
        int bytesRead;
        while ((bytesRead = fileInputStream.read (buffer)) != -1) {
            outputStream.write (buffer, 0, bytesRead);
        }
        outputStream.close();
    }

    public static void gzip(String fileName) throws IOException {
        File inputFile = new File (fileName);
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.err.println ("Cannot read file " + inputFile.getPath());
            System.exit (1);
        }

        File outputFile = new File (fileName + ".gz");
        if (outputFile.exists()) {
            System.err.println ("File " + outputFile.getPath() + " already exists");
            System.exit (1);
        }

        InputStream fileInputStream = new BufferedInputStream (new FileInputStream (inputFile));
        OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream (outputFile), 524288);
        GzipCompressorOutputStream outputStream = new GzipCompressorOutputStream  (fileOutputStream);

        byte[] buffer = new byte [524288];
        int bytesRead;
        while ((bytesRead = fileInputStream.read (buffer)) != -1) {
            outputStream.write (buffer, 0, bytesRead);
        }
        outputStream.close();
    }

    public static void tar(String fileName) throws IOException {
        File inputFile = new File (fileName);
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.err.println ("Cannot read file " + inputFile.getPath());
            System.exit (1);
        }

        File outputFile = new File (fileName + ".tar");
        if (outputFile.exists()) {
            System.err.println ("File " + outputFile.getPath() + " already exists");
            System.exit (1);
        }

        // Output file stream
        FileOutputStream dest = new FileOutputStream(outputFile);

        // Create a TarOutputStream
        TarArchiveOutputStream out = new TarArchiveOutputStream( new BufferedOutputStream( dest ) );

        out.putArchiveEntry(new TarArchiveEntry(inputFile, inputFile.getName()));
        BufferedInputStream origin = new BufferedInputStream(new FileInputStream(inputFile));

        int count;
        byte data[] = new byte[524288];
        while((count = origin.read(data)) != -1) {
            out.write(data, 0, count);
        }

        out.flush();
        origin.close();
        out.close();
    }

    public static void xz(String fileName) throws IOException {
        File inputFile = new File (fileName);
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.err.println ("Cannot read file " + inputFile.getPath());
            System.exit (1);
        }

        File outputFile = new File (fileName + ".xz");
        if (outputFile.exists()) {
            System.err.println ("File " + outputFile.getPath() + " already exists");
            System.exit (1);
        }

        InputStream fileInputStream = new BufferedInputStream (new FileInputStream (inputFile));
        OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream (outputFile), 524288);
        XZCompressorOutputStream outputStream = new XZCompressorOutputStream  (fileOutputStream);

        byte[] buffer = new byte [524288];
        int bytesRead;
        while ((bytesRead = fileInputStream.read (buffer)) != -1) {
            outputStream.write (buffer, 0, bytesRead);
        }
        outputStream.close();
    }
}
