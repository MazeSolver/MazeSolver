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
package es.ull.mazesolver.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.Timer;

import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.gui.environment.EnvironmentSet;

/**
 * Gestor de la simulación.
 */
public class SimulationManager extends Observable {
  private static int DEFAULT_INTERVAL = 200;
  private static int FAST_INTERVAL = 20;

  private Timer m_timer;
  private boolean m_paused;
  private int m_steps;

  private EnvironmentSet m_environments;
  private boolean [] m_finished;
  private boolean m_sim_finished;

  private SimulationResults m_results;

  /**
   * Constructor por defecto del simulador.
   *
   * @param env_set
   *          Conjunto de entornos que va a manejar.
   */
  public SimulationManager (EnvironmentSet env_set) {
    m_steps = -1;
    m_results = new SimulationResults();
    setEnvironments(env_set);

    m_timer = new Timer(0, new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        doStep();
      }
    });
    m_timer.setDelay(DEFAULT_INTERVAL);
    m_timer.setRepeats(true);
  }

  /**
   * Establece la velocidad de simulación al indicar el tiempo que pasa entre
   * cada paso.
   *
   * @param msec
   *          Milisegundos que pasarán entre cada paso de la simulación.
   */
  public void setInterval (int msec) {
    if (msec > 0)
      m_timer.setDelay(msec);
  }

  /**
   * Establece el conjunto de entornos que manipula la simulación.
   *
   * @param env_set
   *          Conjunto de entornos.
   */
  public void setEnvironments (EnvironmentSet env_set) {
    if (env_set == null)
      throw new IllegalArgumentException(
          MainWindow.getTranslations().exception().invalidEnvironment());

    m_environments = env_set;
  }

  /**
   * Comienza la simulación. Si está pausada, la reanuda. Nota: No se pueden
   * agregar o eliminar entornos mientras la simulación se está ejecutando.
   */
  public void startSimulation () {
    m_sim_finished = false;

    // Actualizamos el tamaño de la lista de entornos finalizados por si hay un
    // número diferente de entornos que en la última ejecución
    if (isStopped()) {
      m_finished = new boolean [m_environments.getEnvironmentCount()];
      m_results.clear();
    }

    m_paused = false;

    // Lanzamos un hilo sólo si no se está ejecutando todavía
    if (!isRunning()) {
      m_timer.start();
      m_results.startTimer();

      // Avisamos a los observadores de que la simulación ha cambiado de estado
      setChanged();
      notifyObservers();
    }
  }

  /**
   * Comienza una simulación rápida. Hay que indicar el número de pasos que
   * se desean simular, tras los cuales la simulación se pausará. Si la
   * simulación acaba antes del número de pasos indicado, se parará.
   *
   * @param steps
   *          Número de pasos máximo que se simulará.
   */
  public void startFastSimulation (int steps) {
    if (steps > 0) {
      m_steps = steps;

      setInterval(FAST_INTERVAL);
      startSimulation();
    }
  }

  /**
   * Pausa la simulación actual si se está ejecutando.
   */
  public void pauseSimulation () {
    if (isRunning()) {
      m_timer.stop();
      m_results.pauseTimer();
      m_paused = true;

      // Avisamos a los observadores de que la simulación ha cambiado de estado
      setChanged();
      notifyObservers();
    }
  }

  /**
   * Para la simulación actual si se está ejecutando.
   */
  public void stopSimulation () {
    if (isRunning() || isPaused()) {
      m_timer.stop();
      m_results.pauseTimer();
      m_paused = false;

      if (m_steps >= 0) {
        m_steps = -1;
        setInterval(DEFAULT_INTERVAL);
      }

      // Avisamos a los observadores de que la simulación ha cambiado de estado
      setChanged();
      notifyObservers(m_results);
    }
  }

  /**
   * Lleva a cabo un paso de la simulación, arrancándola si no está actualmente
   * ejecutándose. Este método está pensado para ser utilizado por el usuario
   * directamente cuando quiera hacer una ejecución paso a paso.
   */
  public void stepSimulation () {
    if (isStopped()) {
      startSimulation();
      pauseSimulation();
    }
    doStep();
  }

  /**
   * Indica si la simulación se está ejecutando.
   *
   * @return Si la simulación se está ejecutando.
   */
  public boolean isRunning () {
    return m_timer.isRunning();
  }

  /**
   * Indica si la simulación está pausada.
   *
   * @return Si la simulación está pausada.
   */
  public boolean isPaused () {
    return m_paused;
  }

  /**
   * Indica si la simulación está parada.
   *
   * @return Si la simulación está parada.
   */
  public boolean isStopped () {
    return !m_timer.isRunning() && !m_paused;
  }

  /**
   * Indica si la simulación ha finalizado; es decir, que todos los agentes en
   * los entornos están parados.
   *
   * @return Si la simulación ha acabado (todos los agentes están parados).
   */
  public boolean isFinished () {
    return m_sim_finished;
  }

  /**
   * Devuelve los resultados de la simulación actual. Puede ser que sean incompletos,
   * dado que puede ser que la simulación no haya acabado.
   *
   * @return Resultados de la simulación actual.
   */
  public final SimulationResults getResults () {
    return m_results;
  }

  /**
   * Lleva a cabo un paso de la simulación.
   */
  private void doStep () {
    // Controlamos que si el número de pasos fue especificado y ya llegó a cero,
    // que se pause la simulación y se restablezca la velocidad de ejecución
    if (m_steps == 0) {
      pauseSimulation();
      setInterval(DEFAULT_INTERVAL);
      m_steps = -1;
      return;
    }

    // Si el número de pasos fue especificado se va decrementando para parar en
    // el momento oportuno
    if (m_steps > 0)
      --m_steps;

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
