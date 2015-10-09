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
 * @file SimulationResults.java
 * @date 8/11/2014
 */
package es.ull.mazesolver.util;

import java.util.HashMap;
import java.util.Map;

import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.maze.Maze;

/**
 * Representa los resultados estadísticos de una simulación. También puede
 * contener los datos de una simulación todavía en ejecución, por lo que se
 * puede consultar para simulaciones abortadas también.
 */
public class SimulationResults {
  /**
   * Clase de utilidad para contener toda la información de una simulación en un
   * entorno determinado.
   */
  private class EnvironmentSimulationInfo {
    public Map <Agent, Integer> steps, iterations;
    public long first_elapsed, last_elapsed;
    public Agent winner_agent;

    /**
     * Constructor. Crea una nueva instancia de la clase.
     */
    public EnvironmentSimulationInfo () {
      steps = new HashMap <Agent, Integer>();
      iterations = new HashMap <Agent, Integer>();
      first_elapsed = last_elapsed = -1;
    }
  }

  private Map <Environment, EnvironmentSimulationInfo> m_info;
  private long m_acc_time, m_start_time;

  /**
   * Constructor por defecto. Inicializa los atributos de la clase.
   */
  public SimulationResults () {
    m_info = new HashMap <Environment, EnvironmentSimulationInfo>();
    // Esta variable mide el tiempo que el temporizador ha estado ejecutándose
    // incluso tras hacer pausas.
    m_acc_time = 0;
  }

  /**
   * Elimina toda la información almacenada.
   */
  public void clear () {
    m_info.clear();
    m_acc_time = m_start_time = 0;
  }

  /**
   * Comienza el temporizador para medir el tiempo que tardan los agentes en
   * encontrar la salida al laberinto.
   */
  public void startTimer () {
    m_start_time = System.currentTimeMillis();
  }

  /**
   * Pausa el temporizador. Si se vuelve a iniciar, el tiempo que estuvo antes
   * ejecutándose seguirá presente.
   */
  public void pauseTimer () {
    m_acc_time += System.currentTimeMillis() - m_start_time;
  }

  /**
   * Se indica a las estadísticas que un agente ha salido del laberinto.
   *
   * @param agent
   *          Agente que salió del laberinto.
   */
  public void agentFinished (Agent agent) {
    EnvironmentSimulationInfo info = getInfoFromAgentsEnvironment(agent);

    // Calculamos el tiempo transcurrido para registrar lo que tardó en salir
    long elapsed = m_acc_time + System.currentTimeMillis() - m_start_time;
    if (info.winner_agent == null) {
      info.first_elapsed = elapsed;
      info.winner_agent = agent;
    }
    else
      info.last_elapsed = elapsed;
  }

  /**
   * Indica a las estadísticas que un agente ha realizado un paso.
   *
   * @param agent
   *          Agente que ha realizado el paso.
   */
  public void agentWalked (Agent agent) {
    EnvironmentSimulationInfo info = getInfoFromAgentsEnvironment(agent);
    Integer steps = info.steps.getOrDefault(agent, 0);

    info.steps.put(agent, steps + 1);
  }

  /**
   * Indica a las estadísticas que se ha producido una iteración con el agente
   * dentro del laberinto.
   *
   * @param agent
   *          Agente que ha realizado el paso.
   */
  public void agentIterated (Agent agent) {
    EnvironmentSimulationInfo info = getInfoFromAgentsEnvironment(agent);

    Integer iterations = info.iterations.getOrDefault(agent, 0);
    info.iterations.put(agent, iterations + 1);
  }

  /**
   * Busca el agente que salió antes del laberinto en el entorno, si alguno ha
   * salido.
   *
   * @param env
   *          Entorno en el que buscar al ganador.
   * @return El agente que salió antes del laberinto en el entorno.
   */
  public Agent getWinner (Environment env) {
    EnvironmentSimulationInfo info = m_info.get(env);
    return info != null? info.winner_agent : null;
  }

  /**
   * Busca el agente colocado en el laberinto especificado que salió antes del
   * mismo, esté en el entorno que esté.
   *
   * @param maze
   *          Laberinto del que salió.
   * @return Agente que salió primero del laberinto, si alguno ha salido.
   */
  public Agent getWinner (Maze maze) {
    Agent winner = null;
    int winner_steps = Integer.MAX_VALUE;

    // Buscamos entre todos los entornos donde el laberinto sea el mismo, aquel
    // agente que haya llegado el primero al exterior
    for (Environment env: m_info.keySet()) {
      if (env.getMaze() == maze) {
        EnvironmentSimulationInfo info = m_info.get(env);
        if (info != null) {
          Agent other_winner = info.winner_agent;
          if (other_winner != null) {
            Integer other_steps = info.steps.get(other_winner);
            if (other_steps == null || winner == null || other_steps < winner_steps) {
              winner = other_winner;
              winner_steps = other_steps == null? 0 : other_steps.intValue();
            }
          }
        }
      }
    }

    return winner;
  }

  /**
   * Obtiene el número de pasos e iteraciones que ha realizado cada agente en el
   * entorno en la simulación actual.
   *
   * @param env
   *          Entorno en el que inspeccionar los agentes.
   * @return Número de pasos e iteraciones que ha realizado cada agente en el
   * entorno.
   */
  public Map <Agent, Pair<Integer, Integer>> getSteps (Environment env) {
    // Utilizamos el nº de agentes guardados en lugar del nº actual en el
    // entorno porque se pueden añadir y eliminar agentes en tiempo de ejecución
    EnvironmentSimulationInfo info = m_info.get(env);

    if (info != null) {
      HashMap <Agent, Pair<Integer, Integer>> steps = new HashMap <Agent, Pair<Integer, Integer>>();
      for (Agent agent: info.steps.keySet())
        steps.put(agent, new Pair <>(info.steps.get(agent),
                                     info.iterations.get(agent)));

      for (int i = 0; i < env.getAgentCount(); i++)
        if (!steps.containsKey(env.getAgent(i)))
          steps.put(env.getAgent(i), new Pair <>(0, 0));

      return steps;
    }
    else
      return new HashMap <Agent, Pair<Integer, Integer>>();
  }

  /**
   * Obtiene el tiempo que tardó el primer agente en salir del laberinto.
   *
   * @param env
   *          Entorno en el que evaluar los agentes.
   * @return Tiempo que le llevó salir del laberinto al primero que salió.
   */
  public long timeTakenFirst (Environment env) {
    EnvironmentSimulationInfo info = m_info.get(env);
    return info != null? info.first_elapsed : -1;
  }

  /**
   * Indica la cantidad de tiempo que tardó el agente más rápido en resolver el
   * laberinto, independientemente del entorno en el que se encontrara.
   *
   * @param maze
   *          Laberinto del que salió.
   * @return Tiempo que le llevó salir del laberinto al primero que salió.
   */
  public long timeTakenFirst (Maze maze) {
    long min_time = Long.MAX_VALUE;

    for (Environment env: m_info.keySet()) {
      if (env.getMaze() == maze) {
        long time = timeTakenFirst(env);
        if (time > -1 && time < min_time)
          min_time = time;
      }
    }

    return min_time != Long.MAX_VALUE? min_time : -1;
  }

  /**
   * Obtiene el tiempo que tardó el último agente en salir del entorno.
   *
   * @param env
   *          Entorno en el que evaluar los agentes.
   * @return Tiempo que le llevó salir del laberinto al último que salió.
   */
  public long timeTakenLast (Environment env) {
    EnvironmentSimulationInfo info = m_info.get(env);
    return info != null? info.last_elapsed : -1;
  }

  /**
   * Indica la cantidad de tiempo que tardó el agente más lento en resolver el
   * laberinto, independientemente del entorno en el que se encontrara.
   *
   * @param maze
   *          Laberinto del que salió.
   * @return Tiempo que le llevó salir del laberinto al último que salió.
   */
  public long timeTakenLast (Maze maze) {
    long max_time = Long.MIN_VALUE;

    for (Environment env: m_info.keySet()) {
      if (env.getMaze() == maze) {
        long time = timeTakenLast(env);
        if (time > max_time)
          max_time = time;
      }
    }

    return max_time != Long.MIN_VALUE? max_time : -1;
  }

  /**
   * Busca en m_info la información del entorno asociado al agente indicado. Si
   * no lo encuentra, lo crea.
   *
   * @param agent
   *          Agente de cuyo entorno se quiere extraer la infomación.
   * @return Información de la simulación.
   */
  private EnvironmentSimulationInfo getInfoFromAgentsEnvironment (Agent agent) {
    Environment env = agent.getEnvironment();
    EnvironmentSimulationInfo info = m_info.get(env);

    // Si no hay información almacenada de este agente, lo agregamos a la lista
    if (info == null) {
      info = new EnvironmentSimulationInfo();
      m_info.put(env, info);
    }

    return info;
  }
}
