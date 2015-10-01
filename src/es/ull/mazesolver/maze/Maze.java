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
package es.ull.mazesolver.maze;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.util.Direction;

/**
 * Clase que representa un laberinto.
 */
public class Maze {
  private ArrayList <ArrayList <MazeCell>> m_maze;
  private Point m_exit;

  /**
   * Crea un laberinto a partir de un algoritmo de generación de laberintos
   * ya inicializado.
   *
   * @param alg
   *          Algoritmo de creación de laberintos ya inicializado
   */
  public Maze (MazeCreationAlgorithm alg) {
    if (alg != null) {
      m_maze = alg.createMaze();
      m_exit = alg.getExit();
    }
    else
      throw new IllegalArgumentException(
          MainWindow.getTranslations().exception().invalidMazeCreationAlgorithm());
  }

  /**
   * Crea un laberinto a partir de su versión serializada en un fichero.
   *
   * @param fileName
   *          Nombre del fichero del que cargar el laberinto.
   * @throws IOException
   *           Cuando no se encuentra el fichero, no se puede abrir para su
   *           lectura o no contiene un laberinto válido.
   */
  public Maze (String fileName) throws IOException {
    loadFile(fileName);
  }

  /**
   * Obtiene la celda situada en una posición concreta del laberinto.
   *
   * @param row
   *          Fila.
   * @param column
   *          Columna.
   * @return Celda en la posición indicada.
   */
  public MazeCell get (int row, int column) {
    return m_maze.get(row).get(column);
  }

  /**
   * Establece el contenido de una celda en el laberinto.
   *
   * @param row
   *          Fila.
   * @param column
   *          Columna.
   * @param cell
   *          Celda que se quiere introducir.
   */
  public void set (int row, int column, MazeCell cell) {
    m_maze.get(row).set(column, cell);
  }

  /**
   * Obtiene el número de columnas (anchura) del laberinto.
   *
   * @return Anchura (en celdas) del laberinto.
   */
  public int getWidth () {
    return m_maze.get(0).size();
  }

  /**
   * Obtiene el número de filas (altura) del laberinto.
   *
   * @return Altura (en celdas) del laberinto.
   */
  public int getHeight () {
    return m_maze.size();
  }

  /**
   * Cambia la salida del laberinto a otro lugar, añadiendo y eliminando las
   * paredes necesarias.
   *
   * @param pos
   *          Índice de la celda en la que se quiere colocar la salida.
   *          Dependiendo de la dirección este valor se referirá a una columna
   *          (si la dirección es arriba o abajo) o a una fila (si la dirección
   *          es izquierda o derecha).
   * @param dir
   *          Borde del laberinto donde colocar la salida.
   */
  public void setExit (int pos, Direction dir) {
    // Comprobamos que los parámetros son válidos
    if (pos < 0 || dir == Direction.NONE)
      return;
    if (dir.isVertical() && pos >= getWidth())
      return;
    if (dir.isHorizontal() && pos >= getHeight())
      return;

    // Tapamos la salida anterior antes de modificar su posición
    if (m_exit.x < 0)
      m_maze.get(m_exit.y).get(0).setWall(Direction.LEFT);
    else if (m_exit.x >= getWidth())
      m_maze.get(m_exit.y).get(getWidth() - 1).setWall(Direction.RIGHT);
    else if (m_exit.y < 0)
      m_maze.get(0).get(m_exit.x).setWall(Direction.UP);
    else if (m_exit.y >= getHeight())
      m_maze.get(getHeight() - 1).get(m_exit.x).setWall(Direction.DOWN);

    // Modificamos la salida del laberinto y abrimos la pared
    switch (dir) {
      case UP:
        m_exit.move(pos, 0);
        break;
      case DOWN:
        m_exit.move(pos, getHeight() - 1);
        break;
      case LEFT:
        m_exit.move(0, pos);
        break;
      case RIGHT:
        m_exit.move(getWidth() - 1, pos);
        break;
      default:
        break;
    }

    m_maze.get(m_exit.y).get(m_exit.x).unsetWall(dir);
    m_exit.setLocation(dir.movePoint(m_exit));
  }

  /**
   * Obtiene una copia del lugar donde está la salida del laberinto.
   *
   * @return La posición donde se encuentra la salida al laberinto.
   */
  public Point getExit () {
    return new Point(m_exit);
  }

  /**
   * Determina si el punto se encuentra dentro del laberinto o no.
   *
   * @param p
   *          Punto a testear.
   * @return Si el punto está dentro del laberinto o no.
   */
  public boolean containsPoint (Point p) {
    return p.x >= 0 && p.y >= 0 && p.x < getWidth() && p.y < getHeight();
  }

  /**
   * Carga una instancia de laberinto de un fichero que contiene una instancia
   * de esta clase serializada.
   *
   * @param fileName
   *          Nombre del fichero del que cargar el laberinto.
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
   * Guarda la actual instancia de la clase {@link Maze} en un fichero mediante
   * su serialización.
   *
   * @param fileName
   *          Nombre del fichero donde guardar el laberinto.
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
   *
   * @param rows
   *          Número de filas.
   * @param columns
   *          Número de columnas.
   * @return Número de paredes en el interior del laberinto.
   */
  public static int perfectMazeWalls (int rows, int columns) {
    return (rows * columns) - rows - columns + 1;
  }

  /**
   * Calcula el número de aristas que tiene un laberinto perfecto de las
   * dimensiones dadas. Las aristas representan pasillos entre 2 celdas.
   *
   * @param rows
   *          Númnero de filas.
   * @param columns
   *          Número de columnas.
   * @return Número de aristas del laberinto perfecto.
   */
  public static int perfectMazeEdges (int rows, int columns) {
    return (rows * columns) - 1;
  }
}
