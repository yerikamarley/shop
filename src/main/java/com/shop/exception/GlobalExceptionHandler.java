package com.shop.exception;

// Importa clases de Spring para manejar HTTP y excepciones.
import org.springframework.http.HttpStatus; // Esto define códigos HTTP como 404 (Not Found).
import org.springframework.http.ResponseEntity; // Esto envuelve la respuesta HTTP con cuerpo JSON.
import org.springframework.web.bind.annotation.ControllerAdvice; // Esto hace que esta clase "aconseje" a todos los controllers.
import org.springframework.web.bind.annotation.ExceptionHandler; // Esto marca métodos que manejan excepciones específicas.
import org.springframework.web.context.request.WebRequest; // Esto da info sobre la request (no lo usamos mucho aquí).

import com.shop.exception.ResponseDTO;


// Esto hace que esta clase maneje excepciones globalmente en la app.
@ControllerAdvice // Spring la detecta y aplica a todos los controllers.
public class GlobalExceptionHandler {

    // Esto maneja nuestra excepción custom (ResourceNotFoundException).
    @ExceptionHandler(ResourceNotFoundException.class) // Atrapará solo esta excepción.
    public ResponseEntity<ResponseDTO> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        // Crea un DTO con null en objeto, el mensaje de error, y status "ERROR".
        ResponseDTO response = new ResponseDTO(null, ex.getMessage(), "ERROR");
        // Retorna HTTP 404 (Not Found) con el JSON.
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Esto maneja excepciones de argumentos inválidos (e.g., validaciones fallidas en services).
    @ExceptionHandler(IllegalArgumentException.class) // Atrapará validaciones como "Cantidad negativa".
    public ResponseEntity<ResponseDTO> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ResponseDTO response = new ResponseDTO(null, ex.getMessage(), "ERROR");
        // Retorna HTTP 400 (Bad Request).
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Esto es un fallback: Maneja cualquier otra excepción inesperada.
    @ExceptionHandler(Exception.class) // Atrapará todo lo demás (e.g., errores de DB).
    public ResponseEntity<ResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        ResponseDTO response = new ResponseDTO(null, "Error interno del servidor: " + ex.getMessage(), "ERROR");
        // Retorna HTTP 500 (Internal Server Error).
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}