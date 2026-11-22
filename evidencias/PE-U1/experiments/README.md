# Ejecución experimental

Desde la raíz, con el entorno del proyecto activado, genera primero los módulos Protobuf y ejecuta los mecanismos por separado, porque ambos usan los puertos 5000 a 5002 de localhost:

```powershell
python -m scripts.generate_proto
python -m experiments.run_tcp_batches --mechanism sockets --output data/nueva_comparativa/sockets
python -m experiments.run_tcp_batches --mechanism grpc --output data/nueva_comparativa/grpc
python -m experiments.verify_lamport data/nueva_comparativa/sockets/events_sockets.csv data/nueva_comparativa/grpc/events_grpc.csv
python analysis/analyze.py --input data/nueva_comparativa/sockets data/nueva_comparativa/grpc
```

Usa una carpeta nueva para conservar los resultados anteriores. Cada mecanismo genera una corrida de calentamiento y cinco medidas por tamaño y modo. Cada corrida repite el plan común de 20 intercambios cinco veces. En modo concurrente, los tres emisores comienzan con una barrera y se reparten los 100 intentos en 35, 35 y 30. El rendimiento usa el tiempo de pared registrado en `runs.csv`, incluida la coordinación local.

Las mediciones utilizan tres procesos independientes en localhost, uno por nodo, con conexiones persistentes. Cada archivo de entorno registra sus puertos y PID. El tiempo de pared incluye la coordinación local entre procesos por Pipe. Los recorridos independientes de 20 intercambios usan tres procesos y se ejecutan con `python -m scripts.demo_tcp --plan experiments/plan_intercambios.csv` y `python -m scripts.demo_grpc --plan experiments/plan_intercambios.csv`. Para exportar cualquiera de ellos, ejecuta `python -m experiments.export_events CSV RUN_ID SALIDA.json` con el identificador presente en su CSV.

Los datos de la sesión del 21 de noviembre están en [la comparativa con tres procesos](../data/comparativa_procesos_20261121_150936). Para repetir el análisis de esa sesión, pasa sus subcarpetas `sockets` y `grpc` a `--input`. Las versiones que se usaron para medir están en los archivos `environment.json` de ambos mecanismos. La comparativa anterior con hilos se conserva en `data/comparativa_20261121_121021` como registro histórico.
