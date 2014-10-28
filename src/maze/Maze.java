/**
 * @file Maze.java
 * @date 21/10/2014
 */
package maze;

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

  /**
   * @param alg Algoritmo de creación de laberintos ya inicializado
   */
  public Maze (MazeCreationAlgorithm alg) {
    if (alg != null)
      m_maze = alg.createMaze();
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
    out.close();
    fileOut.close();
  }

}
