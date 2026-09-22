package com.duoc.atenciones.exception;

import com.duoc.atenciones.model.MensajeError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<MensajeError> noEncontrado(RecursoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensajeError(ex.getMessage()));
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<MensajeError> conflicto(ConflictoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MensajeError(ex.getMessage()));
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<MensajeError> solicitudInvalida(SolicitudInvalidaException ex) {
        return ResponseEntity.badRequest().body(new MensajeError(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensajeError> validacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Los datos enviados no son validos");
        log.warn("Error de validacion: {}", mensaje);
        return ResponseEntity.badRequest().body(new MensajeError(mensaje));
    }
}
