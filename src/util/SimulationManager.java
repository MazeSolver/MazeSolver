/**
 * @file SimulationManager.java
 * @date 3/11/2014
 */
package util;

import gui.MainWindow;
import gui.environment.Environment;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Gestor de la simulación. Sólo puede haber uno, así que implementa el patrón
 * 'singleton'.
 */
public class SimulationManager extends Observable implements Runnable {
  private static int DEFAULT_INTERVAL = 10;
  private static SimulationManager s_instance;

  /**
   * @return Instancia única de la clase.
   */
  public static SimulationManager getInstance () {
    if (s_instance == null)
      s_instance = new SimulationManager();
    return s_instance;
  }

  private int m_interval;
  private ScheduledThreadPoolExecutor m_executor;
  private ScheduledFuture <?> m_future;

  private ArrayList <Environment> m_environments;
  private boolean[] m_finished;
  private boolean m_sim_finished;

  /**
   * Constructor por defecto del simulador.
   */
  private SimulationManager () {
    m_interval = DEFAULT_INTERVAL;
    m_executor = new ScheduledThreadPoolExecutor(1);
    m_environments = MainWindow.getInstance().getEnvironments().getEnvironmentList();
  }

  /**
   * @param msec Milisegundos que pasarán entre cada paso de la simulación.
   */
  public void setInterval (int msec) {
    if (msec > 0)
      m_interval = msec;
  }

  /**
   * Comienza la simulación. Si está pausada, la reanuda.
   * Nota: No se pueden agregar o eliminar entornos mientras la simulación se
   * está ejecutando.
   */
  public void startSimulation () {
    m_sim_finished = false;

    // Actualizamos el tamaño de la lista de entornos finalizados por si hay un
    // número diferente de entornos que en la última ejecución
    if (isStopped())
      m_finished = new boolean[m_environments.size()];

    m_future = m_executor.scheduleAtFixedRate(this, 0, m_interval,
        TimeUnit.MILLISECONDS);
  }

  /**
   * Pausa la simulación actual si se está ejecutando.
   */
  public void pauseSimulation () {
    if (isRunning())
      m_future.cancel(false);
  }

  /**
   * Para la simulación actual si se está ejecutando.
   */
  public void stopSimulation () {
    if (m_future != null) {
      m_future.cancel(false);
      m_future = null;
    }
  }

  /**
   * @return Si la simulación se está ejecutando.
   */
  public boolean isRunning () {
    return m_future != null && !m_future.isDone();
  }

  /**
   * @return Si la simulación está pausada.
   */
  public boolean isPaused () {
    return m_future != null && m_future.isCancelled();
  }

  /**
   * @return Si la simulación está parada.
   */
  public boolean isStopped () {
    return m_future == null;
  }

  /**
   * @return Si la simulación ha acabado (todos los agentes están parados).
   */
  public boolean isFinished () {
    return m_sim_finished;
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run () {
    int amount_finished = 0;

    // Hacemos que ejecuten un paso todos los agentes de todos los entornos
    // donde no haya acabado algún agente
    for (int i = 0; i < m_environments.size(); i++) {
      if (!m_finished[i])
        m_finished[i] = m_environments.get(i).runStep();
      else
        amount_finished++;
    }

    // Si todos los agentes han terminado de ejecutar, paramos la simulación
    if (amount_finished == m_environments.size()) {
      stopSimulation();
      m_sim_finished = true;
    }

    setChanged();
    notifyObservers();
  }

}
