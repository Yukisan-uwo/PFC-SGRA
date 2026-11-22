# Evidencia original de PE-U1 para el SGRA

Fuente: https://github.com/BelindaTZ/GA-SUM-02-PE-U1/tree/7bdd543dffd08b08d881db2803629abe5b1811fa

Se conservan sin modificar los CSV, metadatos y explicaciones del conjunto `comparativa_procesos_20261121_150936`. El directorio anterior con hilos y los datos de validación no se utilizan en la sección 9.4. Los README de `data/`, `analysis/` y `experiments/` son copias del repositorio de origen; sus rutas y comandos se refieren a ese proyecto.

El conjunto compara TCP/JSON y gRPC/Protobuf en tres procesos independientes sobre localhost, con cinco repeticiones por modo y tamaño: 4 000 intentos medidos, sin errores. El calentamiento se excluye. No contiene una medición de REST ni de mensajería con broker.

Los cuatro CSV crudos copiados coinciden con el manifiesto SHA-256 original. Se recalcularon medias, p95, intervalos de confianza de RTT y rendimiento desde los datos y se contrastaron con los resúmenes publicados. El resultado se conserva en [el registro de revisión](../../docs/referencias/revision-pe-u1.json). No se ejecutó una nueva campaña experimental.

La autoría corresponde a los integrantes de PE-U1 identificados en su repositorio. La copia permite revisar la evidencia utilizada por el SGRA, sin atribuir esas ejecuciones al equipo completo del PFC.
