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
 * @file RotationDirection.java
 * @date 13/12/2014
 */
package es.ull.mazesolver.util;

/**
 * Representa un sentido de rotación, que puede ser horario o antihorario.
 */
public enum Rotation {
  CLOCKWISE,
  COUNTER_CLOCKWISE;

  /**
   * @return La dirección contraria a la actual.
   */
  public Rotation getOpposite () {
    if (this == CLOCKWISE)
      return COUNTER_CLOCKWISE;
    else
      return CLOCKWISE;
  }

  /**
   * Alias equivalente a {@code Rotation.CLOCKWISE}.
   */
  public static Rotation CW = CLOCKWISE;

  /**
   * Alias equivalente a {@code Rotation.COUNTER_CLOCKWISE}.
   */
  public static Rotation CCW = COUNTER_CLOCKWISE;
}
