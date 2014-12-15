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
 * @file AStarAgent.java
 * @date 4/12/2014
 */
package es.ull.mazesolver.agent;

import java.awt.BorderLayout;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.ull.mazesolver.agent.distance.DistanceCalculator;
import es.ull.mazesolver.agent.util.Path;
import es.ull.mazesolver.gui.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.util.Direction;

/**
 * Agente que implementa el comportamiento del algoritmo A*.
 */
public class AStarAgent extends HeuristicAgent {
  private static final long serialVersionUID = 4696525877860033142L;
  private static final double STEP_COST = 1.0;

  private transient Point m_exit;

  private transient int m_direction_index;
  private transient ArrayList<Direction> m_directions;

  /**
   * Inicializa el agente A* con la distancia de Manhattan por defecto.
   * @param env Entorno en el que colocar al agente.
   */
  public AStarAgent (Environment env) {
    super(env);
  }

  /* (non-Javadoc)
   * @see agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "A*";
  }

  /* (non-Javadoc)
   * @see agent.Agent#setPosition(java.awt.Point)
   */
  @Override
  public void setPosition (Point pos)  {
    super.setPosition(pos);
    resetMemory();
  }

  /* (non-Javadoc)
   * @see agent.Agent#setEnvironment(gui.environment.Environment)
   */
  @Override
  public void setEnvironment (Environment env) {
    super.setEnvironment(env);
    resetMemory();
    m_exit = m_env.getMaze().getExit();
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    if (m_directions == null ||
        (m_direction_index == m_directions.size() && !m_pos.equals(m_exit)))
      calculatePath();

    return m_directions != null && m_direction_index != m_directions.size()?
           m_directions.get(m_direction_index) : Direction.NONE;
  }

  @Override
  public void doMovement (Direction dir) {
    super.doMovement(dir);
    if (m_directions.get(m_direction_index) == dir && m_directions != null)
      m_direction_index++;
  }

  /* (non-Javadoc)
   * @see agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    m_directions = null;
    m_direction_index = 0;
  }

  /* (non-Javadoc)
   * @see agent.Agent#getConfigurationPanel()
   */
  @Override
  public AgentConfigurationPanel getConfigurationPanel () {
    return new AgentConfigurationPanel() {
      private static final long serialVersionUID = 1L;
      private DistanceConfigurationPanel distance;

      @Override
      protected void createGUI (JPanel root) {
        distance = new DistanceConfigurationPanel();
        root.setLayout(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        root.add(distance, BorderLayout.NORTH);
      }

      @Override
      protected void cancel () {}

      @Override
      protected boolean accept () {
        setDistanceCalculator(DistanceCalculator.fromType(distance.getSelectedType()));
        return true;
      }
    };
  }

  /* (non-Javadoc)
   * @see agent.Agent#clone()
   */
  @Override
  public Object clone () {
    AStarAgent ag = new AStarAgent(m_env);
    ag.m_dist = (DistanceCalculator) m_dist.clone();
    return ag;
  }

  private class HeuristicPathComparator implements Comparator<Path> {
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare (Path o1, Path o2) {
      double h1 = m_dist.distance(o1.getEndPoint(), m_exit);
      double h2 = m_dist.distance(o2.getEndPoint(), m_exit);

      // La ordenación se hace por coste acumulado (m_cost) + coste estimado
      // (distancia a la salida)
      return Double.compare(o1.getCost() + h1, o2.getCost() + h2);
    }
  }

  /**
   * Recalcula el camino hacia la salida del laberinto desde la posición actual.
   */
  private void calculatePath () {
    Path solution = null;

    ArrayList<Path> closed = new ArrayList <Path>();
    PriorityQueue<Path> open = new PriorityQueue<Path>(10, new HeuristicPathComparator());

    // Inicialmente la lista abierta contiene una trayectoria formada por sólo
    // el nodo de inicio
    open.add(new Path(m_pos));

    // Repetimos mientras hayan posibles trayectorias por recorrer
    while (!open.isEmpty()) {
      // Sacamos la trayectoria de la lista abierta
      Path path = open.poll();

      // Si la trayectoria finaliza en la salida, se trata de la trayectoria
      // óptima: Salimos del bucle
      if (path.getEndPoint().equals(m_exit)) {
        solution = path;
        break;
      }

      // Insertamos la trayectoria en la lista cerrada
      insertPath(path, closed);

      // Expandimos la trayectoria actual para obtener los posibles pasos que
      // se pueden dar desde la posición actual
      Point pos = path.getEndPoint();
      int n_steps = path.getLength();
      for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
        Direction dir = Direction.fromIndex(i);

        // Para añadir el paso a la trayectoria tiene que ser posible llevarlo
        // a cabo en el entorno y además no puede ser el paso opuesto al que
        // dio justo antes en la trayectoria (sabemos que así no se va a generar
        // una mejor solución, porque repite posiciones)
        if (m_env.movementAllowed(pos, dir)) {
          Point n_pos = dir.movePoint(pos);
          if (n_steps < 2 || !path.getPoint(n_steps - 2).equals(n_pos)) {
            Path expanded_path = path.addStep(dir, STEP_COST);
            Path discarded = insertPath(expanded_path, open);
            if (discarded != null && discarded != expanded_path)
              insertPath(discarded, closed);
          }
        }
      }
    }

    if (solution == null)
      return;

    ArrayList<Point> path = solution.getPath();
    m_directions = new ArrayList<Direction>(path.size()-1);
    for (int i = 1; i < path.size(); i++)
      m_directions.add(Direction.fromPoints(path.get(i-1), path.get(i)));

    m_direction_index = 0;
  }

  /**
   * Buscamos en la lista una trayectoria que acabe en el mismo punto que la que
   * está siendo procesada. Si la encuentra, dependiendo de los costes de ambas
   * trayectorias sólo se deja en la lista la de menor coste. Si no la
   * encuentra, simplemente añade la trayectoria a la lista.
   * @param path Trayectoria a añadir a la lista.
   * @param list Lista de trayectorias a la que se quiere añadir la trayectoria.
   * @return La trayectoria eliminada de la lista, la trayectoria de entrada si
   *         no fue insertada o null, dependiendo del caso.
   */
  private Path insertPath (Path path, Collection<Path> list) {
    Path discarded = null;
    boolean add_path = true;

    Iterator<Path> iter = list.iterator();
    while (iter.hasNext()) {
      Path p = iter.next();
      if (p.endsInTheSamePoint(path)) {
        HeuristicPathComparator comp = new HeuristicPathComparator();
        if (comp.compare(p, path) <= 0) {
          discarded = path;
          add_path = false;
        }
        else {
          discarded = p;
          iter.remove();
        }
        break;
      }
    }

    if (add_path)
      list.add(path);

    return discarded;
  }

  /**
   * Extrae la información del objeto a partir de una forma serializada del
   * mismo.
   * @param input Flujo de entrada con la información del objeto.
   * @throws ClassNotFoundException Si se trata de un objeto de otra clase.
   * @throws IOException Si no se puede leer el flujo de entrada.
   */
  private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
    input.defaultReadObject();
    m_pos = new Point();
  }
}
