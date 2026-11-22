# Evidencias del servicio académico receptor — issue #19

Capturas originales proporcionadas por Mario durante la implementación y prueba local del servicio académico de E1. Se han copiado sin editar su contenido.

| Archivo | Evidencia |
| --- | --- |
| `00-configuracion-spring-initializr.png` | Configuración inicial de Spring Boot 4.0.10, Java 21 y Spring Web (complementaria). |
| `01-servicio-academico-iniciado-puerto-8082.png` | Spring Boot/Tomcat iniciado en puerto 8082. |
| `02-consulta-id-1-respuesta-json-navegador.png` | Respuesta JSON para `/api/asignaturas/1` desde el navegador; no muestra el código HTTP. |
| `03-prueba-id-0-http-400.png` | Prueba HTTP 400 para identificador no positivo. |
| `04-prueba-id-2-http-404.png` | Prueba HTTP 404 para asignatura inexistente. |
| `05-prueba-id-abc-http-400.png` | Prueba HTTP 400 para identificador no numérico. |
| `06-prueba-id-1-http-200-curl.png` | Prueba HTTP 200 para asignatura de demostración mediante curl. |

**Alcance:** evidencias de pruebas locales del servicio receptor (`src/servicio-academico/`). No acreditan todavía comunicación entre dos servicios. Las tres capturas obligatorias de E1, con ambos procesos comunicándose y los datos de usuario, equipo, fecha y hora del sistema, deberán obtenerse después de implementar e integrar el servicio emisor del issue #33.

**Privacidad:** revisar que los nombres de usuario, rutas, iconos y demás información visible sean apropiados para publicar en un repositorio público. No alterar capturas para simular datos o resultados.
