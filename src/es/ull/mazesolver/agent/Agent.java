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
 * @file Agent.java
 * @date 21/10/2014
 */
package es.ull.mazesolver.agent;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import es.ull.mazesolver.gui.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.maze.MazeCell;
import es.ull.mazesolver.util.Direction;

/**
 * Clase que representa un agente abstracto que se encuentra en algún laberinto.
 * Sus subclases implementan los distintos algoritmos para resolver laberintos.
 *
 * Cada agente debe implementar los métodos de serialización para poder guardarse
 * y cargarse correctamente, en caso de que requiriesen configuración adicional
 * a la genérica.
 */
public abstract class Agent implements Cloneable, Serializable {
  private static final long serialVersionUID = 1L;
  private static int s_agent_count = 0;

  private transient int m_agent_id;

  protected transient Environment m_env;
  protected transient Point m_pos;

  /**
   * @param maze
   */
  protected Agent (Environment env) {
    m_agent_id = s_agent_count++;
    m_pos = new Point();
    setEnvironment(env);
  }

  /**
   * @param pos
   *          Nueva posición del agente. Este método se puede sobrecargar en las
   *          clases derivadas para mantener coherente la memoria de la que
   *          disponga el mismo.
   */
  public void setPosition (Point pos) {
    m_pos.x = pos.x;
    m_pos.y = pos.y;
  }

  /**
   * @return Posición en el eje X del agente.
   */
  public int getX () {
    return m_pos.x;
  }

  /**
   * @return Posición en el eje Y de agente.
   */
  public int getY () {
    return m_pos.y;
  }

  /**
   * @return Posición del agente.
   */
  public Point getPos () {
    return new Point(m_pos);
  }

  /**
   * Este método debería sobrecargarse en las clases derivadas que contengan
   * información acerca del camino a seguir por el agente (un plan) de forma que
   * éste siga siendo coherente tras el cambio de laberinto.
   * @param env Entorno donde colocar el agente.
   */
  public void setEnvironment (Environment env) {
    if (env != null)
      m_env = env;
    else
      throw new IllegalArgumentException("El laberinto debe ser válido");
  }

  /**
   * @return Entorno en el que se encuentra el agente.
   */
  public Environment getEnvironment () {
    return m_env;
  }

  /**
   * @param dir Dirección hacia la que mirar.
   * @return Lo que vería el agente si mira en la dirección especificada.
   */
  public MazeCell.Vision look (Direction dir) {
    return m_env.look(m_pos, dir);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals (Object obj) {
    return this == obj;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode () {
    return m_env.hashCode();
  }

  /**
   * Hace que el agente realice el movimiento especificado, sin comprobar que se
   * trata de un movimiento válido.
   *
   * La clase base sólo cambia la posición del agente, si se desea más
   * funcionalidad, se debe sobrecargar en las clases derivadas.
   * @param dir Dirección hacia la que mover el agente.
   */
  public void doMovement (Direction dir) {
    m_pos = dir.movePoint(m_pos);
  }

  /**
   * Pregunta al agente el nombre que le identifica.
   * @return Nombre identificador del agente. Incluye el nombre del algoritmo
   *         que implementa.
   */
  public String getName () {
    return getAlgorithmName() + " " + String.valueOf(m_agent_id);
  }

  /**
   * Carga la descripción serializada del agente de un fichero, lo crea y lo
   * devuelve.
   * @param filename Nombre del fichero de entrada.
   * @param env Entorno en el que cargar el agente.
   * @return El agente creado.
   * @throws IOException Si no es posible leer el fichero.
   */
  public static Agent loadFile (String filename, Environment env) throws IOException {
    try {
      Agent ag;
      FileInputStream file_in = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(file_in);
      ag = (Agent) in.readObject();
      in.close();
      file_in.close();

      ag.m_agent_id = s_agent_count++;
      ag.m_pos = new Point();
      ag.setEnvironment(env);

      return ag;
    }
    catch (ClassNotFoundException c) {
      throw new IOException(c);
    }
  }

  /**
   * Guarda la instancia del agente en un fichero utilizando su serialización.
   * @param filename Nombre del fichero de salida.
   * @throws IOException Si no es posible guardar el fichero.
   */
  public void saveFile (String filename) throws IOException {
    FileOutputStream file_out = new FileOutputStream(filename);
    ObjectOutputStream out = new ObjectOutputStream(file_out);
    out.writeObject(this);
    out.close();
    file_out.close();
  }

  /**
   * Pregunta al agente el nombre del algoritmo que implementa.
   * @return Nombre del algoritmo que implementa.
   */
  public abstract String getAlgorithmName ();

  /**
   * @return La dirección en la que el agente quiere realizar el siguiente
   *         movimiento.
   */
  public abstract Direction getNextMovement ();

  /**
   * Elimina la memoria que el agente tenga sobre el entorno. No elimina su
   * configuración, sino que lo deja en el estado inicial.
   */
  public abstract void resetMemory ();

  /**
   * @return Un panel de configuración para el agente.
   */
  public abstract AgentConfigurationPanel getConfigurationPanel ();

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public abstract Object clone ();

}
