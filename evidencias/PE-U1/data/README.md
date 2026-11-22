# Datos crudos y esquema CSV

La comparativa utilizada por el informe está en
`comparativa_procesos_20261121_150936/sockets/` y
`comparativa_procesos_20261121_150936/grpc/`. Sus archivos `environment.json`
registran Python, dependencias, condiciones de conexión, puertos y PID de los
procesos. Los archivos `cpu.json` y `ram.json` describen la máquina.

Las carpetas de validación y la comparativa anterior con hilos se conservan como
histórico. Para reproducir las estadísticas finales se pasan al análisis solo
las dos carpetas de la comparativa con procesos, mediante `--input`.

## Mensajes y correspondencia con los eventos

TCP/JSON y gRPC/Protobuf intercambian `message_id`, `sender`, `timestamp` y
`message`. El identificador combina corrida, nodo emisor y secuencia. El acuse
conserva ese ID, identifica al nodo que responde, incluye su reloj al enviar y
usa `message="ACK"`. Las marcas son relojes lógicos, no fechas del sistema.

El reloj aumenta antes de cada envío y al recibir aplica
`max(local, recibido) + 1`. La actualización y el registro se protegen por nodo.
Los tamaños 100 y 10240 corresponden a los bytes UTF-8 de la carga `message`,
sin incluir JSON, Protobuf, cabeceras ni identificadores. Se utiliza texto ASCII.

## Archivos y codificación

Los CSV se generan con cabecera, UTF-8, coma como separador y punto decimal.
Las columnas se mantienen en el siguiente orden:

### Eventos: events_sockets.csv / events_grpc.csv

```text
run_id,message_id,mechanism,node,peer,message_kind,event_type,lamport,physical_time
```

- `run_id`: identificador del lote de ejecución.
- `message_id`: identificador común a la solicitud, el acuse y sus eventos.
- `mechanism`: `sockets` o `grpc`.
- `node` y `peer`: nodo que registra y el otro extremo de la comunicación.
- `message_kind`: `request` o `ack`.
- `event_type`: `send` o `receive`.
- `lamport`: reloj después de aplicar la regla del evento.
- `physical_time`: hora real ISO 8601 con zona horaria.

El verificador empareja por corrida, mecanismo, mensaje y clase de mensaje,
comprobando la dirección y los nodos. La posición global de la fila y la hora
física no sustituyen al orden causal. Un intercambio correcto genera cuatro
filas: envío y recepción de la solicitud, y envío y recepción del acuse.

### Latencias: latency_sockets.csv / latency_grpc.csv

```text
run_id,message_id,mechanism,payload_bytes,mode,warmup,client_id,rtt_ms,status
```

- `payload_bytes`: bytes UTF-8 de la carga útil.
- `mode`: `sequential` o `concurrent`.
- `warmup`: 1 identifica calentamiento; 0 identifica una corrida medida.
- `client_id`: nodo que inicia la solicitud.
- `rtt_ms`: RTT en milisegundos, calculado con `time.perf_counter()` desde
  la preparación y envío hasta validar y registrar el acuse. Se excluye la
  conexión inicial. El campo queda vacío en intentos fallidos.
- `status`: `ok`, `timeout` o `error`.

### Lotes: runs.csv

```text
run_id,mechanism,payload_bytes,mode,warmup,clients,attempted,completed,failed,elapsed_s
```

Cada lote tiene un ID único y una fila. `clients` cuenta los clientes activos;
`attempted = completed + failed`. `elapsed_s` mide el tiempo de pared del lote,
incluida la coordinación local. El rendimiento se calcula como
`completed / elapsed_s`, no como el inverso del RTT medio ni usando la suma de
RTT concurrentes. El análisis excluye calentamiento y separa modos y tamaños.

## Integridad y conservación

`SHA256SUMS` contiene los hashes de los CSV publicados, con rutas relativas a
esta carpeta. Por ejemplo, desde la raíz se puede obtener el hash SHA-256 de un
archivo para contrastarlo con su línea del manifiesto:

```powershell
Get-FileHash -Algorithm SHA256 .\data\comparativa_procesos_20261121_150936\sockets\runs.csv
```

No editar, redondear ni sustituir los CSV. Las transformaciones y estadísticas
se generan en `analysis/`. Las nuevas ejecuciones utilizan directorios nuevos
para conservar los originales. Los comandos de corridas y verificación están
en [Ejecución experimental](../experiments/README.md).
