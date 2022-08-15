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
 * @file MazeCreationAlgorithm.java
 * @date 21/10/2014
 */
package es.ull.mazesolver.maze;

import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.util.Direction;
import es.ull.mazesolver.util.Pair;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Interfaz que encapsula un algoritmo de creación de laberintos.
 */
public abstract class MazeCreationAlgorithm {
    /**
     * Número mínimo de filas que puede tener un laberinto (altura mímia).
     */
    public static final int MIN_ROWS = 5;

    /**
     * Número mínimo de columnas que puede tener un laberinto (anchura mínima).
     */
    public static final int MIN_COLUMNS = 5;

    /**
     * Número de filas del laberinto que se va a crear.
     */
    protected int m_rows;

    /**
     * Numero de columnas del laberinto que se va a crear.
     */
    protected int m_columns;

    /**
     * Matriz de celdas del laberinto que se va a crear o se esá creando.
     */
    protected ArrayList<ArrayList<MazeCell>> m_maze;

    /**
     * Punto de salida del laberinto.
     */
    protected Point m_maze_exit;

    private int m_cycles, m_walls;

    /**
     * Constructor. Crea una nueva instancia de la clase.
     *
     * @param rows    Número de filas del laberinto.
     * @param columns Número de columnas del laberinto.
     */
    public MazeCreationAlgorithm(int rows, int columns) {
        if (rows < MIN_ROWS || columns < MIN_COLUMNS)
            throw new IllegalArgumentException(
                    MainWindow.getTranslations().exception().tooSmallRowsCols());

        m_rows = rows;
        m_columns = columns;
        m_maze = initializeMaze();
    }

    /**
     * Devuelve la posición de la salida del laberinto.
     *
     * @return La posición de la salida del laberinto.
     */
    public Point getExit() {
        return new Point(m_maze_exit);
    }

    /**
     * Crea el laberinto, coloca la salida y añade los ciclos y paredes indicados.
     *
     * @return La matriz que contiene las celdas del laberinto.
     */
    public ArrayList<ArrayList<MazeCell>> createMaze() {
        runCreationAlgorithm();
        createExit();
        addRandomCycles(m_cycles);
        addRandomWalls(m_walls);

        return m_maze;
    }

    /**
     * Establece el número de ciclos que se quiere que genere el algoritmo.
     *
     * @param n_cycles Número de ciclos.
     */
    public void setCycles(int n_cycles) {
        m_cycles = n_cycles;
    }

    /**
     * Establece el número de componentes separadas que se quiere tener al generar
     * el laberinto. Si se especifica 1, no se modifica el laberinto perfecto.
     *
     * @param n_components Número de componentes.
     */
    public void setComponents(int n_components) {
        m_walls = n_components - 1;
    }

    /**
     * Ejecuta el algoritmo de creación del laberinto, dejando el resultado en la
     * variable miembro {@link MazeCreationAlgorithm#m_maze}.
     * <p>
     * Cuando se llama a este método, la variable está inicializada con un mapa en
     * el que todas las celdas están rodeadas de paredes.
     */
    protected abstract void runCreationAlgorithm();

    /**
     * Este método crea un laberinto vacío a partir del número de filas y columnas
     * especificado en el constructor. Hace más sencillo a las subclases
     * implementar el método {@link MazeCreationAlgorithm#createMaze}.
     *
     * @return Un laberinto vacío del tamaño especificado.
     */
    protected ArrayList<ArrayList<MazeCell>> initializeMaze() {
        ArrayList<ArrayList<MazeCell>> maze = new ArrayList<ArrayList<MazeCell>>(m_rows);
        for (int y = 0; y < m_rows; y++) {
            maze.add(new ArrayList<MazeCell>(m_columns));
            for (int x = 0; x < m_columns; x++)
                maze.get(y).add(new MazeCell());
        }

        return maze;
    }

    /**
     * Abre un pasillo entre la celda (x, y) y su adyacente en la dirección
     * indicada.
     *
     * @param y   Posición en el eje Y (FILA).
     * @param x   Posición en el eje X (COLUMNA).
     * @param dir Dirección hacia la que abrir el camino.
     */
    protected void openPassage(int y, int x, final Direction dir) {
        Pair<Integer, Integer> desp = dir.decompose();
        m_maze.get(y).get(x).unsetWall(dir);
        m_maze.get(y + desp.second).get(x + desp.first).unsetWall(dir.getOpposite());
    }

    /**
     * Abre una salida en una casilla aleatoria por los bordes del laberinto.
     */
    private void createExit() {
        // Decidimos en qué borde vamos a crear la salida
        Direction dir = Direction.random();

        // Posición en la que se abrirá el hueco: Puede ser tanto una coordenada en
        // X como en Y
        int pos;
        if (dir.isVertical())
            pos = (int) (Math.random() * m_columns);
        else
            pos = (int) (Math.random() * m_rows);

        // Cogemos la celda de salida y abrimos el hueco
        m_maze_exit = new Point();
        switch (dir) {
            case UP:
                m_maze_exit.move(pos, 0);
                break;
            case DOWN:
                m_maze_exit.move(pos, m_rows - 1);
                break;
            case LEFT:
                m_maze_exit.move(0, pos);
                break;
            case RIGHT:
                m_maze_exit.move(m_columns - 1, pos);
                break;
            default:
                break;
        }

        m_maze.get(m_maze_exit.y).get(m_maze_exit.x).unsetWall(dir);
        m_maze_exit.setLocation(dir.movePoint(m_maze_exit));
    }

    /**
     * Elimina paredes de un laberinto perfecto ya creado para crear ciclos y
     * hacer que deje de serlo.
     *
     * @param n Número de paredes a quitar.
     */
    private void addRandomCycles(int n) {
        if (n > Maze.perfectMazeWalls(m_rows, m_columns))
            throw new IllegalArgumentException(
                    MainWindow.getTranslations().exception().tooManyWalls());

        int k = 0;
        Direction dir;
        while (k < n) {
            int x = (int) (Math.random() * m_columns);
            int y = (int) (Math.random() * m_rows);
            Point p = new Point(x, y);
            ArrayList<Direction> directions = new ArrayList<Direction>();

            for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
                dir = Direction.fromIndex(i);
                Point p2 = dir.movePoint(p);
                if (p2.y >= 0 && p2.y < m_rows && p2.x >= 0 && p2.x < m_columns
                        && m_maze.get(p.y).get(p.x).hasWall(dir))
                    directions.add(dir);
            }

            if (!directions.isEmpty()) {
                dir = directions.get((int) (Math.random() * directions.size()));
                openPassage(p.y, p.x, dir);
                k++;
            }
        }
    }

    /**
     * Añade paredes al laberinto perfecto ya creado para que esté compuesto por
     * distintas componentes inaccesibles entre sí y deje de ser perfecto.
     *
     * @param n Número de paredes para añadir.
     */
    private void addRandomWalls(int n) {
        if (n > Maze.perfectMazeEdges(m_rows, m_columns))
            throw new IllegalArgumentException(
                    MainWindow.getTranslations().exception().tooManyWalls());

        int k = 0;
        Direction dir;
        while (k < n) {
            int x = (int) (Math.random() * m_columns);
            int y = (int) (Math.random() * m_rows);
            Point p = new Point(x, y);
            ArrayList<Direction> directions = new ArrayList<Direction>();

            for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
                dir = Direction.fromIndex(i);
                Point p2 = dir.movePoint(p);
                if (p2.y >= 0 && p2.y < m_rows && p2.x >= 0 && p2.x < m_columns
                        && !m_maze.get(p.y).get(p.x).hasWall(dir))
                    directions.add(dir);
            }

            if (!directions.isEmpty()) {
                dir = directions.get((int) (Math.random() * directions.size()));
                m_maze.get(p.y).get(p.x).setWall(dir);
                p = dir.movePoint(p);
                m_maze.get(p.y).get(p.x).setWall(dir.getOpposite());
                k++;
            }
        }
    }

}
