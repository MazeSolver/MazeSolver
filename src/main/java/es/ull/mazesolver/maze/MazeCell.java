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
 * @file MazeCell.java
 * @date 21/10/2014
 */
package es.ull.mazesolver.maze;

import es.ull.mazesolver.util.Direction;

import java.io.Serializable;

/**
 * Clase que representa una celda del laberinto.
 */
public class MazeCell implements Serializable {
    private static final long serialVersionUID = 4328443829621010840L;

    private short m_cell;

    /**
     * Constructor por defecto. La celda creada está rodeada de muros.
     */
    public MazeCell() {
        m_cell |= Direction.UP.val;
        m_cell |= Direction.DOWN.val;
        m_cell |= Direction.RIGHT.val;
        m_cell |= Direction.LEFT.val;
    }

    /**
     * Cambia el estado de la dirección especificada. Si no había muro, ahora lo
     * hay y viceversa.
     *
     * @param dir Lado de la celda que se quiere modificar.
     */
    public void toggleWall(Direction dir) {
        if (hasWall(dir))
            unsetWall(dir);
        else
            setWall(dir);
    }

    /**
     * Pone un muro si no lo hay en la dirección especificada.
     *
     * @param dir Lado de la celda que se quiere modificar.
     */
    public void setWall(Direction dir) {
        m_cell |= dir.val;
    }

    /**
     * Quita el muro si lo hay en la dirección especificada.
     *
     * @param dir Lado de la celda que se quiere modificar.
     */
    public void unsetWall(Direction dir) {
        m_cell &= ~dir.val;
    }

    /**
     * Elimina todas las paredes de la celda.
     */
    public void removeWalls() {
        m_cell = 0;
    }

    /**
     * Indica si hay un muro en la dirección indicada.
     *
     * @param dir Lado de la celda que se quiere consultar.
     * @return Si hay una celda en esa dirección o no.
     */
    public boolean hasWall(Direction dir) {
        return (m_cell & dir.val) != 0;
    }

    /**
     * Enumeración de los diferentes estados que puede tener una celda de cara a
     * un agente cualquiera.
     */
    public static enum Vision {
        /**
         * Este estado significa que la celda está vacía y no hay una pared entre
         * las 2 celdas.
         */
        EMPTY,

        /**
         * Este estado significa que hay una pared entre la posición actual del
         * agente y la celda.
         */
        WALL,

        /**
         * Este estado significa que hay un agente sobre esa celda actualmente y no
         * hay nada interponiéndose en medio.
         */
        AGENT,

        /**
         * Este estado significa que la celda referenciada está fuera del laberinto
         * y además no hay una pared en medio.
         */
        OFFLIMITS;
    }

}
