/**
 * @file MainWindow.java
 * @date 21/10/2014
 */
package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Ventana principal del programa. Sólo puede haber una, así que implementa el
 * patrón 'singleton'.
 */
public class MainWindow extends JFrame {
  private static String APP_NAME = "Maze Solver";

  private static final long serialVersionUID = 1L;
  private static MainWindow m_instance;

  /**
   * @param args No utilizados.
   */
  public static void main (String [] args) {
    MainWindow wnd = MainWindow.getInstance();

    wnd.setTitle(APP_NAME);
    wnd.setSize(new Dimension(640, 480));
    wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    wnd.setVisible(true);
  }

  /**
   * @return Instancia única de la clase.
   */
  public static MainWindow getInstance () {
    return m_instance != null? m_instance : new MainWindow();
  }

  private MainWindow () {
    super();
    // TODO Crear la interfaz
  }

}
