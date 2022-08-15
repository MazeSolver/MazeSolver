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
 * @file AldousBroder.java
 * @date 10 Nov 2014
 */
package es.ull.mazesolver.maze.algorithm;

import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.util.Direction;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Implementación del algoritmo Aldous-Broder para la generación aleatoria de
 * laberintos perfectos.
 */
public class AldousBroder extends MazeCreationAlgorithm {
    private short cellVisitedCount = 0;
    private ArrayList<ArrayList<Boolean>> m_included_cells;

    /**
     * Constructor. Crea una nueva instancia de la clase.
     *
     * @param rows    Número de filas del laberinto.
     * @param columns Número de columnas del laberinto.
     */
    public AldousBroder(int rows, int columns) {
        super(rows, columns);

        // Creamos una matriz de visitados para saber en cada momento cuáles son
        // las celdas que no se han visitado todavía.
        m_included_cells = new ArrayList<ArrayList<Boolean>>(rows);

        for (int y = 0; y < rows; y++) {
            m_included_cells.add(new ArrayList<Boolean>(columns));
            for (int x = 0; x < columns; x++)
                m_included_cells.get(y).add(false);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see maze.MazeCreationAlgorithm#runCreationAlgorithm()
     */
    @Override
    public void runCreationAlgorithm() {
        int x = (int) (Math.random() * m_columns);
        int y = (int) (Math.random() * m_rows);
        Point p = new Point(x, y);

        while (cellVisitedCount < (m_columns * m_rows)) {
            Direction dir = getRandomDirection(p.y, p.x);
            p = dir.movePoint(p);
            if (!m_included_cells.get(p.y).get(p.x)) {
                openPassage(p.y, p.x, dir.getOpposite());
                m_included_cells.get(p.y).set(p.x, true);
                cellVisitedCount++;
            }
        }
    }

    /**
     * Obtiene una dirección aleatoria a partir de la posición indicada que no
     * se sale del tamaño del laberinto.
     *
     * @param y Posición en el eje Y desde la que se quiere partir.
     * @param x Posición en el eje X desde la que se quiere partir.
     * @return Dirección aleatoria dentro del rango.
     */
    private Direction getRandomDirection(int y, int x) {
        Point p = new Point(x, y);
        Point next_pos;
        Direction dir;

        do {
            dir = Direction.random();
            next_pos = dir.movePoint(p);
        }
        while (next_pos.y < 0 || next_pos.y >= m_rows || next_pos.x < 0 || next_pos.x >= m_columns);

        return dir;
    }
}
