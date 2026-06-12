package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.dto.response.DocumentoResponse;
import br.edu.ufvjm.jurisapoio.entity.DocumentoProva;
import br.edu.ufvjm.jurisapoio.enums.CategoriaDocumento;
import br.edu.ufvjm.jurisapoio.exception.BusinessException;
import br.edu.ufvjm.jurisapoio.repository.CasoRepository;
import br.edu.ufvjm.jurisapoio.repository.DocumentoProvaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private static final int MAX_DOCUMENTOS_POR_CASO = 20;

    private final DocumentoProvaRepository documentoProvaRepository;
    private final CasoRepository casoRepository;

    @Transactional
    public DocumentoResponse uploadDocumento(UUID casoId, UUID vitimaId, MultipartFile arquivo, CategoriaDocumento categoria) {
        // TODO: Buscar Caso pelo casoId ou lançar ResourceNotFoundException.
        //       Verificar que caso.vitima.id == vitimaId, caso contrário AccessDeniedException.
        //       RN08: contar documentos via documentoProvaRepository.countByCasoId().
        //       Se count >= 20, lançar BusinessException("Limite de 20 documentos por caso atingido.").
        //       Chamar ArquivoService.validarEArmazenar() para:
        //           - Validar magic bytes via MagicBytesValidator.validar().
        //           - Calcular hashSHA256 do conteúdo.
        //           - Fazer upload para S3 e retornar urlS3.
        //       RN05: cifrar nome original via CriptografiaUtil.cifrar() com chave do caso.
        //       Criar e persistir DocumentoProva. Retornar DocumentoResponse.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional(readOnly = true)
    public List<DocumentoResponse> listarPorCaso(UUID casoId, UUID solicitanteId) {
        // TODO: Verificar participação do solicitante no caso.
        //       RN06: se solicitante for advogado, filtrar apenas documentos com permissaoAdvogado = true.
        //       Mapear para DocumentoResponse e retornar lista.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public DocumentoResponse concederPermissao(UUID documentoId, UUID vitimaId) {
        // TODO: RN06: buscar DocumentoProva ou lançar ResourceNotFoundException.
        //       Verificar que documento.caso.vitima.id == vitimaId.
        //       Setar permissaoAdvogado = true e persistir.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional
    public DocumentoResponse revogarPermissao(UUID documentoId, UUID vitimaId) {
        // TODO: RN06: buscar DocumentoProva ou lançar ResourceNotFoundException.
        //       Verificar que documento.caso.vitima.id == vitimaId.
        //       Setar permissaoAdvogado = false e persistir.
        throw new UnsupportedOperationException("Não implementado");
    }

    @Transactional(readOnly = true)
    public Resource downloadDocumento(UUID documentoId, UUID solicitanteId) {
        // TODO: Buscar DocumentoProva ou lançar ResourceNotFoundException.
        //       RN06: se solicitante for advogado, verificar permissaoAdvogado == true.
        //       Baixar arquivo do S3 via ArquivoService.obterRecurso() e retornar.
        throw new UnsupportedOperationException("Não implementado");
    }

    public DocumentoResponse mapearParaResponse(DocumentoProva documento) {
        // TODO: Converter em DocumentoResponse.
        //       RN05: retornar apenas metadados — nunca nome original nem urlS3 diretamente ao cliente.
        throw new UnsupportedOperationException("Não implementado");
    }
}
