/*
 * This file is part of MazeSolver.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2014 MazeSolver
 * Sergio M. Afonso Fumero <theSkatrak@gmail.com>
 * Kevin I. Robayna Hernández <kevinirobaynahdez@gmail.com>
 */

/**
 * @file SimulationManager.java
 * @date 3/11/2014
 */
package util;

import gui.environment.Environment;
import gui.environment.EnvironmentSet;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Gestor de la simulación.
 */
public class SimulationManager extends Observable implements Runnable {
  private static int DEFAULT_INTERVAL = 200;

  private int m_interval;
  private ScheduledThreadPoolExecutor m_executor;
  private ScheduledFuture <?> m_future;

  private EnvironmentSet m_environments;
  private boolean[] m_finished;
  private boolean m_sim_finished;

  private SimulationResults m_results;

  /**
   * Constructor por defecto del simulador.
   * @param env_set Conjunto de entornos que va a manejar.
   */
  public SimulationManager (EnvironmentSet env_set) {
    m_interval = DEFAULT_INTERVAL;
    m_executor = new ScheduledThreadPoolExecutor(1);
    m_results = new SimulationResults();
    setEnvironments(env_set);
  }

  /**
   * @param msec Milisegundos que pasarán entre cada paso de la simulación.
   */
  public void setInterval (int msec) {
    if (msec > 0)
      m_interval = msec;
  }

  /**
   * Establece el conjunto de entornos que manipula la simulación.
   * @param env_set Conjunto de entornos.
   */
  public void setEnvironments (EnvironmentSet env_set) {
    if (env_set == null)
      throw new IllegalArgumentException("No se puede asignar un entorno nulo.");

    m_environments = env_set;
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
    if (isStopped()) {
      m_finished = new boolean[m_environments.getEnvironmentCount()];
      m_results.clear();
    }

    // Lanzamos un hilo sólo si no se está ejecutando todavía
    if (!isRunning()) {
      m_future = m_executor.scheduleAtFixedRate(this, 0, m_interval,
        TimeUnit.MILLISECONDS);
      m_results.startTimer();
    }
  }

  /**
   * Pausa la simulación actual si se está ejecutando.
   */
  public void pauseSimulation () {
    if (isRunning()) {
      m_future.cancel(false);
      m_results.pauseTimer();
    }
  }

  /**
   * Para la simulación actual si se está ejecutando.
   */
  public void stopSimulation () {
    if (m_future != null) {
      m_future.cancel(false);
      m_future = null;
      m_results.pauseTimer();

      // Avisamos a los observadores que la simulación ha terminado
      setChanged();
      notifyObservers(m_results);
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

  /**
   * @return Resultados de la simulación actual. Puede ser que sean incompletos,
   * dado que puede ser que la simulación no haya acabado.
   */
  public final SimulationResults getResults () {
    return m_results;
  }

  /* (non-Javadoc)
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run () {
    int amount_finished = 0;

    // Hacemos que ejecuten un paso todos los agentes de todos los entornos
    // donde no haya acabado algún agente
    ArrayList <Environment> envs = m_environments.getEnvironmentList();
    for (int i = 0; i < envs.size(); i++) {
      if (!m_finished[i])
        m_finished[i] = envs.get(i).runStep(m_results);
      else
        amount_finished++;
    }

    // Si todos los agentes han terminado de ejecutar, paramos la simulación
    if (amount_finished == m_environments.getEnvironmentCount()) {
      m_sim_finished = true;
      stopSimulation();
    }
  }
}
