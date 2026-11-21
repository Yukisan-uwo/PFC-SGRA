# ADR-001 — Decisión sobre el estilo de arquitectura del SGRA

**Estado:** Propuesta pendiente de revisión y aprobación del equipo.

**Fecha de registro:** 17 de noviembre de 2026  
**Participantes:** Mario Zambrano, Belinda Toaquiza, Bryan Lombeida, Khriz Coronel

## Contexto y problema

El SGRA apoya la organización y el seguimiento de los refuerzos académicos de la Universidad Técnica Estatal de Quevedo. El [alcance acordado en ADR-003](ADR-003-alcance-reutilizacion-tecnologias.md#alcance) contempla a estudiantes, docentes, coordinación y administración. Se propone revisar con el equipo la incorporación formal del perfil de gestión de espacios físicos; no se considera aprobado en este registro.

En la implementación previa, la SPA Angular consume por HTTP un único backend Spring Boot organizado por capas y dominios (`academic`, `general`, `reinforcement` y `security`). El backend utiliza PostgreSQL y se integra con Azure Blob Storage, correo SMTP y validación asistida de cargas con Groq. Esas dependencias externas no convierten los dominios internos en servicios desplegables por separado. La [revisión del código previo](../referencias/analisis-arquitectura-sgra.json) fue estática y encontró que algunas consultas académicas se resuelven mediante funciones de la base de datos desde el módulo de refuerzos; los paquetes no acreditan fronteras de persistencia.

La decisión consiste en determinar si la provisión de información académica debe seguir dentro del backend o quedar a cargo de un sistema separado, y si existe razón suficiente para dividir también el núcleo de refuerzos. Los procesos de un sistema distribuido cooperan mediante comunicación por red ([Fu et al., introducción](https://doi.org/10.1145/3708476)). Esto introduce fallos parciales: una parte puede fallar mientras otras continúan operando ([van Steen y Tanenbaum, cap. 8, p. 462](https://distributed-systems.net/index.php/books/ds4/)).

## Opciones consideradas

| Opción | Descripción | Beneficios | Costos y riesgos |
|---|---|---|---|
| **A. Backend monolítico modular** | La gestión de refuerzos y la provisión de catálogos académicos permanecen en una unidad de despliegue. | Aprovecha la base existente y evita una llamada remota nueva para las consultas internas. | Los módulos se despliegan juntos y, si se replica el backend, se replica la unidad completa. |
| **B. Núcleo modular integrado con un SGA simulado** | El SGRA gestiona los refuerzos; el SGA simulado proporciona los datos académicos acordados mediante una API. | Delimita la responsabilidad académica sin fragmentar el núcleo existente. | Las operaciones que consultan el SGA incorporan una dependencia remota y requieren un contrato y un tratamiento explícito de errores. |
| **C. Microservicios por dominio dentro del SGRA** | Además de separar la provisión de datos académicos, se dividen responsabilidades internas de refuerzos en servicios desplegables por separado cuando exista una frontera que lo justifique. | Podría permitir el despliegue y el escalado selectivo de esos servicios. | Añade trabajo de comunicación, pruebas, monitoreo y autorización; las escrituras distribuidas requieren coordinación adicional cuando involucren varios almacenes. |

Un monolito es una unidad de despliegue, no necesariamente una sola instancia. A la inversa, varios servicios pueden compartir un servidor y caer juntos si este no tiene respaldo. La comparación de estilos no presupone una topología ni redundancia para ninguna alternativa. El escalado conjunto o selectivo se distingue en [Söylemez et al., §2](https://doi.org/10.3390/app12115507); las tareas adicionales de migración, integración y operación se documentan en [Michael Ayas et al., §§4.2.6 y 4.3.5–4.3.7](https://doi.org/10.1007/s10664-023-10308-9).

La **mensajería asíncrona** es un complemento, no una alternativa excluyente. Podría servir para notificaciones diferidas si se identifica esa necesidad. La entrega al menos una vez permite que un mensaje vuelva a procesarse, por lo que deben contemplarse efectos duplicados, por ejemplo mediante operaciones idempotentes ([Maharjan et al., introducción](https://doi.org/10.3390/telecom4020018); [Bonte et al., §2.3](https://doi.org/10.1007/s00778-025-00927-7)). No se propone un intermediario de mensajes para la consulta académica mínima de E1.

## Evaluación según los requisitos

- **RNF01 — Rendimiento.** El máximo de cinco segundos se comprobará en las operaciones especificadas, con hasta 100 usuarios concurrentes y 10 000 solicitudes del período académico activo, tomando Azure como entorno de referencia provisional. No existe evidencia de que el backend previo incumpla esa carga. En la comparación controlada de [Tofan, §§3–4.5](https://doi.org/10.12962/j24068535.v24i2.a1516), distribuir el backend no mejoró la latencia ni las solicitudes atendidas por segundo en la configuración ensayada; esos resultados no sustituyen las pruebas del SGRA.
- **RNF02 — Disponibilidad.** El objetivo del 99,5 % mensual se evalúa sobre las operaciones de solicitud y consulta durante el período académico, excluyendo los mantenimientos programados notificados con al menos 24 horas. Se deberán identificar las dependencias de cada operación y comprobar qué flujos continúan ante una interrupción. Los tiempos de espera y el control de concurrencia pueden limitar el impacto de una dependencia lenta, pero no sustituyen el aislamiento ni la redundancia de componentes críticos ([Botros et al., evaluación de UNFRAGILE, fig. 17](https://doi.org/10.1016/j.infsof.2024.107519); [Söylemez et al., §5.1.5](https://doi.org/10.3390/app12115507)).
- **RNF05 y RNF06 — Auditoría e integridad.** Debe conservarse el registro de las acciones críticas, impedirse la modificación no autorizada de solicitudes, sesiones y asistencias, y verificarse que los reportes coincidan con los registros de origen. Consultar un dato académico antes de escribir únicamente en el SGRA plantea problemas de disponibilidad y vigencia del dato, no una transacción entre dos almacenes. La coordinación de escrituras se evaluaría solo si se identifica una operación que modifica datos en ambos sistemas ([Söylemez et al., §5.1.2](https://doi.org/10.3390/app12115507); [Daraghmi et al., §2.1](https://doi.org/10.3390/app12126242)).

## Decisión propuesta

Se propone la **alternativa B**: mantener un núcleo modular para solicitudes, programación, sesiones, asistencia e historial e integrar un SGA simulado para proveer, por API, los datos académicos que se acuerden. El SGRA no accedería directamente a las tablas del SGA. La separación entre contrato e implementación reduce esa dependencia sin exigir que se reescriba todo el backend ([SWEBOK, cap. 3, *Software Design*](https://ieeecs-media.computer.org/media/education/swebok/swebok-v4.pdf); [Kazanavičius et al., §2.3](https://doi.org/10.3390/app12126189)).

Para E1 se propone partir de la demostración descrita en [ADR-002](ADR-002-mecanismo-comunicacion.md): `servicio-refuerzos` consulta por HTTP con JSON una asignatura sintética a `servicio-academico`. Esa consulta de solo lectura no implica haber extraído módulos del backend previo, ni demuestra una integración completa, persistencia distribuida o alta disponibilidad. Antes de cerrar los diagramas C4 se debe acordar si esos procesos representan al backend adaptado y al SGA simulado o solo a componentes de demostración.

## Consecuencias

La propuesta permitiría reutilizar el núcleo y separar la evolución de los dos sistemas si se despliegan por separado y mantienen contratos compatibles. A cambio, el equipo deberá definir quién administra cada dato, manejar indisponibilidad o respuestas incompatibles y registrar el resultado de las llamadas remotas. No se presupone un mejor rendimiento ni el cumplimiento de los requisitos.

## Validación pendiente

- Revisar con el equipo el alcance del SGA simulado y el perfil de gestión de espacios frente a ADR-003.
- Alinear los nombres, límites e interacciones de ADR-002 con los diagramas C4; ejecutar la consulta mínima y comprobar el comportamiento cuando el receptor no responde.
- Contrastar la propuesta con el backend previo mediante evidencia técnica comparable. Comprobar por separado rendimiento, continuidad de los flujos, auditoría e integridad conforme a los requisitos; la demostración mínima no sustituye esas pruebas.
- Registrar la aceptación únicamente tras la revisión del equipo, con fecha, participantes y acuerdos comprobables. El registro conserva el contexto, las razones y las consecuencias de la decisión ([AWS Prescriptive Guidance, proceso de ADR](https://docs.aws.amazon.com/prescriptive-guidance/latest/architectural-decision-records/)).
