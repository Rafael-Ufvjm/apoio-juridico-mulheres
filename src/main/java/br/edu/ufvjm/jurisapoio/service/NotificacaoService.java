package br.edu.ufvjm.jurisapoio.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificacaoService {

    public void notificarNovoCaso(UUID casoId) {
        // TODO: implementar envio de e-mail quando a dependência de e-mail for adicionada
    }

    public void notificarAdvogadoAprovado(UUID advogadoId) {
        // TODO: implementar envio de e-mail quando a dependência de e-mail for adicionada
    }

    public void notificarAdvogadoRecusado(UUID advogadoId) {
        // TODO: implementar envio de e-mail quando a dependência de e-mail for adicionada
    }

    public void notificarCasoEncerrado(UUID casoId) {
        // TODO: implementar envio de e-mail quando a dependência de e-mail for adicionada
    }
}