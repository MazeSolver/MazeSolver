/**
 * @file Environment.java
 * @date 25/10/2014
 */
package gui;

import java.util.ArrayList;

import javax.swing.JInternalFrame;

import maze.Maze;
import agent.Agent;

/**
 * Una instancia de esta clase representa un entorno de ejecución, formado por
 * un laberinto y por un conjunto de agentes.
 */
public abstract class Environment extends JInternalFrame {
  private static final long serialVersionUID = 1L;

  private Maze m_maze;

  /**
   * Constructor para las clases de tipo entorno.
   * @param maze Laberinto en el que se basa el entorno. Puede ser compartido
   * entre varios entornos.
   */
  public Environment (Maze maze) {
    m_maze = maze;
  }

  /**
   * @return Laberinto base del entorno.
   */
  public Maze getMaze () {
    return m_maze;
  }

  /**
   * Cambia el laberinto del entorno. La memoria almacenada del/los agentes
   * sería inválida en caso de que contuviera información sobre la ruta a llevar
   * a cabo en el entorno, pero no cuando la memoria fuera un conjunto de reglas
   * o una tabla de percepción-acción.
   * @param maze Laberinto en el que se basa el entorno.
   */
  public void setMaze (Maze maze) {
    m_maze = maze;
  }

  /**
   * @param ag Agente que se quiere añadir al entorno.
   * @return Una referencia al propio entorno. Si se trata de un entorno simple
   * y se añade un agente, este método devolverá un entorno de agentes múltiples
   * con la misma información que el anterior. En otro caso devuelve una
   * referencia al propio entorno. Actualizando las variables que referencian a
   * este objeto con el valor devuelto hacemos que se pueda tratar de manera
   * uniforme todos los entornos.
   */
  public abstract Environment addAgent (Agent ag);

  /**
   * @return Agente seleccionado actualmente en el entorno o null si no hay
   * ninguno seleccionado.
   */
  public abstract Agent getSelectedAgent ();

  /**
   * @return Lista de agentes dentro del entorno.
   */
  public abstract ArrayList <Agent> getAgents ();

  /**
   * Ejecuta un paso de la simulación de ejecución de los agentes en el entorno.
   * @return true si algún agente ha salido del laberinto y false en otro caso.
   */
  public abstract boolean runStep ();

}
