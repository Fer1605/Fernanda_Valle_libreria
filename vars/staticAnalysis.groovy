def call(Map config = [:]) {
  boolean abortPipeline = (config.get('abortPipeline', false) as boolean)

  echo "== Static Analysis (heurística por rama) =="

  // Enunciado: sustituir ejecución real por echo
  sh 'echo "Ejecución de las pruebas de calidad de código"'

  // Simulación del Quality Gate: variable de entorno
  String qgStatus = (env.QUALITY_GATE_STATUS ?: 'OK').toString().trim().toUpperCase()

  // Detectar rama desde variables típicas de Jenkins
  String branchName = (env.BRANCH_NAME ?: env.GIT_BRANCH ?: 'unknown').toString()
  branchName = branchName.replace('origin/', '').trim()

  // Enunciado: timeout máximo 5 minutos
  timeout(time: 5, unit: 'MINUTES') {
    echo "Esperando resultado del QualityGate (simulado)..."
    sh 'sleep 2'
  }

  echo "QualityGate (simulado) => ${qgStatus}"
  echo "Rama detectada => ${branchName}"
  echo "abortPipeline => ${abortPipeline}"

  // Regla: decidir si abortar cuando falle el quality gate
  boolean shouldAbortOnFail =
    abortPipeline ||
    branchName == 'master' ||
    branchName.startsWith('hotfix')

  if (qgStatus != 'OK') {
    if (shouldAbortOnFail) {
      error("QualityGate falló y la heurística indica ABORTAR (branch=${branchName}).")
    } else {
      echo "QualityGate falló pero la heurística indica CONTINUAR (branch=${branchName})."
    }
  }

  echo "== Static Analysis: fin =="
}
