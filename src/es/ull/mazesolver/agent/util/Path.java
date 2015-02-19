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
 * @file Path.java
 * @date 4/12/2014
 */
package es.ull.mazesolver.agent.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import es.ull.mazesolver.util.Direction;

/**
 * Representa una trayectoria formada por varios puntos correspondientes a
 * coordenadas en un laberinto.
 */
public class Path {
  private Stack <Point> m_path;
  private double m_cost;

  private Path () {
  }

  /**
   * Crea una nueva trayectoria.
   *
   * @param initial
   *          Punto inicial de la trayectoria.
   */
  public Path (Point initial) {
    m_path = new Stack <Point>();
    m_path.push(new Point(initial));
    m_cost = 0.0;
  }

  /**
   * @return La lista de pasos.
   */
  public ArrayList <Point> getPath () {
    ArrayList <Point> result = new ArrayList <Point>();
    result.addAll(m_path);
    return result;
  }

  /**
   * @return El punto inicial de la trayectoria.
   */
  public Point getStartPoint () {
    return m_path.firstElement();
  }

  /**
   * @return El punto final de la trayectoria.
   */
  public Point getEndPoint () {
    return m_path.peek();
  }

  /**
   * Accede a una posición específica dentro de la trayectoria.
   *
   * @param index
   *          Índice del punto.
   * @return El punto en esa posición de la trayectoria.
   */
  public Point getPoint (int index) {
    return m_path.get(index);
  }

  /**
   * @return El coste de la trayectoria.
   */
  public double getCost () {
    return m_cost;
  }

  /**
   * @return El número de pasos de los que se compone la trayectoria.
   */
  public int getLength () {
    return m_path.size();
  }

  /**
   * Añade un nuevo paso a la trayectoria sin modificar la clase original.
   *
   * @param dir
   *          Dirección hacia la que realizar el paso.
   * @param cost
   *          Coste de llevar a cabo el paso.
   * @return Nueva trayectoria con el paso dado.
   */
  @SuppressWarnings ("unchecked")
  public Path addStep (Direction dir, double cost) {
    Path result = new Path();
    result.m_path = (Stack <Point>) m_path.clone();
    result.m_path.push(dir.movePoint(m_path.peek()));
    result.m_cost = m_cost + cost;
    return result;
  }

  /**
   * Comprueba si las dos trayectorias acaban en el mismo punto.
   *
   * @param other
   *          Trayectoria con la que realizar la comparación.
   * @return Si acaban en el mismo punto o no.
   */
  public boolean endsInTheSamePoint (Path other) {
    return m_path.peek().equals(other.m_path.peek());
  }
}
