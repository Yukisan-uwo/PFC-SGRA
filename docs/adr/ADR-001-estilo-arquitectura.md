# ADR-001 — Estilo de arquitectura

**Estado:** en evaluación  
**Fecha de registro:** 17 de noviembre de 2026  
**Participantes:** Mario Zambrano, Belinda Toaquiza, Bryan Lombeida, Khriz Coronel

## Contexto

El SGRA atiende a estudiantes, docentes, coordinación y administración en lo relacionado con solicitudes, programación, asistencia y seguimiento de refuerzos académicos. La implementación disponible organiza un backend Spring Boot 4.0.3 sobre Java 21, con una API REST JSON, persistencia JPA/PostgreSQL, autenticación por sesión HTTP y dominios `academic`, `general`, `reinforcement` y `security`. El frontend es una aplicación Angular 21 de componentes standalone que consume la API bajo `/api`.

La entrega E1 requiere un esqueleto con al menos dos servicios capaces de comunicarse. El esquema de datos no se encuentra versionado en el código y la ejecución depende de configuración externa. Estas condiciones limitan las alternativas de estilo arquitectónico que se pueden demostrar.

## Opciones consideradas

| Opción | Descripción | Beneficios | Costos y riesgos |
|---|---|---|---|
| Monolito modular por capas con SPA separada | Una sola API concentra las reglas de negocio y el acceso a datos; los módulos se organizan por dominio y la SPA consume HTTP/JSON. | Coherencia transaccional, despliegue sencillo, bajo costo operativo inicial. | Acoplamiento de despliegue, escalado conjunto de los módulos. |
| Microservicios por dominio | Cada dominio se despliega de forma independiente con su propia persistencia. | Autonomía de despliegue, escalado por capacidad, aislamiento de fallos. | Consistencia distribuida, trazabilidad entre servicios, mayor infraestructura. |
| Arquitectura orientada a eventos | Los cambios de estado se publican como eventos para otros componentes. | Desacople temporal, extensibilidad para nuevas reacciones. | Consistencia eventual, orden y duplicados de eventos, complejidad de infraestructura. |

## Consecuencias

La alternativa de monolito modular reduce la complejidad de la demostración y permite mostrar la comunicación con la SPA y con un segundo servicio mínimo. La alternativa de microservicios incrementa la autonomía pero traslada el problema a la coordinación de datos y a la operación. La alternativa orientada a eventos introduce consistencia eventual y requisitos de infraestructura que deben contrastarse con los requisitos no funcionales.
