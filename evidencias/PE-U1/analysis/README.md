# Análisis de resultados

`analyze.py` calcula las estadísticas de latencia y rendimiento de TCP (sockets)
y gRPC a partir de los CSV reales de `data/`, con el formato de
[Datos y esquema CSV](../data/README.md).

## Ejecución

Desde la raíz del repositorio, con las dependencias de `requirements.txt` instaladas:

```bash
python analysis/analyze.py --input data/tcp_lotes data/grpc_lotes
```

`--input` recibe una o más carpetas de corridas finales; cada una debe tener
`runs.csv` y sus `latency_*.csv`. Las carpetas de validación no se pasan al análisis.

## Entradas

- `latency_sockets.csv` / `latency_grpc.csv`: un registro por intento.
- `runs.csv`: condiciones y duración de cada lote.

Si falta una carpeta, un archivo o una columna, el script se detiene sin generar
resultados. No se usan datos de prueba.

## Criterios

- Se excluyen las filas con `warmup=1`.
- Se agrupa por `mode`, `mechanism` y `payload_bytes`; los modos `sequential`
  y `concurrent` nunca se mezclan.
- El RTT se calcula solo con intentos `status=ok`. Los `timeout` y `error`
  se cuentan aparte en `failures_summary.csv`.
- Estadísticas de RTT (ms): media, mediana, desviación estándar muestral y p95.
- Intervalo: IC 95 % con t de Student sobre la media de RTT de cada corrida
  (unidad = corrida; n = número de corridas válidas).
- Rendimiento (mensajes/s): `completed / elapsed_s` por corrida, a partir de `runs.csv`.
  No se deriva de la suma de RTT porque en concurrencia los intervalos se superponen.

## Salidas

| Archivo | Contenido |
|---|---|
| `figures/boxplot.png` | Distribución del RTT por mecanismo y tamaño (300 DPI) |
| `figures/latency_bars.png` | RTT medio con IC 95 % |
| `figures/throughput_bars.png` | Mensajes/s con IC 95 % |
| `tables/summary.tex` | Tabla LaTeX de latencia |
| `tables/throughput.tex` | Tabla LaTeX de rendimiento y fallos |
| `tables/*_summary.csv` | Los mismos resúmenes en CSV |
| `../docs/figures/boxplot.png` | Copia del boxplot para el informe |

Las salidas se regeneran en cada ejecución y no deben editarse a mano.
Los CSV crudos de `data/` nunca se modifican.
