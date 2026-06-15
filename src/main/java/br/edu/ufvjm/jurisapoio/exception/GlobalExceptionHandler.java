package br.edu.ufvjm.jurisapoio.exception;

import br.edu.ufvjm.jurisapoio.dto.response.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .unprocessableEntity()
                .body(new ErroResponse(ex.getMessage(), LocalDateTime.now(), 422));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(ex.getMessage(), LocalDateTime.now(), 404));
    }

    @ExceptionHandler(ArquivoInvalidoException.class)
    public ResponseEntity<ErroResponse> handleArquivoInvalido(ArquivoInvalidoException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErroResponse(ex.getMessage(), LocalDateTime.now(), 400));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .badRequest()
                .body(new ErroResponse(mensagem, LocalDateTime.now(), 400));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErroResponse> handleAutenticacao(AuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErroResponse("Email ou senha inválidos", LocalDateTime.now(), 401));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> handleAcessoNegado(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErroResponse("Acesso negado", LocalDateTime.now(), 403));
    }
}
