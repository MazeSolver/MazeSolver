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
 * @file Pair.java
 * @date 29/10/2014
 */
package es.ull.mazesolver.util;

/**
 * Clase que representa a un par de valores.
 */
public class Pair <T1, T2> {
  public T1 first;
  public T2 second;

  public Pair (T1 f, T2 s) {
    first = f;
    second = s;
  }

  @Override
  public int hashCode () {
    return (first != null? first.hashCode() : 0) * (second != null? second.hashCode() : 0);
  }

  @Override
  public boolean equals (Object obj) {
    if (obj instanceof Pair) {
      Pair <?, ?> o = (Pair <?, ?>) obj;
      try {
        return first.equals(o.first) && second.equals(o.second);
      }
      catch (NullPointerException e) {
        if (first == null && o.first == null) {
          if (second == null && o.second == null)
            return true;
          else if (second != null)
            return second.equals(o.second);
        }
        else if (first != null && first.equals(o.first)) {
          if (second == null && o.second == null)
            return true;
          else if (second != null)
            return second.equals(o.second);
        }
        return false;
      }
    }
    return false;
  }
}
