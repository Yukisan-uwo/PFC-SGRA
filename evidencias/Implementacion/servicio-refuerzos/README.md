# Comprobación local del servicio emisor — issues #20 y #33

Esta carpeta conserva el resultado de una ejecución automatizada de Codex en el equipo del usuario el 21 de noviembre de 2026. No es una sesión de revisión del equipo ni una colección de capturas de pantalla.

## Comprobaciones realizadas

- Se compilaron los servicios con JDK 21 y Spring Boot 4.0.10. El emisor pasó 25 pruebas HTTP automatizadas con un receptor de prueba controlado; la prueba de contexto existente del receptor también pasó.
- Se iniciaron las dos aplicaciones entregadas como procesos independientes: académico en `127.0.0.1:8082` y refuerzos en `127.0.0.1:8081`.
- A través del emisor se comprobaron ID `1` (`200`), IDs `0` y `abc` (`400`), ID `2` (`404`) y la consulta con el receptor detenido (`503`).
- El registro de acceso del receptor contiene únicamente las consultas válidas por ID `1` y `2`: las entradas inválidas no produjeron llamadas salientes.
- Ambos procesos iniciados para la comprobación se detuvieron al finalizar.

## Archivos

| Archivo | Contenido |
|---|---|
| `resultado-pruebas.json` | Fecha de ejecución, entorno, respuestas observadas, resumen de las 25 pruebas y huellas de los archivos de código evaluados. |
| `servicio-refuerzos-ejecucion.txt` | Salida real del emisor, incluidas ruta, estado y duración de las llamadas. |
| `servicio-academico-ejecucion.txt` | Arranque y salida real del receptor entregado por Mario. |
| `receptor-acceso.txt` | Líneas del registro HTTP del receptor que acreditan las dos consultas recibidas. |

## Reproducción y límites

Las instrucciones de arranque y los comandos `curl.exe` están en [el README del emisor](../../../src/servicio-refuerzos/README.md). Sus pruebas HTTP se ejecutan con `mvnw.cmd test` desde ese módulo.

Los tiempos registrados son observaciones de esta ejecución local: no sustituyen PE-U1 ni constituyen una evaluación de rendimiento. La prueba con el receptor controlado comprueba fallos y cuenta solicitudes; la ejecución de las dos aplicaciones acredita además la comunicación real entre el emisor nuevo y el receptor existente.

La prueba por un segundo integrante desde clon limpio, las tres capturas exigidas en #34, el video y la aceptación del diseño continúan pendientes. El código y las pruebas se prepararon con asistencia de Codex y requieren revisión humana. El trabajo corresponde a una consulta sintética local, no a la migración completa del SGRA.
