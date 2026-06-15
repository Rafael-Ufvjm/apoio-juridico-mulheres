package br.edu.ufvjm.jurisapoio.util;

import org.junit.jupiter.api.Test;
import java.util.Base64;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CriptografiaUtilTest {

    @Test
    void gerarChaveBase64_DeveRetornarChaveValida() {
        String chave = CriptografiaUtil.gerarChaveBase64();
        assertThat(chave).isNotEmpty();
        
        byte[] decoded = Base64.getDecoder().decode(chave);
        assertThat(decoded).hasSize(32); // 256 bits para AES-256
    }

    @Test
    void cifrarEDecifrar_DeveRetornarTextoOriginal() {
        String chave = CriptografiaUtil.gerarChaveBase64();
        String textoOriginal = "Esta é uma mensagem muito secreta para teste.";

        String textoCifrado = CriptografiaUtil.cifrar(textoOriginal, chave);
        assertThat(textoCifrado).isNotEmpty();
        assertThat(textoCifrado).isNotEqualTo(textoOriginal);

        String textoDecifrado = CriptografiaUtil.decifrar(textoCifrado, chave);
        assertThat(textoDecifrado).isEqualTo(textoOriginal);
    }

    @Test
    void decifrar_DeveLancarExcecao_QuandoChaveForIncorreta() {
        String chaveCorreta = CriptografiaUtil.gerarChaveBase64();
        String chaveIncorreta = CriptografiaUtil.gerarChaveBase64();
        String textoOriginal = "Dados secretos.";

        String textoCifrado = CriptografiaUtil.cifrar(textoOriginal, chaveCorreta);

        assertThatThrownBy(() -> CriptografiaUtil.decifrar(textoCifrado, chaveIncorreta))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Erro ao decifrar dados");
    }
}
