# ADR-002 — Mecanismo de comunicación principal

**Estado:** Propuesto; pendiente de validación con PE-U1, C4 y esqueleto

**Fecha de registro:** 17 de noviembre de 2026

**Fecha de la propuesta:** 18 de noviembre de 2026

**Participantes:** Mario Zambrano, Belinda Toaquiza, Bryan Lombeida, Khriz Coronel

## Contexto

La base técnica del SGRA contempla una SPA Angular y una API Spring Boot con HTTP/JSON, persistencia JPA/PostgreSQL e integraciones de correo y almacenamiento de recursos. El diseño actual propone usar Groq únicamente como apoyo a la validación del formulario de solicitudes de refuerzo, fuera de la demostración mínima. La autenticación por sesión de la interacción navegador-API no define por sí misma la autenticación entre servicios.

E1 exige demostrar dos servicios comunicándose, sin completar todavía las reglas de negocio. Se propone una consulta de asignatura entre un servicio de refuerzos y un servicio académico. El caso se relaciona con los módulos `reinforcement` y `academic` y con las consultas de asignaturas del SGRA anterior; no representa una extracción de módulos ya realizada.

La vista C4 de contenedores representa la relación propuesta entre la API del SGRA y el SGA simulado. La demostración la ilustra mediante dos procesos locales, sin constituir su despliegue definitivo. Quedan pendientes las mediciones de PE-U1, la ejecución del esqueleto y la revisión del equipo; no se atribuyen resultados de rendimiento ni cumplimiento de RNF.

## Opciones consideradas

| Opción | Tipo | Beneficios | Costos y riesgos |
|---|---|---|---|
| REST/HTTP JSON | Petición-respuesta síncrona | Coincide con la tecnología previa, contrato legible y comprobación con herramientas HTTP. | Dependencia temporal, serialización y tratamiento explícito de errores y tiempos de espera. |
| gRPC | Petición-respuesta síncrona para este caso | Contrato definido en Protocol Buffers y generación de clientes. | Añade definición y herramientas de generación; no hay mediciones locales que justifiquen su adopción. El navegador necesitaría integración adicional, pero dos servicios backend pueden comunicarse directamente. |
| Mensajería asíncrona | Solicitud y respuesta mediante mensajes | Permite desacoplar productores y consumidores y procesar trabajo diferido. | Para esta consulta introduce broker, correlación y gestión de entrega, duplicados y espera de respuesta. No hay una necesidad de procesamiento diferido acreditada para el caso mínimo. |

## Decisión propuesta

Usar **REST sobre HTTP con JSON y comunicación síncrona** para la consulta académica mínima de E1. La persona que ejecuta la demostración espera una respuesta en la misma petición. La elección reduce las herramientas necesarias y permite observar la llamada y sus fallos. No se sostiene que REST sea más rápido que gRPC ni que resulte suficiente para todas las interacciones futuras.

Esta decisión no resuelve ADR-001 ni obliga a extraer todo el backend como microservicios. La tabla de interacciones del informe distingue las relaciones de la arquitectura C4 de la llamada específica de la demostración. La clasificación síncrona describe la espera de respuesta; utilizar HTTP no implica necesariamente REST, ni delegar correo a un hilo equivale a usar un broker.

## Contrato propuesto para E1

| Elemento | Definición |
|---|---|
| Emisor | `servicio-refuerzos`, proceso independiente en `127.0.0.1:8081`. |
| Receptor | `servicio-academico`, proceso independiente en `127.0.0.1:8082`. |
| Entrada de la demostración | `GET http://127.0.0.1:8081/api/demo/asignaturas/{id}`. |
| Llamada entre servicios | Refuerzos ejecuta `GET http://127.0.0.1:8082/api/asignaturas/{id}` y transmite su resultado al cliente. |
| Parámetro | `id`: entero positivo. El catálogo sintético contiene únicamente el ID `1`; no se consulta matrícula ni información personal. |
| Respuesta correcta | HTTP `200`, `Content-Type: application/json`, cuerpo `{"id":1,"nombre":"Asignatura de demostración"}`. |
| ID inválido | HTTP `400`, cuerpo `{"codigo":"ID_INVALIDO"}` para un valor no entero o no positivo; refuerzos no llama al receptor. |
| Asignatura inexistente | HTTP `404`, cuerpo `{"codigo":"ASIGNATURA_NO_ENCONTRADA"}` para cualquier entero positivo distinto de `1`; refuerzos conserva este resultado. |
| Fallo de dependencia | Refuerzos devuelve HTTP `503`, cuerpo `{"codigo":"SERVICIO_ACADEMICO_NO_DISPONIBLE"}` ante desconexión, tiempo agotado, respuesta `5xx` o respuesta incompatible con el contrato del receptor. |
| Espera y reintentos | Presupuesto total propuesto de dos segundos para la llamada saliente, incluyendo conexión y lectura. Una sola tentativa, sin reintentos automáticos. El límite es una configuración inicial pendiente de medición, no una garantía temporal de toda la petición. |
| Datos y seguridad de la demo | Datos sintéticos en memoria, sin escritura ni base de datos; ambos procesos escuchan solo en loopback. Sin autenticación exclusivamente en esta demostración local. Cualquier exposición fuera del equipo requiere definir autenticación entre servicios y transporte protegido. |

Los nombres y rutas son nuevos y propuestos. No sustituyen los endpoints del SGRA anterior. La implementación de los procesos corresponde a los issues #19 y #33; la ejecución reproducible y sus capturas, al #34.

## Consecuencias

- **Beneficios:** contrato pequeño, consulta sin efectos de escritura, respuesta inmediata y prueba independiente de Angular, PostgreSQL y servicios externos.
- **Costos:** refuerzos depende de la disponibilidad del servicio académico para responder correctamente; existe latencia de red y hay que validar el resultado recibido.
- **Fallos:** no se devuelve un éxito ficticio ni una asignatura almacenada localmente en refuerzos cuando falla el receptor. El cliente recibe `503` y puede repetir la consulta manualmente.
- **Observabilidad propuesta:** registrar en cada proceso el servicio, ruta, resultado y duración de la operación, sin credenciales ni datos personales. Los registros y capturas deberán evidenciar que la respuesta procede del receptor.
- **Límites:** esta consulta no demuestra persistencia distribuida, replicación, alta disponibilidad, mejora de rendimiento ni las reglas completas de solicitud de refuerzo. Las políticas de creación de solicitudes, programación y asistencia se decidirán con sus requisitos.

## Evidencia y condiciones de aceptación

| Comprobación | Evidencia requerida | Situación al redactar |
|---|---|---|
| Fundamentación empírica | Informe o datos originales de PE-U1: mecanismo realmente ensayado, equipo y red, carga/tamaño de mensaje, repeticiones, métricas, unidades, errores y localizador. | Pendiente del issue #6. |
| Coherencia de diseño | C4 nivel 2 con la flecha HTTP/JSON entre la API del SGRA y el SGA simulado e inventario de las interacciones. El contrato identifica por separado los procesos de la demostración. | Cotejo documental en las secciones 8 y 9; falta verificar la llamada ejecutada. |
| Consulta correcta | Ejecución del emisor y registro de la llamada al receptor para ID `1`, con el `200` y el JSON pactado. | Pendiente de #19, #33 y #34. |
| Entradas y fallos | Pruebas de ID inválido (`400`), inexistente (`404`), receptor detenido, respuesta incompatible y espera agotada (`503`), sin reintentos. | Pendiente de implementación y prueba. |
| Aceptación del ADR | Revisión del equipo y fecha de aceptación, conservando las limitaciones y el respaldo de PE-U1. | Pendiente; estado Propuesto. |

Al recibir PE-U1 se resumirán solo los resultados realmente obtenidos, con sus condiciones y limitaciones. Si no se ensayaron las alternativas, no se presentará una comparación empírica inexistente. La propuesta se revisará si la evidencia o los RNF la contradicen. El issue #16 permanece abierto hasta comprobar sus criterios de cierre; redactar este ADR no acredita la comunicación ejecutada.

## Fuentes técnicas consultadas

Código del backend previo, revisión `858ff67fb91604e7a2974efcfeaae3c0f3e40612`:

- [Dependencias Java, Spring Boot, JPA/PostgreSQL, correo y Azure Blob](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/pom.xml).
- [Consultas de asignaturas del catálogo del estudiante](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/src/main/java/com/CLMTZ/Backend/controller/reinforcement/student/StudentCatalogController.java).
- [Correo SMTP y variante con delegación asíncrona local](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/src/main/java/com/CLMTZ/Backend/service/external/impl/EmailService.java).
- [Operaciones de archivos con el cliente síncrono de Azure Blob](https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker/blob/858ff67fb91604e7a2974efcfeaae3c0f3e40612/src/main/java/com/CLMTZ/Backend/service/external/impl/StorageService.java).

Estas fuentes documentan la base técnica reutilizada. No reemplazan las mediciones de PE-U1 ni acreditan una ejecución de E1. La estructura del registro se apoya en la guía de AWS citada en el informe.
