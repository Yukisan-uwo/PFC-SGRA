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

## Pruebas complementarias del emisor publicado (21 de noviembre de 2026)

Se compiló desde una exportación limpia de la revisión `668cd9d` con JDK 21 y Maven Wrapper. Pasó la prueba de contexto del módulo. Se ejecutó el emisor publicado, que usa Spring `RestClient`, en `127.0.0.1:8081`. Para controlar las respuestas se usó un servidor HTTP de prueba en `127.0.0.1:8082` que registró las rutas solicitadas. La prueba asistida por Codex complementó las capturas anteriores con los siguientes casos.

| Caso | HTTP del emisor | Resultado observado |
|---|---|---|
| ID `1` con respuesta académica válida | `200` | Devolvió la asignatura sintética del receptor. |
| ID `abc` | `400` | `ID_INVALIDO`. Cero llamadas al receptor. |
| ID `2` con respuesta académica `404` | `404` | `ASIGNATURA_NO_ENCONTRADA`. |
| El receptor respondió `500` | `503` | `SERVICIO_ACADEMICO_NO_DISPONIBLE`. |
| El receptor respondió `200` con un ID distinto al solicitado | `503` | `SERVICIO_ACADEMICO_NO_DISPONIBLE`. |
| El receptor respondió `200` con JSON malformado | `503` | `SERVICIO_ACADEMICO_NO_DISPONIBLE`. |
| El receptor demoró tres segundos antes de responder | `503` | `SERVICIO_ACADEMICO_NO_DISPONIBLE`. Se observó una espera de 1014 ms desde el cliente. |
| Consulta válida inmediatamente después del tiempo agotado | `200` | El emisor volvió a devolver la asignatura sin reiniciarse. |

El receptor de prueba registró una sola petición en cada caso con entrada válida. Los ocho resultados coincidieron con lo esperado. La demora se introdujo en el receptor de prueba para comprobar el tiempo de lectura. Queda pendiente la fundamentación con PE-U1 del issue #16 y la reproducción por otro integrante del issue #34.

- **Emisor publicado:** [aa48060](https://github.com/Yukisan-uwo/PFC-SGRA/commit/aa48060).
- **Integración con el servicio académico real y capturas:** [98ce1ac](https://github.com/Yukisan-uwo/PFC-SGRA/commit/98ce1ac).
- **Seguimiento:** [#20](https://github.com/Yukisan-uwo/PFC-SGRA/issues/20), [#33](https://github.com/Yukisan-uwo/PFC-SGRA/issues/33) y [#16](https://github.com/Yukisan-uwo/PFC-SGRA/issues/16).

## Reproducción técnica desde un clon limpio (21 de noviembre de 2026)

Codex clonó desde GitHub la revisión [a1d6922](https://github.com/Yukisan-uwo/PFC-SGRA/commit/a1d69227db1e8b150eec4c4ce03621892b1f50b7) de `main` y comprobó que el directorio de trabajo estaba limpio antes de iniciar la demostración. Se usaron Windows, Java 21.0.13.1 y la caché local de dependencias de Maven. Ambos servicios reales arrancaron con `mvnw.cmd spring-boot:run` desde sus respectivos directorios.

| Comprobación con `curl.exe` | Estado | Respuesta observada |
|---|---|---|
| Consulta directa al receptor, ID `1` | `200` | Asignatura de demostración. |
| Consulta a través del emisor, ID `1` | `200` | La misma asignatura. |
| Consulta al emisor, ID `0` | `400` | `ID_INVALIDO`. |
| Consulta al emisor, ID `2` | `404` | `ASIGNATURA_NO_ENCONTRADA`. |
| Consulta al emisor tras detener el receptor | `503` | `SERVICIO_ACADEMICO_NO_DISPONIBLE`. |
| Consulta tras reiniciar solo el receptor | `200` | La consulta se recuperó con el mismo proceso emisor. |

Los seis casos coincidieron con el README. El primer intento de clonar en una ruta larga falló al extraer un PDF de referencia. Se repitió el clon en una ruta temporal más corta y se completó la ejecución. Los procesos de prueba se detuvieron al finalizar.

Se revisaron las tres capturas principales existentes. En ellas se ven el usuario, el equipo, la fecha, la hora, los puertos y las respuestas HTTP. La comprobación técnica asistida queda como respaldo para [#34](https://github.com/Yukisan-uwo/PFC-SGRA/issues/34) y [#21](https://github.com/Yukisan-uwo/PFC-SGRA/issues/21). La reproducción por un segundo integrante sigue pendiente de confirmación.
