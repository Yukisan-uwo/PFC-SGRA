# Bitácora — SGRA, PFC

## 17 de noviembre de 2026

**Integrantes participantes**

Mario Zambrano.

### Actividades realizadas

- Revisé los requisitos de la primera entrega del Proyecto Fin de Curso (PFC-E1) de Aplicaciones Distribuidas.
- Organicé la estructura inicial del repositorio para trabajar con el proyecto SGRA.
- Preparé el documento LaTeX con las secciones correspondientes a los 17 apartados solicitados en la actividad.
- Separé las secciones del documento en archivos `.tex` independientes para facilitar el trabajo de los integrantes.
- Reorganicé el archivo principal `main.tex` dentro de la carpeta `docs/`.
- Integré la configuración y los paquetes de LaTeX directamente en `main.tex`.
- Preparé las carpetas `src/`, `docs/`, `docs/adr/`, `docs/diagramas/` y `evidencias/`.
- Dejé creadas las plantillas de los archivos `ADR-001` y `ADR-002` para registrar posteriormente las decisiones arquitectónicas.
- Preparé el archivo `.gitignore` para evitar incluir archivos temporales de compilación y configuraciones locales.

### Problemas y comportamientos observados

Ningun problema observado, como es la base no tuve problemas

### Solución y recuperación

Todo bien

### Resultados obtenidos

- Se estableció una estructura inicial organizada para el desarrollo del PFC-SGRA.
- El documento LaTeX quedó dividido en secciones que permiten trabajar de manera independiente.
- Se prepararon los directorios destinados al código fuente, documentación, diagramas y evidencias.
- Se dejaron las plantillas ADR listas para completar cuando se definan las decisiones arquitectónicas.
- Se verificó la compilación inicial del documento LaTeX.
- La base quedó preparada para incorporarse al repositorio GitHub y continuar con el desarrollo de la primera entrega.

### Tiempo dedicado

4 horas y 23 minutos.

## 17 de noviembre de 2026 — Planificación de E1 y E2

**Integrante participante:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Actividades realizadas

- Revisé las rúbricas y preparé la planificación y el reparto de trabajo de E1 para el tablero **PFC-SGRA | Aplicaciones Distribuidas** en GitHub Projects.
- Detallé E1 en tareas principales y subtareas con responsables, archivos a editar, dependencias, fechas objetivo y criterios de cierre.
- Distribuí las tareas de análisis, implementación, documentación y revisión entre los cuatro integrantes. Asumí la coordinación de la planificación, el resumen ejecutivo y los trabajos relacionados de E1.
- Identifiqué las entrevistas y la trazabilidad del SGRA previo como insumos para E1. Incorporé una tarea para recuperar y verificar la evidencia original antes de vincularla con los requisitos reformulados.

### Acuerdos de trabajo

- Los roles orientan las responsabilidades. Los cuatro integrantes aportan a la documentación y a la revisión de la entrega.
- El equipo decidirá en conjunto qué reutilizar del SGRA previo, qué tecnologías emplear y qué convenciones seguir para diagramas, documentación y trazabilidad.

### Tiempo dedicado

1 hora.

## 17 de noviembre de 2026 — Búsqueda de normas y guías para E1

**Integrante participante:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Actividades realizadas

- Busqué normas y guías para el levantamiento de información, la redacción y trazabilidad de requisitos, los diagramas de arquitectura y los registros de decisiones (ADR). Contrasté las ediciones con los catálogos oficiales y revisé los PDF disponibles.
- Seleccioné cinco fuentes para el equipo. SWEBOK v4.0a orienta el levantamiento. ISO/IEC/IEEE 29148:2011 sirve para redactar requisitos verificables y enlazarlos con la evidencia. ISO/IEC 25010:2011 aporta el modelo de calidad para los requisitos no funcionales. Las figuras 1 y 2 del artículo de Atodiresei, Băutu y Băutu (2024) muestran ejemplos C4 de contexto y contenedores. La guía de AWS orienta la redacción de los ADR.
- Actualicé la bibliografía con las normas, guías y el artículo seleccionado para E1. Organicé las copias de consulta en `docs/referencias/PDFs_Fuentes/` y vinculé las referencias con las secciones del informe donde se aplicarán.

### Resultados obtenidos

- La bibliografía en `docs/referencias/referencias.bib` reúne las cinco fuentes seleccionadas. Cada cita quedó junto a la indicación pendiente de la sección del informe donde el equipo la utilizará.
- Verifiqué que el documento recompilado muestra las cinco referencias.

### Tiempo dedicado

1 hora.

## 17 de noviembre de 2026 — Registro técnico del SGRA

**Integrantes participantes:** Bryan Humberto Lombeida Escaleras.

### Actividades realizadas

- Se converso la estructura del backend y del frontend para identificar módulos, dependencias y mecanismos de comunicación.
- Registré el contexto y las alternativas de estilo arquitectónico en `docs/adr/ADR-001-estilo-arquitectura.md`.
- Registré el contexto y las alternativas de comunicación en `docs/adr/ADR-002-mecanismo-comunicacion.md`.
- Actualicé las secciones del informe que recogen estos registros.

### Decisiones

- El estilo arquitectónico se evalúa entre monolito modular por capas con SPA separada, microservicios por dominio y arquitectura orientada a eventos.
- El mecanismo de comunicación se evalúa entre REST/HTTP JSON síncrono, gRPC y mensajería asíncrona.
- Los registros técnicos se conservan en `docs/adr/` y se resumen en las secciones 8, 9 y 11 del informe.

### Problemas y comportamientos observados

Ninguno.

### Solución y recuperación

Sin incidencias.

### Resultados obtenidos

- Los dos ADR contienen el contexto técnico y las opciones consideradas.
- El informe incorpora los registros en las secciones correspondientes.

### Tiempo dedicado

2 horas.
