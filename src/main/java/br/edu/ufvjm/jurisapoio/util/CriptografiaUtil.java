package br.edu.ufvjm.jurisapoio.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// RN05: cifrar nome de arquivo com AES-256-GCM antes de persistir
public class CriptografiaUtil {

    private CriptografiaUtil() {}

    public static String cifrar(String texto, String chaveBase64) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(chaveBase64);
            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, parameterSpec);

            byte[] ciphertext = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));

            byte[] concatenated = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv, 0, concatenated, 0, iv.length);
            System.arraycopy(ciphertext, 0, concatenated, iv.length, ciphertext.length);

            return Base64.getEncoder().encodeToString(concatenated);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cifrar dados", e);
        }
    }

    public static String decifrar(String textoCifradoBase64, String chaveBase64) {
        try {
            byte[] concatenated = Base64.getDecoder().decode(textoCifradoBase64);
            byte[] iv = new byte[12];
            System.arraycopy(concatenated, 0, iv, 0, 12);

            byte[] ciphertext = new byte[concatenated.length - 12];
            System.arraycopy(concatenated, 12, ciphertext, 0, ciphertext.length);

            byte[] keyBytes = Base64.getDecoder().decode(chaveBase64);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, parameterSpec);

            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao decifrar dados", e);
        }
    }

    public static String gerarChaveBase64() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
