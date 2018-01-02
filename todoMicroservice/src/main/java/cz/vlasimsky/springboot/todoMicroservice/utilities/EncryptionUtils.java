package cz.vlasimsky.springboot.todoMicroservice.utilities;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {
    private BasicTextEncryptor textEncryptor;

    public EncryptionUtils()  {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("secretEncryptionKey");
    }

    public String encrypt(String data) {
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encryptedData) {
        return textEncryptor.decrypt(encryptedData);
    }
}
