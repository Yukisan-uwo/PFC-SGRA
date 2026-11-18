# ADR-002 — Mecanismo de comunicación principal

**Estado:** en evaluación  
**Fecha de registro:** 17 de noviembre de 2026  
**Participantes:** Mario Zambrano, Belinda Toaquiza, Bryan Lombeida, Khriz Coronel

## Contexto

La SPA realiza peticiones a la API mediante rutas bajo `/api`; en desarrollo, el proxy redirige esas peticiones al servicio backend en el puerto 8080. El interceptor HTTP de Angular añade credenciales a cada petición y el backend valida una sesión HTTP con cookie `httpOnly` y `SameSite=Lax`. Los endpoints expuestos responden JSON y admiten `multipart/form-data` para la carga de recursos. Se dispone de Actuator para observabilidad. No hay dependencias de gRPC ni de brokers de mensajería en el código revisado.

Las interacciones críticas identificadas son la creación y consulta de solicitudes, la programación y reprogramación de sesiones, el registro de asistencia y la consulta de historiales. Estas operaciones requieren confirmación inmediata para la persona que espera la respuesta.

## Opciones consideradas

| Opción | Tipo | Beneficios | Costos y riesgos |
|---|---|---|---|
| REST/HTTP JSON | Síncrono | Nativo en navegador, contratos legibles, depuración directa, compatible con la autenticación por sesión existente. | Acoplamiento temporal, latencia de la llamada, necesidad de reintentos idempotentes y manejo de errores. |
| gRPC | Síncrono | Contratos tipados, eficiencia de serialización, streaming. | Requiere capa intermedia para el navegador, mayor complejidad operativa y curva de herramientas. |
| Mensajería asíncrona | Asíncrono | Desacople temporal y absorción de picos de carga. | Consistencia eventual, orden de mensajes, infraestructura de broker y seguimiento de errores. |

## Consecuencias

REST/HTTP JSON permite conservar la autenticación por sesión y simplificar la demostración entre dos servicios. gRPC mejora el rigor del contrato pero añade una capa intermedia para el cliente web. La mensajería asíncrona reduce la dependencia temporal a costa de consistencia eventual y más infraestructura. La elección debe contrastarse con las mediciones de comunicación disponibles y con los requisitos no funcionales del informe.
