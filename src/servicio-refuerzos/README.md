# Servicio de refuerzos — demostración E1

Implementa el emisor de los issues [#20](https://github.com/Yukisan-uwo/PFC-SGRA/issues/20) y [#33](https://github.com/Yukisan-uwo/PFC-SGRA/issues/33). Realiza una llamada HTTP al servicio académico de #19. No incorpora la lógica completa de solicitudes ni una base de datos.

## Requisitos y ejecución

- JDK 21 disponible mediante `JAVA_HOME` y en `PATH`.
- Acceso a Maven Central para la primera ejecución; Maven Wrapper descarga Maven y las dependencias.
- Spring Boot 4.0.10, igual que el receptor académico publicado. Esta es la versión del esqueleto y no una aprobación de las tecnologías de producción.

Desde la raíz del repositorio, en una primera terminal PowerShell:

```powershell
cd src/servicio-academico
.\mvnw.cmd spring-boot:run
```

En una segunda terminal, también desde la raíz:

```powershell
cd src/servicio-refuerzos
.\mvnw.cmd spring-boot:run
```

El receptor escucha en `127.0.0.1:8082` y el emisor en `127.0.0.1:8081`. Ambas aplicaciones se detienen con `Ctrl+C` en sus terminales.

En una tercera terminal:

```powershell
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/1
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/0
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/abc
curl.exe -i http://127.0.0.1:8081/api/demo/asignaturas/2
```

| Caso | Resultado esperado |
|---|---|
| ID `1`, receptor disponible | `200`, `{"id":1,"nombre":"Asignatura de demostración"}`; el orden de las propiedades JSON no es relevante. |
| ID no entero, no positivo o fuera del rango entero del receptor | `400`, `{"codigo":"ID_INVALIDO"}`, sin llamada saliente. |
| ID positivo inexistente, por ejemplo `2` | `404`, `{"codigo":"ASIGNATURA_NO_ENCONTRADA"}`, recibido del receptor. |
| Receptor detenido, lento, con error o respuesta incompatible | `503`, `{"codigo":"SERVICIO_ACADEMICO_NO_DISPONIBLE"}`. |

Para comprobar la caída real, detener solo el receptor y repetir la consulta con ID `1` al emisor. No se devuelve una asignatura local de reemplazo.

## Configuración

`SGRA_ACADEMICO_BASE_URL` permite cambiar el origen del receptor antes de iniciar el emisor. Su valor predeterminado es `http://127.0.0.1:8082`.

El presupuesto predeterminado para la llamada completa, incluyendo la lectura del cuerpo, es de 2000 ms (`sgra.academico.timeout-ms`). Se permite una sola tentativa; Apache HttpClient tiene deshabilitados los reintentos automáticos y las redirecciones en esta instancia. Un fallo de formato, estado o contenido se considera una respuesta incompatible. El límite es una configuración de la demo, no una medición de PE-U1 ni una garantía de latencia del sistema completo.

Los registros del emisor muestran la ruta consultada, el estado devuelto y la duración. Su URL de salida apunta al receptor; el nombre de la asignatura procede de la respuesta HTTP recibida.

## Pruebas automatizadas

Desde `src/servicio-refuerzos/`:

```powershell
.\mvnw.cmd test
```

Las pruebas levantan el emisor en un puerto aleatorio y usan un servidor HTTP local de prueba para controlar las respuestas y contar llamadas. Cubren validación antes de contactar al receptor, respuesta correcta, `404`, errores, formato incompatible, redirecciones, desconexión y espera de cabeceras y cuerpo. El servidor de prueba no sustituye la comprobación adicional entre las dos aplicaciones entregadas.

## Alcance y autoría

El código y las pruebas se prepararon con asistencia de Codex. La revisión humana y la defensa corresponden al equipo. La demo usa datos sintéticos y escucha solo en loopback, sin autenticación. No acredita persistencia distribuida, despliegue de producción, aceptación de ADR-002 ni la revisión por un segundo integrante exigida en #34.
