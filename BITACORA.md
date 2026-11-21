# Bitácora — SGRA, PFC

## 18 de noviembre de 2026 — Revisión del issue #5

**Integrante responsable de la revisión:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Revisión de la documentación disponible

- Revisé el issue #5 y el aporte de Bryan en el commit `9c47573`. Los ADR documentan módulos, dependencias y alternativas del SGRA previo.
- Contrasté ese registro con la propuesta de comunicación de Khriz del commit `0f35cd1`. ADR-001 permanece en evaluación y ADR-002 registra una propuesta pendiente de validación.
- Identifiqué pendientes para completar el cierre del issue: registrar los acuerdos sobre alcance, reutilización, tecnologías, datos críticos y convenciones de documentación; completar la fecha y los asistentes reales de la sesión; y enlazar el primer bosquejo y la fotografía con integrantes y fecha visibles.

### Resultado

El aporte técnico de Bryan está registrado. El cierre del issue #5 queda pendiente de incorporar los acuerdos y las evidencias de la sesión que solicita su descripción.

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

**Integrantes participantes:** Khriz Alexander Coronel Gomez, Bryan Humberto Lombeida Escaleras, Belinda Betzabeth Toaquiza Zambrano, Luis Mario Zambrano Parraga

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

## 18 de noviembre de 2026 — Documentación del contexto y problemática del SGRA

**Integrante participante:** Mario Zambrano.

### Actividades realizadas

- Revisé la documentación del SGRA desarrollada en semestres anteriores para recuperar información sobre el proceso de refuerzo académico en la UTEQ.
- Consulté el levantamiento de información realizado con coordinación académica, identificando las principales dificultades relacionadas con la disponibilidad docente, la comunicación y la organización de tutorías.
- Desarrollé los cuatro subapartados de `docs/secciones/03-contexto.tex`: organización y usuarios, proceso actual y consecuencias, interesados y necesidades, y levantamiento y trazabilidad.
- Elaboré dos tablas para identificar las necesidades de los interesados y relacionar los hallazgos de la entrevista con sus respectivas marcas de tiempo.
- Incorporé la referencia bibliográfica de la entrevista en `docs/referencias/referencias.bib` y revisé las citas utilizadas en el texto.
- Ajusté la presentación de las tablas en LaTeX y recompilé el documento para verificar su visualización.

### Problemas y comportamientos observados

Durante la compilación del documento, las tablas aparecían identificadas como «Cuadro» en lugar de «Tabla». También observé que la referencia de la entrevista no se mostraba correctamente debido a una diferencia entre la clave utilizada en el texto y la registrada en el archivo bibliográfico.

### Solución y recuperación

Se ajustó la configuración de LaTeX para mostrar la denominación «Tabla» y se corrigió la clave bibliográfica correspondiente. Después de recompilar el documento, las tablas y las citas se visualizaron correctamente.

### Resultados obtenidos

- Se completó la redacción inicial del apartado de contexto y planteamiento del problema del SGRA.
- Se identificaron las necesidades de los actores involucrados y las dificultades documentadas durante el levantamiento anterior.
- Se incorporaron dos tablas de apoyo para organizar los interesados y la trazabilidad de los hallazgos.
- Se verificó la presentación del contenido en el documento PDF.
- Los cambios quedaron registrados en GitHub y asociados al issue #9.

### Evidencias

- **Issue:** [#9 — Documentar el proceso actual y sus consecuencias](https://github.com/Yukisan-uwo/PFC-SGRA/issues/9).
- **Commit:** [773b0be — Desarrollar contexto y trazabilidad del SGRA](https://github.com/Yukisan-uwo/PFC-SGRA/commit/773b0be).
- **Archivos modificados:** `docs/secciones/03-contexto.tex`, `docs/referencias/referencias.bib`, `docs/main.tex` y `docs/main.pdf`.

### Tiempo dedicado

1 hora y 14 minutos.
## 18 de noviembre de 2026 — Segunda sesión del día: alcance, reutilización, tecnologías y datos críticos

**Integrantes participantes:** Khriz Alexander Coronel Gomez, Bryan Humberto Lombeida Escaleras, Belinda Betzabeth Toaquiza Zambrano, Luis Mario Zambrano Parraga.

### Actividades realizadas

- Revisé con el equipo el alcance del SGRA para E1 y la base funcional reutilizable.
- Acordamos los límites del sistema, los módulos que se reutilizan y la base tecnológica.
- Identificamos los datos críticos candidatos para el análisis de consistencia y disponibilidad.
- Registré los acuerdos en `docs/adr/ADR-003-alcance-reutilizacion-tecnologias.md` y en la sección 11 del informe.

### Acuerdos

- Alcance: estudiantes, docentes, coordinación y administración; autenticación, catálogos académicos, solicitudes, programación, asistencia, recursos, historial y administración de usuarios y roles. Quedan fuera de E1 los pagos, la videoconferencia integrada, la aplicación móvil nativa, el chat propio y la analítica predictiva.
- Reutilización: se conserva la organización por capas y dominios del backend, las funciones de refuerzo y seguridad y la estructura de la SPA por roles; se adaptan nombres, configuración y documentación al PFC.
- Tecnologías: Java 21, Spring Boot 4.0.3, Maven, Spring Data JPA, PostgreSQL, Spring Session, Angular 21, TypeScript 5.9, Bootstrap 5.3, Chart.js 4.5 y REST/HTTP JSON.
- Datos críticos: identidad y permisos, solicitudes, programación de sesiones, participantes, asistencia, recursos e historial.

### Evidencia de la sesión

![Captura de la reunión del 18 de noviembre de 2026](evidencias/Reunion-18-11-2026.png)

La captura muestra a los cuatro integrantes en la sesión y el tablero del proyecto; el reloj de la reunión indica 18/11/2026 23:48.

### Problemas y comportamientos observados

Ninguno.

### Solución y recuperación

Sin incidencias.

### Resultados obtenidos

- Los acuerdos de alcance, reutilización, tecnologías y datos críticos quedaron registrados en el ADR-003.
- El informe resume los acuerdos en la sección de registros de arquitectura.
- La sesión queda documentada con la captura de la reunión.

### Tiempo dedicado

1 hora.

## 19 de noviembre de 2026 — Recuperación y verificación de evidencias del levantamiento del SGRA

**Integrante participante:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Actividades realizadas

- Revisé las transcripciones del levantamiento anterior del SGRA y las referencias con enlaces a entrevistas, encuestas y conversaciones de validación.
- Recuperé de Google Drive cinco audios de entrevistas y tres consentimientos firmados. Revisé visualmente los consentimientos y comprobé su autorización para grabar voz y utilizar los audios con fines educativos.
- Organicé el material en `evidencias/levantamiento/`, con carpetas de audios, consentimientos y fotografías. Preparé un inventario que relaciona cada entrevista con su participante, su archivo, su enlace y el respaldo de consentimiento disponible.
- Incorporé una copia de la documentación anterior y recuperé sus cinco transcripciones, conservando las intervenciones y las marcas de tiempo disponibles.
- Recuperé una fotografía del trabajo presencial con los interesados y la incorporé como evidencia referencial.
- Añadí al informe el Anexo A de evidencias del levantamiento y trazabilidad y el Anexo B con las transcripciones de las cinco entrevistas.
- Actualicé la bibliografía y las citas del contexto para enlazar la entrevista de coordinación. Registré también las fuentes de los consentimientos y el panel de resultados de la encuesta.
- Recompilé el documento después de integrar los cambios del equipo y comprobé las referencias y la presentación de los anexos.

### Problemas y comportamientos observados

La sección de contexto citaba la carpeta de validaciones posteriores en lugar del audio de la entrevista de coordinación. Las transcripciones de Angelita Bosquez y Daisy Nata carecen de marcas de tiempo. Los tres consentimientos recuperados muestran firmas, pero carecen de fecha de firma visible. Durante la compilación, la fuente monoespaciada configurada impedía generar el PDF y algunos enlaces largos se extendían fuera de los márgenes.

### Solución y recuperación

Corregí la referencia de la entrevista de coordinación, asocié las fuentes con identificadores de entrevista y conservé las preguntas y respuestas de las transcripciones sin agregar tiempos. Documenté las limitaciones de los consentimientos y las comprobaciones pendientes. Ajusté la fuente monoespaciada y los saltos de línea de los enlaces bibliográficos para completar la compilación.

### Resultados obtenidos

- Las cinco entrevistas, los tres consentimientos firmados y la fotografía referencial quedaron organizados y enlazados en el inventario.
- El informe incluye las evidencias y las cinco transcripciones en anexos consultables desde el índice.
- Se corrigió la trazabilidad bibliográfica del contexto hacia el audio de coordinación.
- El PDF recompilado tiene 24 páginas e incorpora los cambios del equipo.
- El inventario identifica como pendientes el cotejo de los fragmentos con los audios, la recuperación de las respuestas crudas de la encuesta y las comprobaciones de acceso y fechas que correspondan al issue #8.
- Los cambios del levantamiento quedaron registrados en GitHub y asociados al issue #8.

### Evidencias

- **Issue:** [#8 — Recuperar y verificar la evidencia original](https://github.com/Yukisan-uwo/PFC-SGRA/issues/8).
- **Commit:** [482f02e — Incorpora evidencias del levantamiento y transcripciones](https://github.com/Yukisan-uwo/PFC-SGRA/commit/482f02e).
- **Inventario:** [Evidencias del levantamiento](evidencias/levantamiento/README.md).
- **Archivos modificados:** `README.md`, `docs/main.tex`, `docs/main.pdf`, `docs/referencias/referencias.bib`, `docs/secciones/03-contexto.tex`, `docs/secciones/17-referencias.tex`, `docs/secciones/18-evidencias.tex`, `docs/secciones/19-transcripciones.tex` y los archivos de `evidencias/levantamiento/`.

### Tiempo dedicado

2 horas.

## 19 de noviembre de 2026 — Definición de requisitos no funcionales del SGRA

**Integrante participante:** Mario Zambrano.

### Actividades realizadas

- Revisé el issue #12 y los requisitos de calidad del SGRA para adaptarlos a la primera entrega del PFC de Aplicaciones Distribuidas.
- Desarrollé seis requisitos no funcionales en `docs/secciones/06-requisitos.tex`, relacionados con rendimiento, disponibilidad, usabilidad, compatibilidad entre navegadores, auditoría e integridad de la información.
- Establecí las características correspondientes de ISO/IEC 25010, las metas cuantificadas, las condiciones de evaluación y los métodos de prueba.
- Redacté las justificaciones de los seis requisitos, considerando su importancia para el funcionamiento y la gestión de refuerzos académicos.
- Incorporé una tabla de trazabilidad que relaciona los requisitos RNF01–RNF06 con sus identificadores de calidad RQA.
- Compilé el documento LaTeX con pdfLaTeX y Biber para revisar la presentación de las tablas, las justificaciones y la trazabilidad en el PDF.
- Realicé ajustes puntuales en la estructura de la tabla y en la redacción de la trazabilidad.

### Problemas y comportamientos observados

Durante la revisión del PDF observé que la tabla de requisitos no funcionales se distribuía en varias páginas y presentaba una separación excesiva de palabras debido al ancho de las columnas. También identifiqué que el título de la subsección quedaba separado del inicio de la tabla.

Además, quedó pendiente precisar algunos criterios de medición, especialmente las condiciones de ejecución y la duración de las pruebas de rendimiento.

### Solución y recuperación

Realicé ajustes en la distribución de las columnas y en el salto de página de la subsección. También corregí la descripción introductoria de la tabla de trazabilidad para relacionarla directamente con los requisitos no funcionales.

### Resultados obtenidos

- Se definieron los seis requisitos no funcionales solicitados en el issue #12.
- Cada requisito cuenta con una característica de calidad, una meta verificable y un método de prueba.
- Se incorporaron las justificaciones de los umbrales establecidos.
- Se añadió la tabla de trazabilidad entre los identificadores RQA y RNF.
- Se comprobó que el contenido desarrollado aparece en el documento PDF.
- Los cambios quedaron registrados en GitHub. El issue #12 permanece abierto hasta completar la revisión final de los criterios de medición.

### Evidencias

- **Issue:** [#12 — Definir seis requisitos no funcionales verificables](https://github.com/Yukisan-uwo/PFC-SGRA/issues/12).
- **Commit:** [7b67c86 — Definir seis requisitos no funcionales verificables](https://github.com/Yukisan-uwo/PFC-SGRA/commit/7b67c86785).
- **Archivo modificado:** `docs/secciones/06-requisitos.tex`.
- **Verificación:** compilación local del documento LaTeX y revisión visual de las tablas y justificaciones en el PDF.

### Tiempo dedicado

2 horas y 54 minutos.

## 19 de noviembre de 2026 — Actualización de la planificación y responsabilidades de E1

**Integrante participante:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Actividades realizadas

- Revisé la planificación existente con la rúbrica de E1 y actualicé 33 descripciones de issues para explicar qué deben hacer los integrantes, con quién necesitan coordinarse, por qué se realiza cada tarea y qué resultado permite cerrarla.
- Creé el issue #35 para elaborar entre los cuatro integrantes el boceto preliminar de C4. La actividad contempla discutir las vistas de contexto y contenedores, conservar el primer bosquejo y registrar los acuerdos y la fotografía de la sesión con integrantes y fecha visibles.
- Vinculé el boceto como sub-issue de arquitectura (#13) y lo ubiqué inmediatamente antes de la elaboración de los C4 formales (#15). Lo asigné a los cuatro integrantes y marqué su entrega como E1 en GitHub Projects.
- Aclaré el reparto de implementación y documentación. Khriz toma la iniciativa sobre el servicio que inicia la interacción (#20), con apoyo de Mario para integrar y probar la llamada al receptor. En la documentación de ejecución y las tres capturas (#34), Khriz redacta las instrucciones y Mario las comprueba desde un clon limpio y obtiene las capturas exigidas.
- Retiré del tablero los issues duplicados #21 y #33 y los desvinculé de la tarea principal #18. Conservé #20 para implementar el emisor y #34 para documentar la ejecución y obtener las capturas.

### Problemas y comportamientos observados

Algunas descripciones ofrecían poca orientación sobre la colaboración y el propósito de las tareas. El primer boceto grupal necesitaba una actividad propia antes de los diagramas formales. Los issues #21 y #33 se habían reincorporado al tablero y duplicaban actividades ya contempladas en #34 y #20, respectivamente.

### Solución y recuperación

Desarrollé las instrucciones de las actividades con explicaciones directas y criterios de cierre comprensibles. Organicé el boceto como una tarea grupal previa al C4 formal y aclaré las responsabilidades de Khriz y Mario en la demostración y su documentación. Retiré los duplicados del proyecto y de la relación de subtareas, conservando los issues en el repositorio.

### Resultados obtenidos

- El equipo dispone de descripciones más detalladas para ejecutar y revisar el trabajo de E1.
- El boceto preliminar está asignado a los cuatro integrantes y vinculado con las tareas de arquitectura y revisión de evidencias.
- Las subtareas de arquitectura quedaron ordenadas como #14, #35, #15, #16 y #17.
- Se aclaró la colaboración para la implementación del emisor, las instrucciones de ejecución y las tres capturas.
- La tarea #18 conserva las subtareas #19 (receptor), #20 (emisor) y #34 (documentación y capturas), sin los duplicados #21 y #33 en el tablero.

### Evidencias

- **Tablero:** [PFC-SGRA | Aplicaciones Distribuidas](https://github.com/users/Yukisan-uwo/projects/1/views/6).
- **Boceto grupal:** [#35 — Elaborar en grupo el boceto preliminar de C4](https://github.com/Yukisan-uwo/PFC-SGRA/issues/35).
- **Arquitectura:** [#13](https://github.com/Yukisan-uwo/PFC-SGRA/issues/13) y [#15](https://github.com/Yukisan-uwo/PFC-SGRA/issues/15).
- **Responsabilidades aclaradas:** [#20](https://github.com/Yukisan-uwo/PFC-SGRA/issues/20) y [#34](https://github.com/Yukisan-uwo/PFC-SGRA/issues/34).
- **Subtareas depuradas:** [#18 — Implementar y demostrar el esqueleto funcional](https://github.com/Yukisan-uwo/PFC-SGRA/issues/18).

### Tiempo dedicado

30 min.

## 20 de noviembre de 2026 — Investigación asistida de trabajos relacionados (issue #24)

**Integrante participante:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Actividades realizadas

- Revisé las referencias del documento anterior del SGRA y el archivo bibliográfico original para recuperar antecedentes sobre coordinación y seguimiento de tutorías universitarias.
- Utilicé la asistencia de Codex para buscar fuentes de acceso libre y contrastar sus datos con las publicaciones originales.
- Preparé una selección inicial de EPIS–UNH, AsesoraMe y un prototipo de tutorías de la Universidad Técnica de Cotopaxi. Los dos últimos se sustituyeron posteriormente.
- Depuré las referencias seleccionadas, corregí la autoría de `Rojas2023` y distinguí artículos de revista, trabajos de congreso y tesis.
- Organicé las copias de consulta y el registro de criterios, enlaces, localizadores y verificaciones en `docs/referencias/`.
- Elaboré con asistencia las comparaciones iniciales en `docs/secciones/05-relacionados.tex` y comprobé las citas y su presentación mediante compilación y revisión visual del PDF.

### Problemas y comportamientos observados

El documento y el archivo bibliográfico anteriores presentaban discordancias entre citas y referencias, duplicados y tipos documentales incorrectos. La selección inicial requería ajustarse al idioma solicitado. Disponer de un DOI tampoco acreditaba por sí solo la indexación de una revista.

### Solución y recuperación

Contrasté las referencias seleccionadas con los textos completos y corregí sus metadatos sin importar toda la bibliografía histórica. Conservé los criterios y motivos de selección o descarte para seguir los cambios de la investigación. La actualización de los antecedentes por idioma se registra en la siguiente entrada.

### Resultados obtenidos

- Se preparó la investigación inicial del issue #24 con fuentes completas de acceso libre.
- Las referencias seleccionadas quedaron depuradas y vinculadas con sus PDF y localizadores.
- Se documentaron las inconsistencias encontradas en las fuentes anteriores.
- La investigación y redacción contaron con asistencia de Codex; la revisión y aceptación por los integrantes permanecen pendientes.
- La elaboración de las comparaciones no equivale al cierre del issue #24: falta la verificación final de sus criterios bibliográficos y de revisión.

### Evidencias

- **Issue:** [#24 — Trabajos relacionados](https://github.com/Yukisan-uwo/PFC-SGRA/issues/24).
- **Registro de investigación:** [Selección de antecedentes](docs/referencias/seleccion-issue-24.json).
- **Archivos modificados:** `docs/secciones/05-relacionados.tex`, `docs/referencias/referencias.bib` y los PDF de consulta seleccionados.
- **Verificación:** contraste de metadatos y textos completos, compilación con pdfLaTeX y Biber y revisión visual del PDF.

### Tiempo dedicado

1 hora.

## 20 de noviembre de 2026 — Actualización de los trabajos relacionados

**Integrante participante:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Actividades realizadas

- Amplié la búsqueda con asistencia de Codex y SciSpace para incorporar dos antecedentes con texto completo en inglés.
- Sustituí AsesoraMe y el prototipo de Cotopaxi por ASP tutTrak, de Grigoryan, y TutorYuk, de Seah y colaboradores.
- Conservé EPIS–UNH como antecedente recuperado del trabajo anterior y contrasté la autoría, el año, el DOI y el contenido de los tres estudios seleccionados.
- Organicé los PDF completos y actualicé la bibliografía y la comparación del informe.
- Comprobé la inclusión de las revistas de ASP tutTrak y TutorYuk en DOAJ y registré la evidencia disponible.

### Problemas y comportamientos observados

Los dos antecedentes sustituidos no cumplian del todo como trabajos relacionados.

### Solución y recuperación

Seleccioné dos artículos con desarrollo completo y conservé EPIS–UNH. Retiré de la selección activa las referencias y copias de los trabajos sustituidos, manteniendo sus enlaces y motivos de descarte en el registro de investigación.

### Resultados obtenidos

- La selección funcional quedó integrada por EPIS–UNH, ASP tutTrak y TutorYuk.
- Los tres antecedentes tienen texto completo de acceso libre guardado en la carpeta de fuentes.
- ASP tutTrak y TutorYuk cuentan con DOI verificado y evidencia de inclusión de sus revistas en DOAJ.

### Evidencias

- **Issue:** [#24 — Trabajos relacionados](https://github.com/Yukisan-uwo/PFC-SGRA/issues/24).
- **Registro de investigación:** [Selección y actualización de antecedentes](docs/referencias/seleccion-issue-24.json).
- **Fuentes:** `Huaman-Rojas-2023-EPIS-UNH.pdf`, `Grigoryan-2024-ASP-tutTrak.pdf` y `Seah-2023-TutorYuk.pdf`, en `docs/referencias/PDFs_Fuentes/`.
- **Archivos modificados:** `docs/secciones/05-relacionados.tex` y `docs/referencias/referencias.bib`.

### Tiempo dedicado

40 min.



## 20 de noviembre de 2026 — Fundamentos teóricos y alternativas para la distribución del SGRA

**Integrante participante:** TOAQUIZA ZAMBRANO BELINDA BETZABETH.

### Actividades realizadas

- Utilicé Codex y SciSpace para investigar fuentes verificables de acceso libre sobre descomposición, modularidad, migración, propiedad de datos, comunicaciones, seguridad y operación de sistemas distribuidos.
- Contrasté las fichas bibliográficas con los textos completos, los metadatos de Crossref y otras fuentes originales cuando se detectaron discrepancias.
- Realicé con asistencia una revisión dirigida del código disponible del backend y frontend para identificar preguntas y dependencias pertinentes para el SGRA, sin ejecutar los sistemas ni atribuir resultados de pruebas.
- Incorporé el subapartado 5.6, «Fundamentos teóricos y alternativas para la distribución», al final de trabajos relacionados, con cuatro subapartados de teoría y comparación.
- Amplié la investigación inicial de tres a diez fuentes técnicas, con al menos tres referencias pertinentes para cada subapartado. Relacioné sus aportes y límites con preguntas del SGRA, sin seleccionar una arquitectura.
- Organicé los diez PDF técnicos y sus enlaces, huellas, metadatos y localizadores en el registro de investigación.
- Retiré del cuerpo del informe la explicación del proceso de búsqueda y conservé su trazabilidad en el registro y en esta bitácora. La búsqueda fue exploratoria, con publicaciones abiertas de 2022–2026; no se presenta como revisión sistemática exhaustiva.
- Simplifiqué la declaración de asistencia de Codex y SciSpace en un solo párrafo sobre su propósito en trabajos relacionados y fundamentos teóricos.
- Recompilé el documento con pdfLaTeX y Biber y revisé visualmente el contenido, las tablas, la declaración de IA y las referencias.

### Problemas y comportamientos observados

Algunas fichas de SciSpace omitían autores o presentaban datos diferentes de los textos originales; tampoco se obtuvo el PDF completo de todos los candidatos.

### Solución y recuperación

Amplié y contrasté las fuentes para respaldar cada subapartado. Corregí los metadatos con los originales, distinguí la copia arXiv de Bolanowski y colaboradores de su capítulo publicado y excluí candidatos sin PDF completo verificable. Conservé en los criterios del proyecto el uso de ISO/IEC 25010:2011 por su disponibilidad de consulta gratuita para el equipo.

### Resultados obtenidos

- El informe incluye teoría sobre descomposición y servicios, migración y datos, comunicaciones y criterios complementarios antes de decidir.
- Los cuatro subapartados cuentan con al menos tres fuentes pertinentes; los diez textos técnicos citados tienen PDF de consulta guardado.
- Se explican límites de transferencia de los estudios al SGRA, incluidos aislamiento de sagas, condiciones de los experimentos y garantías no evaluadas de mensajería.
- El PDF recompilado tiene 35 páginas y no presenta citas sin resolver. Persisten advertencias anteriores de maquetación en otras secciones.
- Los cambios del informe, las referencias, los PDF y los criterios del proyecto se publicaron en `main` mediante el commit `71572f6`. Esta bitácora quedó fuera de esa publicación.
- El mensaje de ese commit no incluyó `#24`; el registro bibliográfico sí conserva el enlace al issue. La revisión humana y el cierre del issue permanecen pendientes.

### Evidencias

- **Issue:** [#24 — Trabajos relacionados](https://github.com/Yukisan-uwo/PFC-SGRA/issues/24).
- **Commit:** [71572f6 — Documenta trabajos relacionados y fundamentos teoricos del SGRA](https://github.com/Yukisan-uwo/PFC-SGRA/commit/71572f6c95f046e1b45a975b7ef63ccbb40d62c3).
- **Registro de investigación:** [Análisis y fuentes técnicas](docs/referencias/analisis-arquitectura-sgra.json).
- **Archivos modificados:** `docs/secciones/05-relacionados.tex`, `docs/secciones/16-ia.tex`, `docs/referencias/referencias.bib`, `docs/main.pdf`, `AGENTS.md` y los PDF y registros de consulta.
- **Verificación:** compilación con pdfLaTeX y Biber, revisión visual, comprobación de citas y huellas de los PDF y contraste de la restauración de sección 7, requisitos y ADR mediante Git.

### Tiempo dedicado

50 min.

## 20 de noviembre de 2026 — Revisión de ADR-001 y justificación de la arquitectura distribuida

**Integrante participante:** No consignado en el registro de la actividad.

### Actividades realizadas

- Se revisó el borrador de ADR-001 y su correspondencia con los requisitos no funcionales, la consulta mínima de ADR-002 y el alcance acordado en ADR-003.
- Se contrastaron las afirmaciones del borrador con la bibliografía existente y se revisaron las referencias propuestas en la conversación sobre comunicación entre procesos, fallos parciales, entrega de mensajes, rendimiento y contención de fallos.
- Se actualizaron el ADR-001 y su resumen en el informe. Se diferenciaron los estilos arquitectónicos de la topología de despliegue y se presentó la mensajería asíncrona como un complemento, no como una cuarta alternativa excluyente.
- Se redactó la sección 7 como una justificación autónoma. Tras revisar el borrador,se redacto a partir de las necesidades del SGRA y dejar el reconocimiento de la investigación y el desarrollo iniciales en la sección 5.

### Problemas y comportamientos observados

El borrador comparaba un monolito limitado a un solo servidor con alternativas que no tenían la misma restricción. También usaba notas provisionales de «Afirmación a respaldar», y algunas afirmaciones requerían fuentes más directas. La sección 7 describia la propuesta para poder explicar directamente a partir de la separación entre la gestión de refuerzos y la provisión de datos académicos.

### Solución y recuperación

Para las fuentes de Fu y colaboradores y de van Steen y Tanenbaum se conservaron enlaces de consulta sin redistribuir los PDF.

### Resultados obtenidos

- El ADR-001 documenta como **propuesta** mantener el núcleo modular del SGRA e integrarlo con un SGA simulado mediante una API. No se presenta como una decisión aprobada ni como una arquitectura implementada.
- La sección 7 justifica la separación de responsabilidades sin depender del trabajo anterior. Vincula la propuesta con rendimiento, disponibilidad, integridad y auditoría, y distingue la consulta sintética de E1 de las pruebas necesarias para validar esos requisitos.
- Se incorporaron cinco entradas bibliográficas y se archivaron los PDF de Bonte, Botros y Tofan. Se dejó constancia de la asistencia de IA en el informe.
- El informe de 36 páginas se recompiló con pdfLaTeX y Biber. Se revisaron visualmente las páginas modificadas y se comprobó que no quedaran citas sin resolver.
- Quedan pendientes la aprobación del ADR por el equipo, la delimitación del SGA simulado y la validación de la propuesta mediante pruebas. No se registraron como actividades concluidas.

### Evidencias

- **Decisión propuesta:** [ADR-001](docs/adr/ADR-001-estilo-arquitectura.md).
- **Informe:** [sección 7](docs/secciones/07-justificacion.tex), [resumen de ADR](docs/secciones/11-adr.tex), [declaración de IA](docs/secciones/16-ia.tex) e [informe compilado](docs/main.pdf).
- **Fuentes:** [bibliografía](docs/referencias/referencias.bib) y [registro de investigación](docs/referencias/analisis-arquitectura-sgra.json), apartado `adr001_proposal_sources`.
- **Verificación:** compilación local con pdfLaTeX y Biber, revisión visual y comprobación de citas y huellas de los PDF archivados.

### Tiempo dedicado

4 horas.
