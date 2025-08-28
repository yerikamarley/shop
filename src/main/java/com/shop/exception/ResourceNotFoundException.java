package com.shop.exception;

// Esto importa la clase padre para excepciones no chequeadas (no necesitas try-catch obligatorios).
import java.lang.RuntimeException;

// Esto crea una excepción personalizada para cuando un recurso (como un Articulo) no se encuentra.
public class ResourceNotFoundException extends RuntimeException {

    // Constructor: Recibe un mensaje de error, como "Articulo no encontrado con ID: 1".
    public ResourceNotFoundException(String message) {
        super(message); // Esto pasa el mensaje a la clase padre para que se muestre en logs.
    }
}