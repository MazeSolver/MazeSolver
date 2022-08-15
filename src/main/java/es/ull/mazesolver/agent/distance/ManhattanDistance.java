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
 * @file ManhattanDistance.java
 * @date 3/12/2014
 */
package es.ull.mazesolver.agent.distance;

import java.awt.Point;

/**
 * Métrica de distancia correspondiente a la distancia de Manhattan, rectilínea
 * o taxicab.
 */
public class ManhattanDistance extends DistanceCalculator {
    private static final long serialVersionUID = 6109509269853813165L;

    /*
     * (non-Javadoc)
     *
     * @see agent.distance.DistanceCalculator#distance(java.awt.Point,
     * java.awt.Point)
     */
    @Override
    public double distance(Point p1, Point p2) {
        return Math.abs(p2.x - p1.x) + Math.abs(p2.y - p1.y);
    }

    /*
     * (non-Javadoc)
     *
     * @see agent.distance.DistanceCalculator#getType()
     */
    @Override
    public DistanceType getType() {
        return DistanceType.MANHATTAN;
    }

    /*
     * (non-Javadoc)
     *
     * @see agent.distance.DistanceCalculator#clone()
     */
    @Override
    public Object clone() {
        return new ManhattanDistance();
    }
}
