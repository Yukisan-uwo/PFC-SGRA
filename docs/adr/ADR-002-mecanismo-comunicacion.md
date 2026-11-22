# ADR-002 — Mecanismo de comunicación principal

**Estado:** Aceptado. Decisión de utilizar REST/HTTP JSON síncrono, según confirmación del equipo comunicada por Mario Zambrano.

**Fecha de registro:** 17 de noviembre de 2026

**Fecha de la propuesta:** 18 de noviembre de 2026

**Fecha de confirmación de aceptación:** 21 de noviembre de 2026

**Participantes:** Mario Zambrano, Belinda Toaquiza, Bryan Lombeida, Khriz Coronel.

## Contexto

La base técnica del SGRA contempla una aplicación web SPA desarrollada con Angular y una API Spring Boot con comunicación HTTP/JSON, persistencia mediante JPA/PostgreSQL e integraciones externas de correo y almacenamiento de recursos. El diseño actual propone utilizar Groq únicamente como apoyo a la validación del formulario de solicitudes de refuerzo, fuera de la demostración mínima. La autenticación por sesión entre el navegador y la API no establece por sí misma un mecanismo de autenticación entre servicios.

La primera entrega (E1) requiere demostrar la comunicación entre al menos dos servicios independientes, sin implementar todavía todas las reglas de negocio. Para cumplir este objetivo, se definió una consulta de asignaturas entre un servicio de refuerzos y un servicio académico. Esta interacción se relaciona con los dominios `reinforcement` y `academic` del SGRA anterior, pero no representa una extracción completa de sus módulos.

La vista C4 de contenedores representa la arquitectura propuesta del SGRA, que contempla una API modular y su comunicación con un SGA simulado. Por su parte, la demostración de E1 implementa dos procesos locales independientes que permiten comprobar el mecanismo de comunicación seleccionado, sin constituir el despliegue definitivo del sistema.

Los servicios `servicio-academico` y `servicio-refuerzos` fueron implementados utilizando Java 21, Spring Boot 4.0.10 y Maven. La versión inicial prevista para el backend completo era Spring Boot 4.0.3; la diferencia correspondiente a la demostración se documenta en [ADR-003](ADR-003-alcance-reutilizacion-tecnologias.md).

Durante las pruebas locales se verificaron la consulta satisfactoria, el tratamiento de identificadores inválidos, la respuesta ante asignaturas inexistentes y el comportamiento cuando el servicio académico deja de estar disponible. Estas comprobaciones permiten respaldar la implementación del contrato, pero no sustituyen las mediciones de rendimiento, las pruebas de disponibilidad ni la validación de los requisitos no funcionales.

## Opciones consideradas

| Opción | Tipo | Beneficios | Costos y riesgos |
|---|---|---|---|
| REST/HTTP JSON | Petición-respuesta síncrona | Coincide con las tecnologías utilizadas en el SGRA, permite definir contratos legibles y facilita las pruebas con herramientas HTTP. | Genera dependencia temporal entre servicios, requiere serialización de mensajes y tratamiento explícito de errores y tiempos de espera. |
| gRPC | Petición-respuesta síncrona para este caso | Permite definir contratos mediante Protocol Buffers y generar clientes para la comunicación entre servicios. | Introduce herramientas adicionales de definición y generación de código. No se dispone de mediciones comparables que justifiquen su adopción para esta consulta mínima. |
| Mensajería asíncrona | Comunicación mediante intercambio de mensajes | Facilita el desacoplamiento temporal y el procesamiento diferido de determinadas operaciones. | Para esta consulta requiere infraestructura adicional, correlación de solicitudes y respuestas, y tratamiento de entregas duplicadas o retrasadas. No se identificó una necesidad de procesamiento diferido para el caso mínimo. |

## Decisión aceptada

El equipo acepta utilizar **REST sobre HTTP con mensajes JSON y comunicación síncrona** como mecanismo principal para la consulta académica mínima de E1.

Esta decisión se fundamenta en la compatibilidad con la base tecnológica disponible, la simplicidad del contrato y la posibilidad de comprobar directamente la comunicación entre los servicios mediante herramientas HTTP.

La interacción funciona mediante un modelo de petición-respuesta: el servicio de refuerzos recibe una solicitud del cliente, consulta al servicio académico y espera su respuesta antes de devolver el resultado.

La decisión no establece que REST tenga mejor rendimiento que gRPC ni que sea el mecanismo más adecuado para todas las interacciones futuras del SGRA. Tampoco implica que el sistema completo deba dividirse en microservicios.

La alternativa de mensajería asíncrona podrá evaluarse posteriormente para operaciones que admitan procesamiento diferido, como determinadas notificaciones. No se incorpora un intermediario de mensajes en la demostración E1.

La aceptación de este ADR corresponde a la **elección del mecanismo de comunicación**. No implica que se hayan verificado todos los requisitos no funcionales ni que PE-U1 compare el rendimiento de REST con otras alternativas.

## Contrato de comunicación implementado para E1

| Elemento | Definición |
|---|---|
| Emisor | `servicio-refuerzos`, proceso independiente que escucha en `127.0.0.1:8081`. |
| Receptor | `servicio-academico`, proceso independiente que escucha en `127.0.0.1:8082`. |
| Entrada de la demostración | `GET http://127.0.0.1:8081/api/demo/asignaturas/{id}`. |
| Llamada entre servicios | Refuerzos ejecuta `GET http://127.0.0.1:8082/api/asignaturas/{id}` y devuelve al cliente los datos recibidos. |
| Parámetro | `id`: identificador entero positivo. El catálogo sintético contiene únicamente la asignatura con ID `1`. |
| Respuesta correcta | HTTP `200`, contenido JSON equivalente a `{"id":1,"nombre":"Asignatura de demostración"}`. |
| Identificador inválido | HTTP `400`, cuerpo `{"codigo":"ID_INVALIDO"}` para identificadores no numéricos o no positivos. El emisor rechaza estas entradas sin consultar al receptor. |
| Asignatura inexistente | HTTP `404`, cuerpo `{"codigo":"ASIGNATURA_NO_ENCONTRADA"}` para identificadores positivos diferentes de `1`. |
| Fallo de dependencia | HTTP `503`, cuerpo `{"codigo":"SERVICIO_ACADEMICO_NO_DISPONIBLE"}` cuando el receptor no está disponible, se produce un error del cliente HTTP o el contenido recibido es incompatible con el contrato esperado. |
| Espera y reintentos | El emisor utiliza `SimpleClientHttpRequestFactory`, con un segundo de espera para conexión y un segundo para lectura. Se realiza una sola tentativa, sin reintentos automáticos configurados. Estos valores no garantizan un máximo total de dos segundos para toda la petición. |
| Datos utilizados | Información sintética en memoria, sin consultas a matrículas reales ni almacenamiento de datos personales. |
| Seguridad de la demostración | Ambos procesos escuchan únicamente en loopback (`127.0.0.1`). La demostración no incorpora autenticación ni transporte cifrado entre servicios. Su exposición fuera del equipo requerirá controles adicionales. |

Los nombres y rutas anteriores corresponden al esqueleto implementado para E1 y no sustituyen los endpoints del SGRA preexistente.

La implementación del servicio receptor se relaciona con el issue [#19](https://github.com/Yukisan-uwo/PFC-SGRA/issues/19), la del emisor con el issue [#33](https://github.com/Yukisan-uwo/PFC-SGRA/issues/33) y la documentación de ejecución con el issue [#34](https://github.com/Yukisan-uwo/PFC-SGRA/issues/34).

## Implementación y comprobación del contrato

La comunicación se implementó mediante dos aplicaciones Spring Boot independientes.

El servicio académico utiliza `AsignaturaController.java` para procesar las consultas de asignaturas y devolver respuestas HTTP con mensajes JSON.

El servicio de refuerzos utiliza `ConsultaAsignaturaController.java`, que recibe las solicitudes y emplea `RestClient` para comunicarse con el receptor. El emisor valida el identificador solicitado y comprueba que la respuesta recibida corresponda a la asignatura esperada.

Los proyectos se encuentran en las siguientes ubicaciones:

- [Servicio académico](../../src/servicio-academico/).
- [Servicio de refuerzos](../../src/servicio-refuerzos/).

### Pruebas manuales realizadas

Las pruebas se ejecutaron localmente el 21 de noviembre de 2026 mediante PowerShell y `curl.exe`, con cada servicio iniciado en una terminal independiente.

| Prueba | Petición o condición | Resultado observado |
|---|---|---|
| Consulta satisfactoria | `GET /api/demo/asignaturas/1` | HTTP `200` y JSON de la asignatura sintética. |
| Identificador inválido | `GET /api/demo/asignaturas/0` | HTTP `400` y código `ID_INVALIDO`. |
| Asignatura inexistente | `GET /api/demo/asignaturas/2` | HTTP `404` y código `ASIGNATURA_NO_ENCONTRADA`. |
| Receptor no disponible | Detener el servicio académico y consultar el ID `1` desde refuerzos. | HTTP `503` y código `SERVICIO_ACADEMICO_NO_DISPONIBLE`. |
| Recuperación del receptor | Reiniciar el servicio académico y repetir la consulta del ID `1`. | HTTP `200`, sin reiniciar el servicio de refuerzos. |

La prueba de indisponibilidad consistió en detener temporalmente el servicio académico mientras el servicio de refuerzos permanecía en ejecución. La respuesta HTTP `503` confirmó que el emisor no devolvía una asignatura ficticia cuando su dependencia estaba fuera de servicio.

Después de reiniciar el receptor, se repitió la consulta y se obtuvo HTTP `200`, comprobando la recuperación de la comunicación.

Las capturas auténticas de estas pruebas se conservan en [evidencias/Implementacion/](../../evidencias/Implementacion/).

Se identifican como evidencias principales:

- [07-integracion-http-200.png](../../evidencias/Implementacion/07-integracion-http-200.png): servicios activos y consulta satisfactoria.
- [08-integracion-http-400-404.png](../../evidencias/Implementacion/08-integracion-http-400-404.png): validación de identificadores y asignaturas inexistentes.
- [09-integracion-consulta-entre-servicios.png](../../evidencias/Implementacion/09-integracion-consulta-entre-servicios.png): consulta directa al receptor y consulta mediante el emisor.

También se conservaron capturas complementarias del error HTTP `503` y de la recuperación posterior.

**Alcance de las pruebas:** además de las capturas manuales, el registro de integración documenta ocho comprobaciones automatizadas con un receptor controlado, incluidos HTTP `500`, ID incompatible, JSON malformado, agotamiento de lectura y recuperación. No son mediciones de rendimiento bajo carga ni de disponibilidad prolongada del SGRA.

## Consecuencias

- **Beneficios:** el mecanismo aceptado permite una comunicación directa entre dos procesos independientes, con un contrato pequeño, mensajes legibles y pruebas reproducibles mediante herramientas HTTP.
- **Dependencia temporal:** el servicio de refuerzos necesita que el servicio académico responda para completar correctamente la consulta. Si el receptor no está disponible, la operación puede fallar.
- **Manejo de errores:** el emisor informa la indisponibilidad mediante HTTP `503`, en lugar de presentar una consulta fallida como si hubiera sido satisfactoria.
- **Recuperación:** las pruebas locales demostraron que, al reiniciar el receptor, el emisor puede volver a consultar la asignatura sin necesidad de reiniciarse.
- **Observabilidad:** las capturas y respuestas HTTP permiten comprobar manualmente los estados de la interacción. Para una implementación posterior se propone incorporar registros estructurados de servicio, ruta, resultado y duración, sin exponer credenciales ni datos personales.
- **Seguridad:** la demostración es exclusivamente local. Una integración entre equipos o entornos diferentes necesitará autenticación entre servicios y protección del transporte.
- **Limitaciones arquitectónicas:** estas pruebas no acreditan persistencia distribuida, replicación, tolerancia a particiones de red, alta disponibilidad ni cumplimiento de los requisitos no funcionales del sistema completo.

## Evidencia y condiciones de aceptación

| Comprobación | Evidencia requerida | Estado actualizado |
|---|---|---|
| Fundamentación empírica | Resultados originales de PE-U1: mecanismos ensayados, equipo y red, carga, tamaño de mensajes, repeticiones, métricas, unidades y errores. | Incorporados en la sección 9.4 del informe: 4 000 intentos de TCP/JSON y gRPC/Protobuf, en tres procesos locales Python. CSV y metadatos en `evidencias/PE-U1/`; verificación en `docs/referencias/revision-pe-u1.json`. REST y mensajería no fueron medidos. |
| Coherencia del diseño | Correspondencia entre el C4 nivel 2, el inventario de interacciones y el contrato de comunicación. | Revisada documentalmente. El C4 distingue la arquitectura propuesta del SGRA y la demostración local de E1. |
| Consulta correcta | Ejecución de ambos servicios, petición al receptor y respuesta HTTP `200` con el JSON esperado. | Verificada mediante las pruebas locales y las capturas `07` y `09`. |
| Identificadores y fallos | HTTP `400`, `404`, `503`, recuperación, respuestas incompatibles y tiempos de espera agotados. | Verificados manualmente `400`, `404`, `503` por receptor detenido y recuperación a `200`. El registro de integración documenta, por separado, las comprobaciones automatizadas de respuesta incompatible, HTTP `500` y agotamiento de lectura. |
| Documentación reproducible | Dependencias, configuración, comandos de arranque, peticiones de prueba y respuestas esperadas. | Documentada en `README.md` y en el apartado 14. Reproducción técnica asistida desde clon limpio registrada en `evidencias/Implementacion/README-integracion.md`; pendiente confirmación de un segundo integrante. |
| Aceptación del mecanismo | Confirmación de la decisión arquitectónica por el equipo y registro de las condiciones y limitaciones. | **Aceptado**, según la confirmación del equipo comunicada por Mario Zambrano el 21 de noviembre de 2026. La aceptación no constituye una validación experimental de los RNF. |

### Verificaciones pendientes

Aunque el mecanismo de comunicación fue aceptado y su implementación mínima fue comprobada, todavía deben atenderse las siguientes actividades para completar la evidencia técnica:

1. Solicitar a otro integrante la reproducción de ambos servicios desde un clon limpio del repositorio.
2. Verificar en la integración final que el informe, el C4, ADR-002 y el código publicado describan el mismo mecanismo.
3. Completar las pruebas adicionales de manejo de fallos si el equipo decide incluirlas en el alcance de E1.

Si una alternativa no fue medida en PE-U1, se mantendrá como comparación conceptual y no se presentarán resultados experimentales inexistentes.

El issue [#16 — Documentar comunicaciones y completar ADR-002](https://github.com/Yukisan-uwo/PFC-SGRA/issues/16) puede permanecer abierto hasta reunir las evidencias pendientes y comprobar sus criterios de cierre, aunque la decisión arquitectónica ya tenga estado **Aceptado**.

## Fuentes técnicas consultadas

Código del backend previo, revisión `858ff67fb91604e7a2974efcfeaae3c0f3e40612`:

- [Dependencias Java, Spring Boot, JPA/PostgreSQL, correo y Azure Blob](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/pom.xml).
- [Consultas de asignaturas del catálogo del estudiante](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/src/main/java/com/CLMTZ/Backend/controller/reinforcement/student/StudentCatalogController.java).
- [Correo SMTP y variante con delegación asíncrona local](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/src/main/java/com/CLMTZ/Backend/service/external/impl/EmailService.java).
- [Operaciones de archivos con el cliente síncrono de Azure Blob](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/src/main/java/com/CLMTZ/Backend/service/external/impl/StorageService.java).

Estas fuentes documentan la base técnica reutilizada y las decisiones de diseño. No reemplazan las mediciones de PE-U1 ni constituyen evidencia de rendimiento del esqueleto implementado.

La estructura del ADR se apoya en la guía de registros de decisiones de arquitectura de AWS utilizada en el informe.
### Resultados originales de PE-U1 incorporados

Se consultó [la revisión 7bdd543](https://github.com/BelindaTZ/GA-SUM-02-PE-U1/tree/7bdd543dffd08b08d881db2803629abe5b1811fa), en particular `data/comparativa_procesos_20261121_150936/`. Se excluyeron el calentamiento y la comparativa anterior con hilos. Las medias, p95, intervalos de confianza del RTT y rendimiento se recalcularon desde los CSV y coincidieron con los resúmenes publicados. La comparación solo abarca TCP/JSON y gRPC/Protobuf; no demuestra una ventaja de REST ni el cumplimiento de los RNF del SGRA.
