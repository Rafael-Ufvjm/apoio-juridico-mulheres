package br.edu.ufvjm.jurisapoio.util;

import br.edu.ufvjm.jurisapoio.exception.ArquivoInvalidoException;

// Valida o tipo real do arquivo pelos magic bytes — não confiar apenas na extensão ou Content-Type
public class MagicBytesValidator {

    private MagicBytesValidator() {}

    public static void validar(byte[] primeirosBytes, String tipoMIMEDeclarado) {
        // TODO: Mapear tipos MIME permitidos para seus magic bytes:
        //         "image/jpeg"      → { 0xFF, 0xD8, 0xFF }
        //         "image/png"       → { 0x89, 0x50, 0x4E, 0x47 }
        //         "application/pdf" → { 0x25, 0x50, 0x44, 0x46 }
        //         "audio/mpeg"      → { 0x49, 0x44, 0x33 } ou { 0xFF, 0xFB }
        //         "video/mp4"       → verificar bytes 4-7 = "ftyp"
        //       Verificar se primeirosBytes começa com o magic bytes do tipoMIMEDeclarado.
        //       Se não bater, lançar ArquivoInvalidoException("Tipo de arquivo não corresponde ao conteúdo real.").
        //       Se tipoMIME não estiver na lista de permitidos, também lançar exceção.
        throw new UnsupportedOperationException("Não implementado");
    }
}
