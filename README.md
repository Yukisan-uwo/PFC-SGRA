# SGRA — Proyecto Fin de Curso, Aplicaciones Distribuidas

**Estado:** E1 en integración: esqueleto funcional implementado y probado localmente; pendientes verificación desde clon limpio e integración final del informe

**Proyecto:** Sistema de Gestión de Refuerzo Académico (SGRA).  
**Equipo:** Lombeida Escaleras Bryan Humberto; Coronel Gomez Khriz Alexander; Zambrano Parraga Luis Mario; Toaquiza Zambrano Belinda Betzabeth.
**Asignatura:** Aplicaciones Distribuidas.  
**Roles oficiales:** Khriz Coronel, Líder de Desarrollo; Bryan Lombeida, Arquitecto; Belinda Toaquiza, Responsable de Documentación; Mario Zambrano, Responsable de Calidad.


**Facultad:** Facultad de Ciencias de la Computación.

**Carrera y curso:** Ingeniería de Software, séptimo semestre, paralelo A.

**Período académico:** REGULAR 2026–2027 SPA.

**Fecha de entrega E1:** 21 de noviembre de 2026.

**Repositorio:** https://github.com/Yukisan-uwo/PFC-SGRA

**Carátula para el SGA:** [PDF de una página](docs/caratula-e1.pdf). Comparte datos y diseño con la portada del informe mediante `docs/datos-portada.tex` y `docs/portada.tex`. Para regenerarla desde `docs/`, ejecutar `pdflatex -interaction=nonstopmode -halt-on-error caratula-e1.tex`.
**Link del video: ** https://youtu.be/m74iTQC4bY4  
## Estructura

- `docs/main.tex`: portada, preámbulo, configuración y enlaces a las secciones.
- `docs/secciones/`: apartados 2 al 17 de E1; cada integrante edita los archivos asignados.
- `docs/referencias/referencias.bib`: referencias reales para `biblatex` + `biber`.
- `docs/adr/`: registros de decisiones arquitectónicas en Markdown. ADR-001 continúa propuesto; ADR-002 está aceptado para la comunicación síncrona REST/HTTP JSON y ADR-003 está aprobado para el alcance, las tecnologías y los datos críticos del SGRA.
- `docs/diagramas/`: bocetos C4 en `preliminares/` y editables de contexto y contenedores en `formales/`.
- `docs/figuras/`: exportaciones de los diagramas C4 incluidas en el informe.
- `src/`: código del esqueleto de al menos dos servicios que se comunicarán, dos servicios implementados.
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

### Descripción

Para la Entrega 1 (E1) se implementaron dos servicios independientes que se comunican mediante REST/HTTP con intercambio de datos JSON.

- `servicio-academico`: receptor que devuelve una asignatura sintética. Escucha en `127.0.0.1:8082`.
- `servicio-refuerzos`: emisor que recibe una consulta y solicita la información al servicio académico. Escucha en `127.0.0.1:8081`.

Esta implementación demuestra la comunicación entre procesos y el manejo de errores. No incluye base de datos, autenticación ni funcionalidades completas del SGRA.

### Requisitos para ejecutar

- Java JDK 21.
- Conexión a Internet para descargar las dependencias de Maven durante la primera ejecución.
- PowerShell en Windows, o una terminal compatible con Maven Wrapper en otro sistema operativo.

En Windows, clona el repositorio en una carpeta con una ruta corta. Un destino con muchos niveles puede superar el límite de longitud al extraer algunos PDF de referencia.

Ambos proyectos utilizan Spring Boot 4.0.10, Spring Web MVC y Maven.
No es necesario instalar Maven globalmente, porque cada servicio incluye Maven Wrapper.

### Configuración

El archivo `application.properties` de cada servicio contiene su configuración local.

**Servicio académico:**

```properties
spring.application.name=servicio-academico
server.port=8082
server.address=127.0.0.1
```

**Servicio de refuerzos:**

```properties
spring.application.name=servicio-refuerzos
server.port=8081
server.address=127.0.0.1
```

El emisor consulta al receptor mediante la dirección
`http://127.0.0.1:8082`.

Esta dirección está definida en
`ConsultaAsignaturaController.java`.

No se requieren credenciales, variables de entorno ni conexiones a bases de datos para la demostración.

### Iniciar el servicio académico

Abrir una terminal de PowerShell desde la raíz del repositorio y ejecutar:

```powershell
cd .\src\servicio-academico
.\mvnw.cmd spring-boot:run
```

El proceso debe iniciar en el puerto 8082.

Mantener esta terminal abierta.

### Iniciar el servicio de refuerzos

Abrir una segunda terminal desde la raíz del repositorio y ejecutar:

```powershell
cd .\src\servicio-refuerzos
.\mvnw.cmd spring-boot:run
```

El proceso debe iniciar en el puerto 8081.
Mantener ambas terminales abiertas.

### Comprobar la comunicación

Abrir una tercera terminal y ejecutar:

```powershell
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/1
```

El servicio de refuerzos consulta al académico y devuelve:

```json
{"id":1,"nombre":"Asignatura de demostración"}
```

El estado HTTP esperado es `200 OK`.

### Comprobar errores

**Identificador inválido:**

```powershell
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/0
```

Resultado esperado: HTTP 400.

```json
{"codigo":"ID_INVALIDO"}
```

**Asignatura inexistente:**

```powershell
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/2
```

Resultado esperado: HTTP 404.

```json
{"codigo":"ASIGNATURA_NO_ENCONTRADA"}
```

### Comprobar la indisponibilidad del receptor

1. Detener únicamente el servicio académico mediante `Ctrl + C` en su terminal.
2. Mantener ejecutándose el servicio de refuerzos.
3. Ejecutar:

```powershell
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/1
```

Resultado esperado: HTTP 503.

```json
{"codigo":"SERVICIO_ACADEMICO_NO_DISPONIBLE"}
```

Reiniciar el servicio académico y repetir la consulta. El resultado esperado vuelve a ser HTTP 200, sin reiniciar el servicio de refuerzos.

El cliente HTTP utiliza tiempos de espera de un segundo para conexión y un segundo para lectura. No se ha medido un límite máximo total de latencia.

### Evidencias de ejecución

Las capturas auténticas se encuentran en
`evidencias/Implementacion/`.

- [Evidencia 1: comunicación HTTP 200](evidencias/Implementacion/07-integracion-http-200.png).
- [Evidencia 2: errores HTTP 400 y 404](evidencias/Implementacion/08-integracion-http-400-404.png).
- [Evidencia 3: consulta entre servicios](evidencias/Implementacion/09-integracion-consulta-entre-servicios.png).
- [Registro completo de la integración](evidencias/Implementacion/README-integracion.md).

Las pruebas fueron realizadas localmente mediante PowerShell y `curl.exe`. La reproducción desde un clon limpio por otro integrante sigue pendiente de verificación.

**Ajuste de versión para E1:** la base inicial del backend preveía Spring Boot 4.0.3. El [receptor académico](src/servicio-academico/pom.xml) se generó con Spring Boot 4.0.10, disponible en Spring Initializr, manteniendo Java 21 y Maven. Véase [ADR-003](docs/adr/ADR-003-alcance-reutilizacion-tecnologias.md#tecnologías).

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
