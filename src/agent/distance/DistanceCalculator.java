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
 * @file DistanceCalculator.java
 * @date 3/12/2014
 */
package agent.distance;

import java.awt.Point;
import java.io.Serializable;

/**
 * Interfaz que implementan las clases que pueden medir la distancia entre
 * 2 puntos en 2 dimensiones.
 */
public abstract class DistanceCalculator implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Mide la distancia entre 2 puntos.
   * @param p1 Punto 1.
   * @param p2 Punto 2.
   * @return Distancia entre los 2 puntos.
   */
  public abstract double distance (Point p1, Point p2);

  /**
   * Mide la distancia entre 2 puntos.
   * @param x1 Posición en el eje X del punto 1.
   * @param y1 Posición en el eje Y del punto 1.
   * @param x2 Posición en el eje X del punto 2.
   * @param y2 Posición en el eje Y del punto 2.
   * @return Distancia entre los 2 puntos.
   */
  public double distance (int x1, int y1, int x2, int y2) {
    return distance(new Point(x1, y1), new Point(x2, y2));
  }
}
