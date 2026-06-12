package br.edu.ufvjm.jurisapoio.util;

// Anonimizar dados sensíveis antes de persistir em log_auditoria
public class AnonimizacaoUtil {

    private AnonimizacaoUtil() {}

    public static String anonimizarIp(String ip) {
        // TODO: Se IPv4 (contém "."), substituir o último octeto por "xxx" (ex: 192.168.1.xxx).
        //       Se IPv6, substituir os últimos 16 chars por "xxxx:xxxx".
        //       Retornar ip anonimizado. Nunca armazenar IP completo.
        throw new UnsupportedOperationException("Não implementado");
    }
}
