# Evidencias de integración de los servicios — E1 / issues #19, #33 y #34

Capturas originales compartidas por Mario durante las pruebas locales del 21 de noviembre de 2026. Las imágenes se han copiado sin editar ni modificar su contenido.

## Tres capturas principales solicitadas para E1

- `07-integracion-http-200.png`: nombre del equipo y usuario, fecha y hora reales; puertos 8081 y 8082 en escucha; petición al emisor con estado HTTP 200 y cuerpo JSON.
- `08-integracion-http-400-404.png`: datos del equipo, fecha y hora; ambos puertos en escucha; respuestas del emisor para ID 0 (HTTP 400) e ID 2 (HTTP 404).
- `09-integracion-consulta-entre-servicios.png`: datos del equipo, fecha y hora; ambos puertos en escucha; consultas directas al receptor y a través del emisor, ambas HTTP 200.

## Capturas complementarias

- `10-integracion-http-503-receptor-detenido.png`: petición al emisor mientras el receptor estaba detenido; estado HTTP 503 y mensaje de dependencia no disponible.
- `11-integracion-recuperacion-http-200.png`: después de reiniciar el receptor, el emisor volvió a responder HTTP 200.
- `12-integracion-primera-consulta-http-200.png`: primera respuesta correcta desde el emisor.
- `13-integracion-prueba-http-400.png`: rechazo de identificador no positivo por el emisor.
- `14-integracion-prueba-http-404.png`: asignatura inexistente a través del emisor.
- `15-servicio-refuerzos-configuracion-spring-initializr.png`: configuración inicial del proyecto del emisor.
- `16-servicio-refuerzos-iniciado-puerto-8081.png`: inicio correcto del servicio emisor en Spring Boot.

## Alcance y comprobaciones pendientes

Las pruebas manuales muestran respuestas 200/400/404/503 y recuperación. La comparación de respuestas directas e indirectas, junto con la prueba de dependencia detenida, respalda la comunicación entre dos procesos locales. No acredita despliegue definitivo, alta disponibilidad, replicación ni pruebas automatizadas completas.
