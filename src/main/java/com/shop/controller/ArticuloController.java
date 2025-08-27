package com.shop.controller;
import com.shop.entity.Articulo;
import com.shop.entity.ArticuloCaracteristica;
import com.shop.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {
@Autowired
private ArticuloService service;
@GetMapping
public ResponseEntity<Map<String, Object>> getAll() {
Map<String, Object> response = new HashMap<>();
response.put("objeto", service.getAll());
response.put("mensaje", "Artículos obtenidos correctamente");
response.put("status", HttpStatus.OK.value());
return ResponseEntity.ok(response);
}
@GetMapping("/{id}")
public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
Map<String, Object> response = new HashMap<>();
try {
response.put("objeto", service.getById(id));
response.put("mensaje", "Artículo obtenido correctamente");
response.put("status", HttpStatus.OK.value());
return ResponseEntity.ok(response);
} catch (RuntimeException e) {
response.put("objeto", null);
response.put("mensaje", e.getMessage());
response.put("status", HttpStatus.NOT_FOUND.value());
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
}
}
@PostMapping
public ResponseEntity<Map<String, Object>> create(@RequestBody Articulo articulo) {
Map<String, Object> response = new HashMap<>();
String error = validateArticulo(articulo, false);
if (error != null) {
response.put("objeto", null);
response.put("mensaje", error);
response.put("status", HttpStatus.BAD_REQUEST.value());
return ResponseEntity.badRequest().body(response);
}
response.put("objeto", service.create(articulo));
response.put("mensaje", "Artículo creado correctamente");
response.put("status", HttpStatus.CREATED.value());
return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
@PutMapping("/{id}")
public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Articulo articulo) {
Map<String, Object> response = new HashMap<>();
String error = validateArticulo(articulo, true);
if (error != null) {
response.put("objeto", null);
response.put("mensaje", error);
response.put("status", HttpStatus.BAD_REQUEST.value());
return ResponseEntity.badRequest().body(response);
}
try {
response.put("objeto", service.update(id, articulo));
response.put("mensaje", "Artículo actualizado correctamente");
response.put("status", HttpStatus.OK.value());
return ResponseEntity.ok(response);
} catch (RuntimeException e) {
response.put("objeto", null);
response.put("mensaje", e.getMessage());
response.put("status", HttpStatus.NOT_FOUND.value());
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
}
}
@DeleteMapping("/{id}")
public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
Map<String, Object> response = new HashMap<>();
try {
service.delete(id);
response.put("objeto", null);
response.put("mensaje", "Artículo eliminado correctamente");
response.put("status", HttpStatus.OK.value());
return ResponseEntity.ok(response);
} catch (RuntimeException e) {
response.put("objeto", null);
response.put("mensaje", e.getMessage());
response.put("status", HttpStatus.NOT_FOUND.value());
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
}
}
private String validateArticulo(Articulo articulo, boolean isUpdate) {
if (!isUpdate) {
if (articulo.getName() == null || articulo.getName().trim().isEmpty()) {
return "El nombre es obligatorio y no puede estar vacío";
}
if (articulo.getCategoria() == null || articulo.getCategoria().getId() == null) {
return "La categoría es obligatoria";
}
if (articulo.getQuantity() == null || articulo.getQuantity() < 0) {
return "La cantidad debe ser cero o positiva";
}
if (articulo.getState() == null) {
return "El estado es obligatorio";
}
}
if (articulo.getName() != null && articulo.getName().length() > 100) {
return "El nombre no puede exceder 100 caracteres";
}
if (articulo.getDescription() != null && articulo.getDescription().length() > 255) {
return "La descripción no puede exceder 255 caracteres";
}
if (articulo.getQuantity() != null && articulo.getQuantity() < 0) {
return "La cantidad debe ser cero o positiva";
}
if (articulo.getCaracteristicas() != null) {
for (ArticuloCaracteristica charac : articulo.getCaracteristicas()) {
if (charac.getValue() == null || charac.getValue().trim().isEmpty()) {
return "El valor de característica no puede estar vacío";
}
}
}
return null;
}
}