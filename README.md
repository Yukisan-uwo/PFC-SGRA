# SGRA — Proyecto Fin de Curso, Aplicaciones Distribuidas

**Estado:** Entrega 1 (E1) en desarrollo, con documentación y esqueleto mínimo de dos servicios; **no constituye una entrega completa**.

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
- `docs/diagramas/`: bocetos C4 en `preliminares/` y editables de contexto y contenedores en `formales/`.
- `docs/figuras/`: exportaciones de los diagramas C4 incluidas en el informe.
- `src/`: servicio académico receptor y servicio de refuerzos emisor de la consulta mínima de E1.
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

Requiere **JDK 21**, con `JAVA_HOME` y `PATH` configurados para esa versión. Maven Wrapper descarga Maven y las dependencias en el primer arranque, por lo que necesita acceso a Internet.

Desde la raíz del repositorio, iniciar el receptor en una terminal PowerShell:

```powershell
cd src/servicio-academico
.\mvnw.cmd spring-boot:run
```

En otra terminal, desde la raíz, iniciar el emisor:

```powershell
cd src/servicio-refuerzos
.\mvnw.cmd spring-boot:run
```

Comprobar la comunicación desde una tercera terminal:

```powershell
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/1
```

Se espera HTTP `200` y `{"id":1,"nombre":"Asignatura de demostración"}`. La llamada entra por el puerto `8081` y consulta realmente al receptor en `8082`. Los registros del emisor muestran ruta, estado y duración. Para comprobar el fallo de dependencia, detener el receptor y repetir la consulta: se espera `503` con código `SERVICIO_ACADEMICO_NO_DISPONIBLE`.

Los casos `0` y `abc` producen `400` sin llamada saliente; `2` produce `404` cuando el receptor está disponible. El emisor admite `SGRA_ACADEMICO_BASE_URL` para configurar el origen del receptor, sin secretos.

Las instrucciones completas, la configuración y los comandos de prueba están en [el README del emisor](src/servicio-refuerzos/README.md). Las capturas, la prueba por un segundo integrante desde clon limpio y el video siguen pendientes de #34 y #29; estas instrucciones no acreditan que esas actividades se hayan realizado.

## GitHub Projects y video

- **Tablero de tareas E1/E2:** pendiente de enlace, asignación y cierre de issues.
- **Video de presentación (5–7 minutos):** pendiente de grabación y enlace.
- **Repositorio definitivo:** sustituir por el URL público único que se usará durante las cuatro entregas.

## Código SGRA preexistente (referencia; repositorios aún no definitivos)

- Backend: https://github.com/BelindaTZ/SEG-INF-back-SGRA-Docker
- Frontend: https://github.com/BelindaTZ/SEG-INF-front-SGRA-Docker

El código existente se incorporará a este repositorio o se reorganizará según la arquitectura que el equipo apruebe; **no** se incluye en esta plantilla. No dar por implementado el esqueleto por el hecho de existir una carpeta `src/`.

## Evidencia y autoría

El inventario del levantamiento anterior está en [evidencias/levantamiento/README.md](evidencias/levantamiento/README.md). Relaciona los audios, los consentimientos firmados, las transcripciones y una fotografía de validación. El informe incorpora un anexo de evidencias con sus fuentes y las comprobaciones pendientes del issue #8.

Cada integrante debe tener al menos 4 commits significativos propios e issues asignados/cerrados; el historial debe abarcar al menos 4 días diferentes. Registrar únicamente entrevistas, capturas, pruebas, reuniones, medidas y resultados reales. Los entregables previos FORM-AC-01, FORM-TL-02 y FORM-IC-01 son requisitos para presentar E1.
