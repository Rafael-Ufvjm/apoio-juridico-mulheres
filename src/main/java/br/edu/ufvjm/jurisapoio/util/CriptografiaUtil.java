package br.edu.ufvjm.jurisapoio.util;

// RN05: cifrar nome de arquivo com AES-256-GCM antes de persistir
public class CriptografiaUtil {

    private CriptografiaUtil() {}

    public static String cifrar(String texto, String chaveBase64) {
        // TODO: Decodificar chaveBase64 para bytes (Base64.getDecoder().decode).
        //       Gerar IV aleatório de 12 bytes (SecureRandom).
        //       Criar Cipher AES/GCM/NoPadding com SecretKeySpec.
        //       Inicializar com Cipher.ENCRYPT_MODE, SecretKeySpec e GCMParameterSpec(128, iv).
        //       Concatenar IV + ciphertext e retornar como Base64.
        throw new UnsupportedOperationException("Não implementado");
    }

    public static String decifrar(String textoCifradoBase64, String chaveBase64) {
        // TODO: Decodificar textoCifradoBase64 para bytes.
        //       Extrair os primeiros 12 bytes como IV.
        //       Criar Cipher AES/GCM/NoPadding com Cipher.DECRYPT_MODE.
        //       Retornar texto decifrado como String UTF-8.
        throw new UnsupportedOperationException("Não implementado");
    }

    public static String gerarChaveBase64() {
        // TODO: Gerar 32 bytes aleatórios via SecureRandom.
        //       Retornar como Base64 para armazenamento.
        throw new UnsupportedOperationException("Não implementado");
    }
}
