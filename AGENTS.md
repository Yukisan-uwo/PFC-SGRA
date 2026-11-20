# Criterios acordados para el proyecto SGRA

## Informe autónomo y procedencia del material

- El informe de Aplicaciones Distribuidas debe ser autónomo: debe poder comprenderse y evaluarse sin consultar el informe anterior del SGRA.
- Presentar el proyecto como una reformulación integral del SGRA bajo un enfoque de aplicaciones distribuidas. Reconocer brevemente que existieron investigación y desarrollo iniciales y citar su procedencia cuando corresponda.
- Recuperar, revisar y adaptar el material pertinente del trabajo anterior. Explicar en el documento actual el problema, contexto, actores, alcance, requisitos, diseño y evaluación necesarios; evitar remisiones al informe anterior que sustituyan esas explicaciones.
- El informe anterior acredita procedencia y autoría; no reemplaza la fundamentación del documento actual. Citar fuentes primarias del levantamiento y de la literatura cuando estén disponibles, conservando fechas y limitaciones reales.
- Distinguir las funciones existentes, los acuerdos aprobados, los cambios propuestos y lo efectivamente implementado y validado. No presentar la distribución como completada mientras no exista evidencia.
- No atribuir retrospectivamente al desarrollo inicial decisiones o influencias bibliográficas incorporadas en la revisión actual.

## Trabajos relacionados y fuentes

- Comparar al menos tres sistemas o estudios publicados: problema y solución, aprendizaje aplicable al SGRA y diferencias concretas de contexto, alcance, funciones o solución técnica documentada.
- Mantener EPIS–UNH como antecedente seleccionado. Los otros dos antecedentes seleccionados deben tener texto completo en inglés. La selección actual es ASP tutTrak y TutorYuk.
- Las fuentes nuevas seleccionadas deben disponer de texto completo de acceso libre y descargable. Guardar los PDF en `docs/referencias/PDFs_Fuentes/` y registrar metadatos, enlaces, localizadores y verificaciones en `docs/referencias/seleccion-issue-24.json`.
- Usar SciSpace también para búsquedas bibliográficas y contrastar sus fichas y extracciones con las publicaciones originales. No confundir DOI, presencia en buscadores e indexación de revista.
- Registrar el idioma del cuerpo del texto, distinguiéndolo del título y del resumen. La copia consultada de EPIS–UNH presenta título y resumen en inglés y español, con desarrollo en español; no afirmar que los tres textos completos están en inglés.
- Mantener referencias IEEE y diferenciar los tres trabajos relacionados de los mínimos globales de bibliografía de E1. No cerrar el issue #24 hasta comprobar todos sus criterios.
- La investigación de arquitectura y código corresponde a una etapa posterior a la selección y comparación funcional, salvo nueva instrucción del usuario.

## Edición y validación

- Redactar directamente sobre el SGRA y su alcance actual, con lenguaje académico claro. Usar pasado para los estudios realizados y las actividades efectivamente concluidas; presente para el alcance vigente y formulación de propuesta para lo pendiente.
- Conservar los archivos LaTeX como fuentes editables. Tras cambios de contenido o tablas, compilar con pdfLaTeX y Biber, resolver las citas y revisar visualmente las páginas modificadas antes de entregar el PDF.
- Registrar la asistencia de IA y los pendientes de revisión humana sin atribuir al equipo sesiones, tiempos, mediciones o decisiones que no estén acreditados.

Estos criterios fueron acordados con el usuario el 20 de noviembre de 2026 y deben orientar el trabajo futuro en este repositorio.

## Consulta del material del docente

- Fuente de consulta: `https://github.com/gleiston-guerrero/Asignaturas/tree/main/AplicacionesDistribuidas`. Distinguir `2026 SPA`, correspondiente al período actual SPA 2026–2027, de `2026 PPA` y sus materiales históricos.
- En la revisión del 20 de noviembre de 2026 se encontró el encuadre actual y las guías de E3 y E4 en `2026 SPA`, pero no una guía específica de E1 identificada en ese árbol. No aplicar automáticamente los mínimos ni las siete dimensiones de la rúbrica científica histórica `2026 PPA/rubrica_PFC_E1_cientifica.docx`: contrastar con la guía de E1 publicada en el SGA. Mantener mientras tanto los requisitos ya aportados por el usuario y distinguir las recomendaciones adicionales.
- El encuadre actual establece seis dimensiones generales, informe LaTeX reproducible desde clon limpio, bibliografía IEEE verificable, evidencia de autoría y PDF de una sola página en el SGA con la URL del repositorio escrita completa en una sola línea.
- La declaración de IA no sustituye la autoría ni la verificación humana del contenido. Mantener la asistencia expresamente identificada y no presentar propuestas, mediciones o validaciones como trabajo realizado por el equipo sin evidencia.
- Por decisión del usuario del 20 de noviembre de 2026, conservar ISO/IEC 25010:2011: es la edición cuyo texto pudo consultar gratuitamente el equipo, que no dispone de presupuesto para adquirir la de 2023. Explicitar edición y límites; no atribuir conformidad con 2023. Las guías posteriores utilizan 2023: contrastar su exigencia con la consigna aplicable.

## Alcance de la investigación técnica

- Corrección expresa del usuario: esta etapa consiste únicamente en investigar teoría, arquitecturas, tecnologías y criterios de comparación antes de decidir. La sección 7 no corresponde a nuestro trabajo: no editarla ni incorporar decisiones o recomendaciones en ADR u otras secciones del informe por esta investigación.
- Por aclaración posterior del usuario, incorporar la teoría al final de Trabajos relacionados como subapartado de fundamentos teóricos y alternativas. Relacionar conceptos con preguntas del SGRA sin adoptar alternativas, fijar arquitectura ni atribuir decisiones al equipo.
- Material: subapartado de `docs/secciones/05-relacionados.tex`, bibliografía general y `docs/referencias/analisis-arquitectura-sgra.json`. Los PDF y hallazgos de código son insumos, no decisiones.
- Por indicación del usuario, cada subapartado teórico nuevo debe fundamentarse en al menos tres fuentes pertinentes. Integrar y contrastar sus aportes; evitar acumular citas sin respaldo concreto. Mantener PDF completos de acceso libre y localizadores.
- La explicación de búsquedas, filtros, herramientas, selección y archivos de trazabilidad pertenece a la bitácora y al registro de investigación, no al cuerpo del marco teórico.
- Las observaciones de FORM-FB-01 y FORM-AC-01 son retroalimentación de otras actividades; no asumir que sus incidencias siguen pendientes o que evalúan el PFC. Véase `docs/referencias/revision-consignas-e1.json`.
