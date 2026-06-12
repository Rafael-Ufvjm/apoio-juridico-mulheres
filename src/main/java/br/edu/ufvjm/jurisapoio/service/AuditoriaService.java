package br.edu.ufvjm.jurisapoio.service;

import br.edu.ufvjm.jurisapoio.entity.LogAuditoria;
import br.edu.ufvjm.jurisapoio.repository.LogAuditoriaRepository;
import br.edu.ufvjm.jurisapoio.util.AnonimizacaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final LogAuditoriaRepository logAuditoriaRepository;

    @Async
    public void registrar(String tipoEvento, String tipoEntidade, String idEntidade, String ipOrigem, String detalhes) {
        // TODO: Chamar AnonimizacaoUtil.anonimizarIp(ipOrigem) antes de persistir.
        //       Criar LogAuditoria com todos os campos.
        //       Persistir via logAuditoriaRepository.save().
        //       Método @Async para não bloquear a requisição principal.
        throw new UnsupportedOperationException("Não implementado");
    }
}
