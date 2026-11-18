# SGRA — Proyecto Fin de Curso, Aplicaciones Distribuidas

**Estado:** estructura documental inicial de la Entrega 1 (E1), **no constituye una entrega completa**.

**Proyecto:** Sistema de Gestión de Refuerzo Académico (SGRA).  
**Equipo:** Mario, Belinda, Bryan y Khriz.  
**Asignatura:** Aplicaciones Distribuidas.  
**Roles oficiales:** Khriz Coronel, Líder de Desarrollo; Bryan Lombeida, Arquitecto; Belinda Toaquiza, Responsable de Documentación; Mario Zambrano, Responsable de Calidad.

**Entrega E1:** sábado 28 de noviembre de 2026, 11:59 (hora de Ecuador).

## Estructura

- `docs/main.tex`: portada, preámbulo, configuración y enlaces a las secciones.
- `docs/secciones/`: apartados 2 al 17 de E1; cada integrante edita los archivos asignados.
- `docs/referencias/referencias.bib`: referencias reales para `biblatex` + `biber`.
- `docs/adr/`: ADR-001 y ADR-002 en Markdown, pendientes de decisión.
- `docs/diagramas/`: diagramas C4 editables de contexto y contenedores, por elaborar.
- `docs/figuras/`: ilustraciones y exportaciones que se citarán en el informe.
- `src/`: código del esqueleto de al menos dos servicios que se comunicarán, por implementar.
- `evidencias/`: acta o encuesta, sesiones de diseño, capturas y demás evidencias auténticas.
- `BITACORA.md`: registro de sesiones reales, decisiones y problemas del equipo.

## Compilar el documento

Requiere una distribución LaTeX con `pdflatex` y `biber`. Desde la carpeta `docs/`:

```bash
pdflatex -interaction=nonstopmode -halt-on-error main.tex
biber main
pdflatex -interaction=nonstopmode -halt-on-error main.tex
pdflatex -interaction=nonstopmode -halt-on-error main.tex
```

Al finalizar se genera `docs/main.pdf`. Ese PDF compilado se versionará con la entrega E1, después de completar y validar el contenido. En Overleaf, seleccionar `docs/main.tex` como archivo principal y usar pdfLaTeX/Biber.

## Ejecución del esqueleto

**Pendiente de implementación.** Antes de la entrega documentar aquí las dependencias, variables de entorno de ejemplo (sin secretos), comandos exactos para iniciar al menos dos servicios, prueba de comunicación, salida esperada y procedimiento de verificación.

## GitHub Projects y video

- **Tablero de tareas E1/E2:** pendiente de enlace, asignación y cierre de issues.
- **Video de presentación (5–7 minutos):** pendiente de grabación y enlace.
- **Repositorio definitivo:** sustituir por el URL público único que se usará durante las cuatro entregas.

## Código SGRA preexistente (referencia; repositorios aún no definitivos)

- Backend: https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker
- Frontend: https://github.com/BelindaTZ/SEG-INF-front-SGRA-Docker

El código existente se incorporará a este repositorio o se reorganizará según la arquitectura que el equipo apruebe; **no** se incluye en esta plantilla. No dar por implementado el esqueleto por el hecho de existir una carpeta `src/`.

## Evidencia y autoría

Cada integrante debe tener al menos 4 commits significativos propios e issues asignados/cerrados; el historial debe abarcar al menos 4 días diferentes. Registrar únicamente entrevistas, capturas, pruebas, reuniones, medidas y resultados reales. Los entregables previos FORM-AC-01, FORM-TL-02 y FORM-IC-01 son requisitos para presentar E1.
