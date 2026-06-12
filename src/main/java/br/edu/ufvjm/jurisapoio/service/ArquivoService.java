package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.exception.ArquivoInvalidoException;
import br.edu.ufvjm.jurisapoio.util.MagicBytesValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArquivoService {

    public record ResultadoUpload(String urlS3, String hashSHA256, String tipoMIME, Long tamanhoBytes) {}

    public ResultadoUpload validarEArmazenar(MultipartFile arquivo) {
        // TODO: Ler os primeiros 8 bytes do arquivo para validação de magic bytes.
        //       Chamar MagicBytesValidator.validar(primeirosBytes, arquivo.getContentType()).
        //       Verificar tamanho máximo: 50 MB (configurado em application.yml multipart.max-file-size).
        //       Calcular hashSHA256 do conteúdo completo via MessageDigest.getInstance("SHA-256").
        //       Fazer upload para S3 (ou armazenamento local em dev) com nome = hashSHA256.
        //       Retornar ResultadoUpload com urlS3, hashSHA256, tipoMIME e tamanhoBytes.
        throw new UnsupportedOperationException("Não implementado");
    }

    public Resource obterRecurso(String urlS3) {
        // TODO: Baixar o arquivo do S3 (ou ler do disco em dev) a partir da urlS3.
        //       Retornar como Resource para ser servido pelo controller com Content-Disposition.
        throw new UnsupportedOperationException("Não implementado");
    }
}
