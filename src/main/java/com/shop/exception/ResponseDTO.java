package com.shop.exception;

// Esto es un DTO (Data Transfer Object) simple para respuestas JSON uniformes en la API.
public class ResponseDTO {

    private Object objeto; // Esto guarda el objeto real (e.g., un Articulo o una lista, o null en error).
    private String mensaje; // Mensaje como "Creado exitosamente" o "No encontrado".
    private String status; // "OK" para éxito, "ERROR" para fallos.

    // Constructor: Para crear el DTO fácilmente.
    public ResponseDTO(Object objeto, String mensaje, String status) {
        this.objeto = objeto; // Asigna el objeto.
        this.mensaje = mensaje; // Asigna el mensaje.
        this.status = status; // Asigna el status.
    }

    // Getters: Para que Jackson (JSON) los serialice automáticamente.
    public Object getObjeto() {
        return objeto; // Retorna el objeto.
    }

    public String getMensaje() {
        return mensaje; // Retorna el mensaje.
    }

    public String getStatus() {
        return status; // Retorna el status.
    }

    // No necesitas setters por ahora, ya que es inmutable después de crear.
}