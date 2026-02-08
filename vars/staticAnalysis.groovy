def call(Map config = [:]) {
  // 1) Parámetro requerido: abortPipeline (por defecto false)
  boolean abortPipeline = (config.get('abortPipeline', false) as boolean)

  echo "== Static Analysis: inicio =="

  // 2) Sustitución del análisis real por un echo (requisito del enunciado)
  sh 'echo "Ejecución de las pruebas de calidad de código"'

  // 3) Simulación de Quality Gate usando variable de entorno
  String qgStatus = (env.QUALITY_GATE_STATUS ?: 'OK').toString().trim().toUpperCase()

  // 4) Timeout máximo 5 minutos (requisito del enunciado)
  timeout(time: 5, unit: 'MINUTES') {
    echo "Esperando resultado del QualityGate (simulado)..."
    sh 'sleep 2'
  }

  echo "QualityGate (simulado) => ${qgStatus}"

  // 5) Si falla y abortPipeline=true, aborta. Si abortPipeline=false, continúa
  if (qgStatus != 'OK') {
    if (abortPipeline) {
      error("QualityGate falló y abortPipeline=true => abortando pipeline.")
    } else {
      echo "QualityGate falló pero abortPipeline=false => el pipeline continúa."
    }
  }

  echo "== Static Analysis: fin =="
}
