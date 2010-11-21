package util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 */
public class Crypto {

    public enum Mode {

        ENCRYPT, DECRYPT
    }
    public final static String algorithm = "AES";

    public static Cipher getCipher(Mode mode, String password) {
        Cipher cipher = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(algorithm);
            SecureRandom random = new SecureRandom(password.getBytes());
            kgen.init(random);
            SecretKey key = kgen.generateKey();
            cipher = Cipher.getInstance(algorithm);
            int cipherMode = mode == Mode.ENCRYPT
                             ? Cipher.ENCRYPT_MODE
                             : Cipher.DECRYPT_MODE;
            cipher.init(cipherMode, key);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipher;
    }
}
