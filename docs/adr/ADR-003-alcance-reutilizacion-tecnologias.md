# ADR-003 — Alcance, reutilización, tecnologías y datos críticos

**Estado:** Aprobado  
**Fecha:** 18 de noviembre de 2026  
**Participantes:** Khriz Alexander Coronel Gomez, Bryan Humberto Lombeida Escaleras, Belinda Betzabeth Toaquiza Zambrano, Luis Mario Zambrano Parraga

## Contexto

El SGRA cubre el proceso de refuerzo académico de la UTEQ. La base disponible separa las funciones académicas, generales, de refuerzo y de seguridad, y cuenta con una interfaz organizada por roles. Para la primera entrega se requiere delimitar el sistema, identificar qué se reutiliza, fijar la base tecnológica y reconocer los datos cuya pérdida o inconsistencia afecta el proceso.

## Acuerdos

### Alcance

- Actores: estudiante, docente, coordinación académica, gestor de áreas de trabajo y administración.
- Funciones incluidas: autenticación y control de acceso; consulta de catálogos académicos de períodos, carreras, materias, paralelos y horarios provistos por el SGA simulado; solicitud de refuerzo; revisión, aceptación, rechazo y reprogramación; programación y ejecución de sesiones; registro de participantes y asistencia; carga de recursos de la sesión; historial; tablero de coordinación; administración de usuarios y roles.
- Fuera del alcance de la primera entrega: pagos, videoconferencia integrada, aplicación móvil nativa, chat propio y analítica predictiva.
- Coordinación realiza seguimiento y consulta de reportes. En el diseño propuesto, los datos académicos se consultan mediante la API del SGA simulado; no se incluye un flujo de carga de estudiantes a cargo de coordinación.
- El correo, el almacenamiento de recursos y el apoyo a la validación del formulario de solicitudes son integraciones de apoyo, no parte del dominio principal.
- La demostración de primera entrega comprende la interacción mínima entre dos servicios; las reglas completas de negocio permanecen en el alcance del sistema.

### Reutilización

- Se reutiliza la separación por capas y por dominios del backend: `academic`, `general`, `reinforcement` y `security`.
- Se reutilizan como base las funciones de autenticación por sesión, administración de usuarios y roles, consulta de catálogos académicos, solicitudes, programación, participantes, asistencia, recursos e historial. La fuente y administración de los datos académicos corresponden al SGA simulado en la integración propuesta.
- Se reutiliza la estructura de la SPA por roles y sus servicios HTTP.
- Se adaptan nombres, configuración, dependencias y documentación al PFC; no se reescribe el sistema desde cero.
- La reutilización conserva el modelo funcional existente como referencia para la implementación del esqueleto y de las entregas siguientes.

### Tecnologías

- Backend del SGRA (base prevista inicialmente): Java 21, Spring Boot 4.0.3, Maven, Spring Data JPA, PostgreSQL, Spring Session, Spring Security Crypto y Actuator.
- **Versión del esqueleto E1:** los dos proyectos implementados, [`servicio-academico`](../../src/servicio-academico/pom.xml) y [`servicio-refuerzos`](../../src/servicio-refuerzos/pom.xml), fijan Spring Boot 4.0.10, Java 21 y Maven. La base inicialmente prevista para la API completa del SGRA sigue descrita arriba con Spring Boot 4.0.3; el esqueleto no acredita que esa API ya se haya implementado o actualizado.
- Frontend: Angular 21, TypeScript 5.9, npm 11, Bootstrap 5.3 y Chart.js 4.5.
- Comunicación: REST/HTTP JSON y sesión HTTP para la interacción navegador-API; la demostración entre servicios usa HTTP síncrono.
- Servicios de apoyo: correo SMTP, almacenamiento Azure Blob y, como integración propuesta, Groq para apoyar exclusivamente la validación del formulario de solicitudes de refuerzo.
- La base tecnológica se conserva para reducir el riesgo de migración y aprovechar el conocimiento del equipo.

### Datos críticos candidatos

- Identidad y permisos: usuarios, roles y sesiones.
- Solicitudes de refuerzo y sus transiciones de estado.
- Programación de sesiones: fecha, franja horaria, modalidad, espacio y docente asignado.
- Participantes y registro de asistencia.
- Recursos y observaciones de la sesión.
- Historial de refuerzos y estados consultados por coordinación.

Estos datos se consideran críticos porque su pérdida o inconsistencia impide programar sesiones, registrar asistencia o mantener la trazabilidad del refuerzo. Requieren integridad transaccional, control de acceso, respaldo y registro de cambios.

## Consecuencias

- La reutilización reduce el esfuerzo de implementación, pero mantiene dependencia de las versiones y de la configuración del sistema existente.
- La base tecnológica es estable para el equipo, aunque exige actualización de dependencias y gestión externa de credenciales.
- Los datos críticos obligan a definir transacciones, autorización, respaldos y auditoría antes de ampliar el alcance.
- La demostración de la primera entrega verifica la comunicación entre dos servicios; no acredita por sí sola la persistencia distribuida ni la disponibilidad de todos los módulos.
