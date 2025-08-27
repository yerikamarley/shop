# Shop API - Gestión de Almacén con Spring Boot

## Descripción
Este proyecto es una API RESTful básica para la gestión de un almacén, desarrollada con Spring Boot. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre entidades como productos (artículos), compras, ventas, categorías, características, proveedores y clientes. 

El enfoque está en las capas principales de un backend:
- **Entity**: Modelos de datos con JPA y relaciones (OneToMany, ManyToOne).
- **Repository**: Interfaces para acceso a DB usando JpaRepository.
- **Service**: Lógica de negocio para CRUD.
- **Controller**: Endpoints REST con validaciones manuales y respuestas JSON estandarizadas (objeto, mensaje, status).

El proyecto sigue el modelo de base de datos proporcionado (entidades con FKs para relaciones), y usa validaciones manuales en controllers (no en entities, como solicitaste). Respuestas en JSON cumplen con el formato requerido: `{"objeto": datos, "mensaje": "descripción", "status": código HTTP}`.



## Tecnologías Usadas
- **Spring Boot**: Framework principal (versión 3.3.3).
- **Spring Data JPA**: Para persistencia en DB con repositories.
- **Hibernate**: ORM para mapear entities a tablas (ddl-auto=update genera tablas automáticamente).
- **MySQL**: Base de datos (configurada en application.properties).
- **Lombok**: Para reducir boilerplate (getters/setters con @Data).
- **Maven**: Gestor de dependencias (pom.xml).
- **Java 21**: Versión del lenguaje.

Dependencias clave en pom.xml:
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- mysql-connector-j
- lombok

## Instalación y Configuración
1. **Requisitos**:
   - Java 21+ instalado.
   - Maven instalado (o usa Eclipse/IntelliJ con Maven support).
   - MySQL instalado y running (puerto 3306, user root, pass 1234 – ajusta si difiere).
   - Eclipse o IDE con plugin Lombok instalado (para ver getters/setters generados).

2. **Clona el proyecto** (si usas Git, de lo contrario crea estructura manual):
   ```
   git clone [tu-repo]
   cd shop
   ```

3. **Configura DB**:
   - Crea una DB en MySQL llamada "shop" (opcional, ddl-auto=update la crea).
   - En `src/main/resources/application.properties`:
     ```
     spring.application.name=shop
     spring.datasource.url=jdbc:mysql://localhost:3306/shop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
     spring.datasource.username=root
     spring.datasource.password=1234
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
     server.port=8080
     ```
     - Ajusta user/pass si tu MySQL es diferente.

4. **Compila y corre**:
   - En terminal: `mvn clean install` (compila).
   - `mvn spring-boot:run` (corre la app).
   - O en Eclipse: Right-click proyecto > Run As > Spring Boot App.
   - App corre en http://localhost:8080. Chequea logs para "Tomcat started".

## Estructura del Proyecto
- **com.shop.entity**: Entities con @Entity, relaciones JPA (@ManyToOne, @OneToMany con cascade y orphanRemoval).
- **com.shop.repository**: Interfaces que extienden JpaRepository para CRUD automático.
- **com.shop.service**: Clases con métodos CRUD (getAll, getById, create, update, delete), inyectan repository.
- **com.shop.controller**: Endpoints REST (@RestController), validaciones manuales en métodos private, respuestas JSON personalizadas.

Ejemplo de relación: Articulo tiene ManyToOne a Categoria (FK category_id), OneToMany a ArticuloCaracteristica (borra características al eliminar artículo).

## Endpoints API
Todos endpoints responden JSON con "objeto", "mensaje", "status". Usa Postman para probar (crea datos en orden: padres primero, hijas después).

### /api/articulos (Articulo)
- GET: Leer todos.
- GET /{id}: Leer por ID.
- POST: Crear (body: {"name": "...", "categoria": {"id": 1}, ...}).
- PUT /{id}: Actualizar.
- DELETE /{id}: Eliminar.

### /api/categorias (Categoria)
- Similar, body POST: {"description": "..."}

### /api/caracteristicas (Caracteristica)


### /api/proveedores (Proveedor)
- Body POST: {"name": "...", "email": "..."}

### /api/clientes (Cliente)


### /api/articulo-caracteristicas (ArticuloCaracteristica)
- Body POST: {"articulo": {"id": 1}, "caracteristica": {"id": 1}, "value": "..."}

### /api/compras (Compra)
- Body POST: {"time": "2025-08-27T10:00:00", "proveedor": {"id": 1}, "value": 100}

### /api/compra-articulos (CompraArticulo)
- Body POST: {"articulo": {"id": 1}, "compra": {"id": 1}, "quantity": 5, "uniValue": 20, "value": 100}

### /api/ventas (Venta)


### /api/venta-articulos (VentaArticulo)
- Body POST: {"venta": {"id": 1}, "articulo": {"id": 1}, "quantity": 2, "value": 100}

## Pruebas con Postman
1. Crea colección "Shop ".
2. Orden para datos: Categoria > Caracteristica > Proveedor > Cliente > Articulo > ArticuloCaracteristica > Compra > CompraArticulo > Venta > VentaArticulo.
3. Ejemplo POST /api/articulos: Body JSON, envía, chequea status 201 y ID en objeto.

## Notas
- Validaciones: Manuales en controllers (ej. campos obligatorios, positivos). Si falla, status 400.
- Relaciones: Cascade borra hijos al eliminar padre (ej. eliminar Articulo borra sus características).
- Mejoras futuras: Agrega lógica en services para actualizar stock (ej. en CompraArticuloService, suma quantity a Articulo.quantity).
- Debug: Pon show-sql=true para ver queries SQL en console.

