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
 * @file Kruskal.java
 * @date 3 Nov 2014
 */
package es.ull.mazesolver.maze.algorithm;

import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.util.Direction;
import es.ull.mazesolver.util.Pair;

import java.util.ArrayList;

/**
 * Implementación del algoritmo de Kruskal para la generación aleatoria de
 * laberintos.
 */
public class Kruskal extends MazeCreationAlgorithm {
    private ArrayList<Integer> disjoint_set;
    private ArrayList<short[]> walls;

    /**
     * Constructor. Crea una nueva instancia de la clase.
     *
     * @param rows    Número de filas del laberinto.
     * @param columns Número de columnas del laberinto.
     */
    public Kruskal(int rows, int columns) {
        super(rows, columns);
        walls = new ArrayList<short[]>();

        // Creamos una matriz de visitados para saber en cada momento cuáles son
        // las celdas que no se han visitado todavía.
        disjoint_set = new ArrayList<Integer>();

        for (int i = 0; i < m_rows * m_columns; i++)
            disjoint_set.add(i);

        addAll();
    }

    /*
     * (non-Javadoc)
     *
     * @see maze.MazeCreationAlgorithm#runCreationAlgorithm()
     */
    @Override
    public void runCreationAlgorithm() {
        int nextWall, y, x;
        while (!walls.isEmpty()) {
            // Seleccionamos una celda y una direccion de dentro de las posibles que
            // no hemos escogido aun.
            nextWall = (int) Math.round(0 + (Math.random() * (walls.size() - 1)));
            y = walls.get(nextWall)[0];
            x = walls.get(nextWall)[1];
            Direction dir = Direction.fromValue(walls.get(nextWall)[2]);
            Pair<Integer, Integer> desp = dir.decompose();

            // Si la celda vecina a la posicion i,j +dir pertenece a otro conjunto
            // entonces, la marcamos del mismo conjunto (y a cada elemento del mismo)
            // y abrimos el pasillo por ahi.
            if (value(y, x) != value(y + desp.second, x + desp.first)) {
                openPassage(y, x, dir);
                union(value(y, x), value(y + desp.second, x + desp.first));
            }
            walls.remove(nextWall);
        }
    }

    /**
     * Añade todos los muros a la lista de muros a elegir aleatoriamente.
     */
    private void addAll() {
        for (int y = 0; y < m_rows; y++)
            for (int x = 0; x < m_columns; x++)
                for (short k = 1; k < Direction.MAX_DIRECTIONS; k++) {
                    Direction dir = Direction.fromIndex(k);
                    Pair<Integer, Integer> desp = dir.decompose();
                    if ((y + desp.second >= 0) && (x + desp.first >= 0) && (y + desp.second < m_rows)
                            && (x + desp.first < m_columns)) {
                        short[] aux = {(short) y, (short) x, dir.val};
                        walls.add(aux);
                    }
                }
    }

    /**
     * Obtiene el índice que representa la posición (x, y).
     *
     * @param y Posición en el eje Y.
     * @param x Posición en el eje X.
     * @return La posición del vector dada por el punto (x, y).
     */
    private int pos(int y, int x) {
        // FIXME Esto está invertido. La fórmula real sería (y * m_columns) + x
        // No lo cambio porque no sé si hay código que depende de este
        // comportamiento erróneo.
        return (x * m_columns) + y;
    }

    /**
     * Une dos conjuntos disjuntos, de la forma siguiente: Todo conjunto que
     * tenga como valor representativo value_from, lo mueve al conjunto de valor
     * value_to.
     *
     * @param value_from Valor representativo del conjunto que se va a ser unido al otro
     *                   conjunto.
     * @param value_to   Valor representativo del conjunto al que se va a unir el otro
     *                   conjunto.
     */
    private void union(int value_from, int value_to) {
        for (int k = 0; k < disjoint_set.size(); k++)
            if (disjoint_set.get(k) == value_from)
                disjoint_set.set(k, value_to);
    }

    /**
     * Obtiene el valor del conjunto del elemento (x, y).
     *
     * @param y Posición en el eje Y.
     * @param x Posición en el eje X.
     * @return Valor del conjunto del elemento (x, y).
     */
    private int value(int y, int x) {
        return disjoint_set.get(pos(y, x));
    }

}
