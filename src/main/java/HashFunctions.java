import com.sun.xml.internal.ws.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.zip.Adler32;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Created by Masha on 02.10.2015.
 */
public class HashFunctions {

    static MessageDigest algorithm;

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
            algorithm = MessageDigest.getInstance("Tiger", "BC");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static int seed = 1;
    private static int m = 0x5bd1e995;
    private static int r = 24;

    public static String getMD5String(byte[] data)
    {
        String output = "";
        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] md5sum = m.digest(data);
            BigInteger bigInt = new BigInteger(1, md5sum);
            output = bigInt.toString(16);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    public static String getSHAString(byte[] data)
    {
        String output = "";
        try
        {
            MessageDigest m = MessageDigest.getInstance("SHA-256");
            byte[] SHAsum = m.digest(data);
            BigInteger bigInt = new BigInteger(1, SHAsum);
            output = bigInt.toString(16);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    public static int getMurmurHashCode(byte[] data) {
        // Initialize the hash to a 'random' value
        int len = data.length;
        int h = seed ^ len; //xor

        int i = 0;
        while (len >= 4) {
            int k = data[i + 0] & 0xFF;
            k |= (data[i + 1] & 0xFF) << 8;
            k |= (data[i + 2] & 0xFF) << 16;
            k |= (data[i + 3] & 0xFF) << 24; //<< - сдвиг влево

            k *= m;
            k ^= k >>> r; // >>> - беззнаковый сдвиг вправо
            k *= m;

            h *= m;
            h ^= k;

            i += 4;
            len -= 4;
        }

        switch (len) {
            case 3:
                h ^= (data[i + 2] & 0xFF) << 16;
            case 2:
                h ^= (data[i + 1] & 0xFF) << 8;
            case 1:
                h ^= (data[i + 0] & 0xFF);
                h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }

    public static byte[] getAdler32ByteHash(byte[] input) {
        Adler32 alder = new Adler32();
        alder.update(input);
        ByteBuffer buf = ByteBuffer.wrap(new byte[8]);
        buf.putLong(alder.getValue());
        return buf.array();
    }

    public static byte[] getTigerHashBytes(byte[] input)
            throws NoSuchAlgorithmException, UnsupportedEncodingException,
            NoSuchProviderException {
        algorithm.reset();
        return algorithm.digest(input);
    }
}
