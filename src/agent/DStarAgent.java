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
 * @file DStarAgent.java
 * @date 10/12/2014
 */
package agent;

import gui.AgentConfigurationPanel;
import gui.environment.Environment;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.swing.JPanel;

import maze.Direction;
import maze.Maze;
import maze.MazeCell;
import maze.algorithm.EmptyMaze;
import agent.distance.DistanceCalculator;

/**
 * Agente que implementa el algoritmo D* para calcular la ruta más corta hasta
 * la salida teniendo tan sólo conocimiento local del entorno.
 * @see <a href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.15.3683">
 *   Optimal and Efficient Path Planning for Unknown and Dynamic Environments
 * </a>
 */
public class DStarAgent extends HeuristicAgent {
  private static final long serialVersionUID = 1342168437798267323L;

  /**
   * No se trata del laberinto en el que el agente se mueve, sino la
   * representación de lo que el agente conoce sobre el laberinto. Todas
   * aquellas zonas que el agente no ha visitado supone que no contienen
   * paredes.
   */
  private transient Maze m_maze;
  private transient Point m_exit;
  private transient ArrayList<ArrayList<State>> m_state_maze;

  private transient PriorityQueue<State> m_open;
  private transient double k_old;

  /**
   * @param env Entorno en el que se va a colocar al agente.
   */
  public DStarAgent (Environment env) {
    super(env);
  }

  /* (non-Javadoc)
   * @see agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "D*";
  }

  /* (non-Javadoc)
   * @see agent.Agent#setEnvironment(gui.environment.Environment)
   */
  @Override
  public void setEnvironment (Environment env) {
    super.setEnvironment(env);
    resetMemory();

    Maze real_maze = env.getMaze();
    m_maze = new Maze(new EmptyMaze(real_maze.getHeight(), real_maze.getWidth()));

    // La salida la colocaremos dentro del laberinto para que el agente pueda
    // utilizarla como un estado más, luego en el método getNextMovement() se
    // encarga de moverse al exterior si está en el punto al lado de la salida
    m_exit = real_maze.getExit();
    if (m_exit.x < 0) m_exit.x++;
    else if (m_exit.x == m_maze.getWidth()) m_exit.x--;
    else if (m_exit.y < 0) m_exit.y++;
    else /*m_exit.y == m_maze.getHeight()*/ m_exit.y--;
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // Si estamos al lado de la salida evitamos cálculos y salimos directamente
    for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);
      if (m_env.look(m_pos, dir) == MazeCell.Vision.OFFLIMITS)
        return dir;
    }

    if (m_state_maze == null)
      calculatePath();

    // Obtenemos las celdas real y estimada para compararlas
    MazeCell known_cell = m_maze.get(m_pos.y, m_pos.x);
    MazeCell real_cell = m_env.getMaze().get(m_pos.y, m_pos.x);

    // Comprobamos en todas las direcciones que las paredes están colocadas en
    // los mismos sitios
    ArrayList<Direction> changed = new ArrayList<Direction>();
    for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);

      // En las direcciones en las que encontremos diferencias hacemos que la
      // representación interna del agente se actualice
      if (real_cell.hasWall(dir) != known_cell.hasWall(dir)) {
        known_cell.toggleWall(dir);

        Point new_point = dir.movePoint(m_pos);
        if (m_maze.containsPoint(new_point)) {
          m_maze.get(new_point.y, new_point.x).toggleWall(dir.getOpposite());
          changed.add(dir);
        }
      }
    }

    // Si la representación del laberinto se modifica, esto significa también
    // que las distancias desde la posición actual hacia alguna de sus vecinas
    // ha cambiado, así que hay que actualizar la ruta calculada por si ha
    // dejado de ser factible.
    if (!changed.isEmpty()) {
      State x = m_state_maze.get(m_pos.y).get(m_pos.x);

      for (Direction dir: changed) {
        Point new_pos = dir.movePoint(m_pos);
        State y = m_state_maze.get(new_pos.y).get(new_pos.x);
        modifyCost(y);
        calculatePartialPath(x);
      }
    }

    for (int i = 0; i < m_maze.getHeight(); i++) {
      for (int j = 0; j < m_maze.getWidth(); j++) {
        State s = m_state_maze.get(i).get(j);
        if (s.backpointer == null)
          System.out.print("·");
        else {
          switch (Direction.fromPoints(s.point, s.backpointer.point)) {
            case UP:
              System.out.print("\u2191");
              break;
            case DOWN:
              System.out.print("\u2193");
              break;
            case LEFT:
              System.out.print("\u2190");
              break;
            case RIGHT:
              System.out.print("\u2192");
              break;
            default:
              System.out.print("·");
              break;
          }
        }
      }
      System.out.println();
    }
    System.out.println();

    Point next_pos = m_state_maze.get(m_pos.y).get(m_pos.x).backpointer.point;
    return Direction.fromPoints(m_pos, next_pos);
  }

  /* (non-Javadoc)
   * @see agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    m_state_maze = null;
    m_open = new PriorityQueue<State>();
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
        root.add(distance);
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
    DStarAgent ag = new DStarAgent(m_env);
    ag.setDistanceCalculator(m_dist);
    return ag;
  }

  /**
   * Define los posibles valores con los que puede estar etiquetado un estado.
   */
  private static enum Tag {
    NEW, OPEN, CLOSED
  }

  /**
   * Representa un estado dentro del algoritmo D*.
   */
  private class State implements Comparable<State> {
    public Point point;
    public State backpointer;    // b(X)
    public Tag tag;              // t(X)
    public double path_cost;     // h(X)
    public double previous_cost; // p(X)
    public double key_value;     // k(X)

    /**
     * Crea un estado a partir de su posición en el laberinto. Se marca como
     * "nuevo" y se le asignan costes de infinito para todas sus propiedades.
     * @param pos
     */
    public State (Point pos) {
      point = (Point) pos.clone();
      tag = Tag.NEW;
      path_cost = previous_cost = key_value = Double.MAX_VALUE;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo (State s) {
      return Double.compare(key_value, s.key_value);
    }

    /**
     * Analiza los vecinos que tiene en todas las direcciones y devuelve todos
     * aquellos que existen. Es decir, las celdas adyacentes que están dentro
     * del laberinto.
     * @return Una lista con los vecinos del estado.
     */
    public ArrayList<State> getNeighbours () {
      ArrayList <State> neighbours = new ArrayList<State>();

      for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
        Direction dir = Direction.fromIndex(i);
        Point new_pos = dir.movePoint(point);
        if (m_maze.containsPoint(new_pos))
          neighbours.add(m_state_maze.get(new_pos.y).get(new_pos.x));
      }

      return neighbours;
    }
  }

  /**
   * Recalcula la ruta hasta la salida desde el punto actual. Utiliza el
   * conocimiento que se tiene actualmente sobre el laberinto para hacerlo.
   * Este método crea desde cero la estructura de estados, por lo que se debe
   * utilizar sólo una vez por entorno.
   */
  private void calculatePath () {
    // Creamos la matriz de estados, donde cada celda representa un nodo en el
    // grafo que manipula el algoritmo
    m_state_maze = new ArrayList<ArrayList<State>>(m_maze.getHeight());
    for (int i = 0; i < m_maze.getHeight(); i++) {
      m_state_maze.add(new ArrayList <State>(m_maze.getWidth()));
      for (int j = 0; j < m_maze.getWidth(); j++)
        m_state_maze.get(i).add(new State(new Point(j, i)));
    }

    State initial = m_state_maze.get(m_pos.y).get(m_pos.x);
    State goal = m_state_maze.get(m_exit.y).get(m_exit.x);

    goal.path_cost = 0.0;
    insert(goal);

    double value = 0.0;
    while (initial.tag != Tag.CLOSED && value != -1)
      value = processState();
  }

  /**
   * Una vez se encuentra un obstáculo nuevo y se llama a {@code modifyCost()},
   * se debe llamar a este método para que recalcule el camino hacia la salida
   * de una forma mucho más eficiente que utilizar {@code calculatePath()}.
   * Sólo recalcula aquella parte del camino previamente calculado que ha sido
   * invalidada tras la modificación.
   */
  private void calculatePartialPath (State x) {
    double kmin = getKmin();
    while (kmin <= x.path_cost)
      kmin = processState();
  }

  /**
   * Computa los costes del nodo actual hacia el destino cuando se ejecuta
   * repetidamente hasta que el nodo actual se etiqueta como "cerrado".
   * @return El valor de Kmin. Devolverá -1 si no hay ninguna solución factible.
   */
  private double processState () {
    State x = minState();
    if (x == null)
      return -1;

    k_old = getKmin();
    delete(x);

    ArrayList<State> neighbours = x.getNeighbours();

    // Reducimos el coste del nodo actual si se puede desde alguno de sus
    // vecinos, pero sólo si el camino actual a los vecinos es óptimo
    for (State y: neighbours) {
      if (y.tag == Tag.CLOSED && y.path_cost <= k_old &&
          x.path_cost > y.path_cost + distance(y, x)) {
        x.backpointer = y;
        x.path_cost = y.path_cost + distance(y, x);
      }
    }

    for (State y: neighbours) {
      // Propagación del coste a los estados no visitados
      if (y.tag == Tag.NEW) {
        y.backpointer = x;
        y.path_cost = x.path_cost + distance(x, y);
        y.previous_cost = y.path_cost;
        insert(y);
      }
      else {
        // Propagación de costes a través de los backpointers
        if (y.backpointer == x && y.path_cost != x.path_cost + distance(x, y)) {
          if (y.tag == Tag.OPEN) {
            if (y.path_cost < y.previous_cost)
              y.previous_cost = y.path_cost;

            y.path_cost = x.path_cost + distance(x, y);
          }
          else {
            y.path_cost = x.path_cost + distance(x, y);
            y.previous_cost = y.path_cost;
          }
          insert(y);
        }
        else {
          // Mejora los costes de los vecinos si puede
          if (y.backpointer != x && y.path_cost > x.path_cost + distance(x, y)) {
            if (x.previous_cost >= x.path_cost) {
              y.backpointer = x;
              y.path_cost = x.path_cost + distance(x, y);

              if (y.tag == Tag.CLOSED)
                y.previous_cost = y.path_cost;

              insert(y);
            }
            else {
              x.previous_cost = x.path_cost;
              insert(x);
            }
          }
          else {
            if (y.backpointer != x && x.path_cost > y.path_cost + distance(y, x) &&
                y.tag == Tag.CLOSED && y.path_cost > k_old) {
              y.previous_cost = y.path_cost;
              insert(y);
            }
          }
        }
      }
    }

    return getKmin();
  }

  /**
   * Notifica al algoritmo que se ha detectado una incoherencia entre las
   * distancias que se utilizaron al procesar el nodo X y lo que han medido los
   * sensores, de manera que lo introduce en la lista abierta para volver a ser
   * tratado.
   * @param x Estado que ha detectado incoherencia entre su representación del
   * entorno y lo detectado por sus sensores.
   * @return El valor de Kmin.
   */
  private double modifyCost (State x) {
    if (x.tag == Tag.CLOSED) {
      x.previous_cost = x.path_cost;
      insert(x);
    }
    return getKmin();
  }

  /**
   * @return El estado de la lista abierta con menor valor de k.
   */
  private State minState () {
    return m_open.isEmpty()? null : m_open.peek();
  }

  /**
   * @return El valor de k más pequeño que hay en la lista abierta.
   */
  private double getKmin () {
    State min = minState();
    return min != null? min.key_value : -1.0;
  }

  /**
   * Se elimina el estado indicado de la lista abierta y se modifica su etiqueta
   * por "cerrado".
   * @param s Estado que eliminar.
   */
  private void delete (State s) {
    m_open.remove(s);
    s.tag = Tag.CLOSED;
  }

  /**
   * Se inserta el estado en la lista abierta, modificando su etiqueta y
   * calculando el valor de k que tiene asociado a partir de h y p.
   * @param s Estado que insertar.
   */
  private void insert (State s) {
    // Reposicionamiento del elemento si ya estaba, en lugar de inserción
    if (s.tag == Tag.OPEN)
      m_open.remove(s);

    s.key_value = Math.min(s.path_cost, s.previous_cost);
    s.tag = Tag.OPEN;
    m_open.add(s);
  }

  /**
   * Calcula la distancia entre dos estados vecinos teniendo en cuenta que si
   * hay una pared que los separa, la distancia es infinita.
   * @param x Estado X.
   * @param y Estado Y.
   * @return El valor de la distancia entre ambos estados.
   */
  private double distance (State x, State y) {
    Point pos = x.point;
    if (m_maze.get(pos.y, pos.x).hasWall(Direction.fromPoints(pos, y.point)))
      return Double.MAX_VALUE;
    else
      return m_dist.distance(pos, y.point);
  }
}
