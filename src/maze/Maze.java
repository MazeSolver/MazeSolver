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
 * @file Maze.java
 * @date 21/10/2014
 */
package maze;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Clase que representa un laberinto.
 */
public class Maze {
  private ArrayList <ArrayList <MazeCell>> m_maze;
  private Point m_exit;

  /**
   * @param alg Algoritmo de creación de laberintos ya inicializado
   */
  public Maze (MazeCreationAlgorithm alg) {
    if (alg != null) {
      m_maze = alg.createMaze();
      m_exit = alg.getExit();
    }
    else
      throw new IllegalArgumentException("El algoritmo de creación del laberinto es inválido");
  }

  /**
   * @param fileName Nombre del fichero del que cargar el laberinto.
   * @throws IOException
   *           Cuando no se encuentra el fichero, no se puede abrir para su
   *           lectura o no contiene un laberinto válido.
   */
  public Maze (String fileName) throws IOException {
    loadFile(fileName);
  }

  /**
   * @param row Fila.
   * @param column Columna.
   * @return Celda en la posición indicada.
   */
  public MazeCell get (int row, int column) {
    return m_maze.get(row).get(column);
  }

  /**
   * @param row Fila.
   * @param column Columna.
   * @param cell Celda que se quiere introducir.
   */
  public void set (int row, int column, MazeCell cell) {
    m_maze.get(row).set(column, cell);
  }

  /**
   * @return Anchura (en celdas) del laberinto.
   */
  public int getWidth () {
    return m_maze.get(0).size();
  }

  /**
   * @return Altura (en celdas) del laberinto.
   */
  public int getHeight () {
    return m_maze.size();
  }

  /**
   * @return La posición donde se encuentra la salida al laberinto.
   */
  public Point getExit () {
    return new Point(m_exit);
  }

  /**
   * Determina si el punto se encuentra dentro del laberinto o no.
   * @param p Punto a testear.
   * @return Si el punto está dentro del laberinto o no.
   */
  public boolean containsPoint (Point p) {
    return p.x >= 0 && p.y >= 0 && p.x < getWidth() && p.y < getHeight();
  }

  /**
   * @param fileName Nombre del fichero del que cargar el laberinto.
   * @throws IOException
   *           Cuando no se encuentra el fichero, no se puede abrir para su
   *           lectura o no contiene un laberinto válido.
   */
  @SuppressWarnings ("unchecked")
  public void loadFile (String fileName) throws IOException {
    try {
      FileInputStream fileIn = new FileInputStream(fileName);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      m_maze = (ArrayList <ArrayList <MazeCell>>) in.readObject();
      m_exit = (Point) in.readObject();
      in.close();
      fileIn.close();
    }
    catch (ClassNotFoundException c) {
      throw new IOException(c);
    }
  }

  /**
   * @param fileName Nombre del fichero donde guardar el laberinto.
   * @throws IOException
   *           Cuando el fichero no se puede abrir o no se tienen permisos de
   *           escritura en el mismo.
   */
  public void saveFile (String fileName) throws IOException {
    FileOutputStream fileOut = new FileOutputStream(fileName);
    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    out.writeObject(m_maze);
    out.writeObject(m_exit);
    out.close();
    fileOut.close();
  }

  /**
   * Calcula el número máximo de aristas que se pueden añadir en un laberinto
   * del tamaño dado. Cada arista se refiere a un pasillo abierto entre 2
   * celdas. Se puede ver como el número de paredes que tiene en su interior un
   * laberinto perfecto, sin contar el contorno.
   * @param rows Número de filas.
   * @param columns Número de columnas.
   * @return Número de paredes en el interior del laberinto.
   */
  public static int perfectMazeWalls (int rows, int columns) {
    return (rows-1) * (columns-1);
  }

  /**
   * Calcula el número de aristas que tiene un laberinto perfecto de las
   * dimensiones dadas. Las aristas representan pasillos entre 2 celdas.
   * @param rows Númnero de filas.
   * @param columns Número de columnas.
   * @return Número de aristas del laberinto perfecto.
   */
  public static int perfectMazeEdges (int rows, int columns) {
    return (rows * columns) - 1;
  }
}
