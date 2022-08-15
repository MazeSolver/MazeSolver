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
 * @file Wilson.java
 * @date Jan 6, 2015
 */
package es.ull.mazesolver.maze.algorithm;

import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.util.Direction;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Implementación del algoritmo Wilson para la generación aleatoria de
 * laberintos perfectos.
 */
public class Wilson extends MazeCreationAlgorithm {
    private int m_remaining;
    private ArrayList<ArrayList<Boolean>> m_included_cells;

    /**
     * Constructor. Crea una nueva instancia de la clase.
     *
     * @param rows    Número de filas del laberinto.
     * @param columns Número de columnas del laberinto.
     */
    public Wilson(int rows, int columns) {
        super(rows, columns);

        // Creamos una matriz de visitados para saber en cada momento cuáles son
        // las celdas que no se han visitado todavía.
        m_included_cells = new ArrayList<ArrayList<Boolean>>(rows);

        for (int y = 0; y < rows; y++) {
            m_included_cells.add(new ArrayList<Boolean>(columns));
            for (int x = 0; x < columns; x++)
                m_included_cells.get(y).add(false);
        }
        m_remaining = columns * rows - 1;
        int x = (int) (Math.random() * m_columns);
        int y = (int) (Math.random() * m_rows);
        m_included_cells.get(y).set(x, true);
    }

    /*
     * (non-Javadoc)
     *
     * @see es.ull.mazesolver.maze.MazeCreationAlgorithm#runCreationAlgorithm()
     */
    @Override
    protected void runCreationAlgorithm() {
        while (m_remaining > 0) {
            ArrayList<Short[]> path = walk();
            for (int i = 0; i < path.size(); i++) {
                Short[] aux = path.get(i);
                Point p = new Point(aux[0], aux[1]);
                Direction dir = Direction.fromValue(aux[2]);
                m_included_cells.get(p.y).set(p.x, true);
                openPassage(p.y, p.x, dir);
                m_remaining -= 1;
            }
        }
    }

    /**
     * Método que calcula un camino a seguir.
     *
     * @return Lista de puntos con su respectiva dirección que hemos de seguir
     * para llegar a una zona visitada.
     */
    private ArrayList<Short[]> walk() {
        Point p_start = getRandomStarter();
        ArrayList<ArrayList<Direction>> directionsTaken;
        directionsTaken = new ArrayList<ArrayList<Direction>>(m_rows);

        for (int y = 0; y < m_rows; y++) {
            directionsTaken.add(new ArrayList<Direction>(m_columns));
            for (int x = 0; x < m_columns; x++)
                directionsTaken.get(y).add(Direction.NONE);
        }
        Point p = p_start;
        do {
            Direction dir = getRandomDirection(p.y, p.x);
            directionsTaken.get(p.y).set(p.x, dir);
            p = dir.movePoint(p);
        }
        while (!m_included_cells.get(p.y).get(p.x));

        p = p_start;
        ArrayList<Short[]> path = new ArrayList<Short[]>();
        do {
            Direction dir = directionsTaken.get(p.y).get(p.x);
            Short[] pos = {(short) p.x, (short) p.y, dir.val};
            path.add(pos);
            p = dir.movePoint(p);
        }
        while (!m_included_cells.get(p.y).get(p.x));

        return path;
    }

    /**
     * Obtiene una dirección aleatoria desde la posición indicada que la conecte
     * con una posición no explorada dentro del laberinto que se está creando.
     *
     * @param y Posición en el eje Y desde la que se quiere partir.
     * @param x Posición en el eje X desde la que se quiere partir.
     * @return Una direccion aleatoria dentro de las posibles a las que ir
     * en la casilla dada por las posiciones i y j.
     */
    private Direction getRandomDirection(int y, int x) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        Point actual = new Point(x, y);

        // Comprobamos qué posiciones de alrededor son válidas y no se han visitado
        // Suponemos que la posición proporcionada es válida para empezar
        for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
            Direction dir = Direction.fromIndex(i);
            Point next = dir.movePoint(actual);

            if (next.y >= 0 && next.y < m_rows && next.x >= 0 && next.x < m_columns)
                directions.add(dir);
        }

        if (directions.isEmpty())
            return Direction.NONE;
        else
            return directions.get((int) (Math.random() * directions.size()));
    }

    /**
     * Obtiene un punto inicial de manera aleatoria desde el cual se puede llamar
     * al método {@link Wilson#walk}.
     *
     * @return Punto inicial aleatorio para metodo walk
     */
    private Point getRandomStarter() {
        ArrayList<Point> freePoints = new ArrayList<Point>();
        for (int y = 0; y < m_rows; y++)
            for (int x = 0; x < m_columns; x++)
                if (!m_included_cells.get(y).get(x))
                    freePoints.add(new Point(x, y));
        return freePoints.get((int) (Math.random() * freePoints.size()));
    }

}
