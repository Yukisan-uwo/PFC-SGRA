# ADR-003 — Alcance, reutilización, tecnologías y datos críticos

**Estado:** Aprobado  
**Fecha:** 18 de noviembre de 2026  
**Participantes:** Khriz Alexander Coronel Gomez, Bryan Humberto Lombeida Escaleras, Belinda Betzabeth Toaquiza Zambrano, Luis Mario Zambrano Parraga

## Contexto

El SGRA cubre el proceso de refuerzo académico de la UTEQ. La base disponible separa las funciones académicas, generales, de refuerzo y de seguridad, y cuenta con una interfaz organizada por roles. Para E1 se requiere delimitar el sistema, identificar qué se reutiliza, fijar la base tecnológica y reconocer los datos cuya pérdida o inconsistencia afecta el proceso.

## Acuerdos

### Alcance

- Actores: estudiante, docente, coordinación y administración.
- Funciones incluidas: autenticación y control de acceso; catálogos académicos de periodos, carreras, materias, paralelos y horarios; solicitud de refuerzo; revisión, aceptación, rechazo y reprogramación; programación y ejecución de sesiones; registro de participantes y asistencia; carga de recursos de la sesión; historial; tablero de coordinación; administración de usuarios y roles.
- Fuera del alcance de E1: pagos, videoconferencia integrada, aplicación móvil nativa, chat propio y analítica predictiva.
- Las integraciones de correo, almacenamiento de archivos y validación asistida de cargas se mantienen como servicios de apoyo y no como dominio principal.
- La demostración de E1 comprende la interacción mínima entre dos servicios; las reglas completas de negocio permanecen en el alcance del sistema.

### Reutilización

- Se reutiliza la separación por capas y por dominios del backend: `academic`, `general`, `reinforcement` y `security`.
- Se reutilizan las funciones de autenticación por sesión, administración de usuarios y roles, catálogos académicos, solicitudes, programación, participantes, asistencia, recursos e historial.
- Se reutiliza la estructura de la SPA por roles y sus servicios HTTP.
- Se adaptan nombres, configuración, dependencias y documentación al PFC; no se reescribe el sistema desde cero.
- La reutilización conserva el modelo funcional existente como referencia para la implementación del esqueleto y de las entregas siguientes.

### Tecnologías

- Backend: Java 21, Spring Boot 4.0.3, Maven, Spring Data JPA, PostgreSQL, Spring Session, Spring Security Crypto y Actuator.
- Frontend: Angular 21, TypeScript 5.9, npm 11, Bootstrap 5.3 y Chart.js 4.5.
- Comunicación: REST/HTTP JSON y sesión HTTP para la interacción navegador-API; la demostración entre servicios usa HTTP síncrono.
- Servicios de apoyo: correo SMTP, almacenamiento Azure Blob y validación de cargas con Groq.
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
- La demostración de E1 verifica la comunicación entre dos servicios; no acredita por sí sola la persistencia distribuida ni la disponibilidad de todos los módulos.
