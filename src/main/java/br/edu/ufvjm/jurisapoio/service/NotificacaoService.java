package br.edu.ufvjm.jurisapoio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final JavaMailSender mailSender;

    public void notificarNovoCaso(UUID casoId) {
        // TODO: Buscar Caso pelo casoId.
        //       Montar e-mail para os advogados ATIVOS com disponibilidade != OCUPADO.
        //       RN02: não incluir nenhum dado identificador da vítima no e-mail — apenas protocolo e tipoViolencia.
        //       Enviar via mailSender.send() com template simples.
        throw new UnsupportedOperationException("Não implementado");
    }

    public void notificarAdvogadoAprovado(UUID advogadoId) {
        // TODO: Buscar AdvogadoVoluntario pelo advogadoId.
        //       Enviar e-mail de boas-vindas ao advogado aprovado.
        throw new UnsupportedOperationException("Não implementado");
    }

    public void notificarAdvogadoRecusado(UUID advogadoId) {
        // TODO: Buscar AdvogadoVoluntario pelo advogadoId.
        //       Enviar e-mail com a justificativa de recusa (RN18).
        throw new UnsupportedOperationException("Não implementado");
    }

    public void notificarCasoEncerrado(UUID casoId) {
        // TODO: Buscar Caso pelo casoId.
        //       Notificar vítima e advogado sobre o encerramento.
        //       RN02: não expor dados da vítima ao advogado no e-mail.
        throw new UnsupportedOperationException("Não implementado");
    }
}
