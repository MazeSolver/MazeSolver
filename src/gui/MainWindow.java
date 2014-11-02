/**
 * @file MainWindow.java
 * @date 21/10/2014
 */
package gui;

import gui.environment.EnvironmentSet;
import gui.environment.SimpleEnvironment;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToolBar;

import maze.Maze;
import maze.algorithm.Prim;

/**
 * Ventana principal del programa. Sólo puede haber una, así que implementa el
 * patrón 'singleton'.
 */
public class MainWindow extends JFrame {
  private static String APP_NAME = "Maze Solver";
  private static int DEFAULT_WIDTH = 640;
  private static int DEFAULT_HEIGHT = 480;

  private static final long serialVersionUID = 1L;
  private static MainWindow m_instance;

  /**
   * @param args No utilizados.
   */
  public static void main (String [] args) {
    MainWindow wnd = MainWindow.getInstance();

    wnd.setTitle(APP_NAME);
    wnd.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    wnd.setVisible(true);
  }

  /**
   * @return Instancia única de la clase.
   */
  public static MainWindow getInstance () {
    return m_instance != null? m_instance : new MainWindow();
  }

  // Panel que contiene la barra de acciones y tanto el panel con los laberintos
  // como el panel de configuración.
  private JPanel m_global_panel;

  // Barra de menús
  private JMenuBar m_menu_bar;

  // Barra de acciones
  private JToolBar m_toolbar;

  // Panel de configuración. Por defecto está oculto, pero cuando el usuario
  // vaya a configurar un agente la interfaz adecuada aparecerá en su lugar.
  private JPanel m_config_panel;

  // Otros elementos en la interfaz
  private JButton m_run, m_step, m_pause, m_stop;
  private JSlider m_zoom;
  private JMenu m_menu_file, m_menu_maze, m_menu_agent, m_menu_help;
  private JMenuItem m_itm_maze_open, m_itm_exit;
  private JMenuItem m_itm_maze_clone, m_itm_maze_change, m_itm_maze_close;
  private JMenuItem m_itm_agent_add, m_itm_agent_config, m_itm_agent_remove;
  private JMenuItem m_itm_about;

  EnvironmentSet m_environments;

  /**
   * Constructor de la clase. Crea la interfaz y configura su estado interno
   * para permitir el uso del programa.
   */
  private MainWindow () {
    super();
    createInterface();
  }

  /**
   * Construye los objetos de los que se compone la interfaz.
   */
  private void createInterface () {
    setLayout(new BorderLayout());

    createMenus();
    createToolbar();

    m_global_panel = new JPanel(new BorderLayout());
    m_global_panel.add(m_toolbar, BorderLayout.NORTH);
    m_environments = new EnvironmentSet();

    // XXX Sólo de prueba. Borrar cuando se haya probado la visualización del
    // laberinto y la generación del mismo.
    m_environments.addEnvironment(new SimpleEnvironment(new Maze(new Prim(20, 20))));

    m_global_panel.add(m_environments, BorderLayout.CENTER);
    add(m_menu_bar, BorderLayout.NORTH);
    add(m_global_panel, BorderLayout.CENTER);

    pack();
  }

  /**
   * Crea la barra de menús y sus elementos.
   */
  private void createMenus () {
    m_menu_bar = new JMenuBar();

    m_menu_file = new JMenu("File");
    m_menu_maze = new JMenu("Maze");
    m_menu_agent = new JMenu("Agent");
    m_menu_help = new JMenu("Help");

    // Menú "File"
    m_itm_maze_open = new JMenuItem("Open maze...");
    m_itm_exit = new JMenuItem("Exit");

    m_menu_file.add(m_itm_maze_open);
    m_menu_file.addSeparator();
    m_menu_file.add(m_itm_exit);

    // Menú "Maze"
    m_itm_maze_clone = new JMenuItem("Clone maze");
    m_itm_maze_change = new JMenuItem("Change maze...");
    m_itm_maze_close = new JMenuItem("Close maze");

    m_menu_maze.add(m_itm_maze_clone);
    m_menu_maze.add(m_itm_maze_change);
    m_menu_maze.add(m_itm_maze_close);

    // Menú "Agent"
    m_itm_agent_add = new JMenuItem("Add agent...");
    m_itm_agent_config = new JMenuItem("Configure agent...");
    m_itm_agent_remove = new JMenuItem("Remove agent");

    m_menu_agent.add(m_itm_agent_add);
    m_menu_agent.add(m_itm_agent_config);
    m_menu_agent.add(m_itm_agent_remove);

    // Menú "Help"
    m_itm_about = new JMenuItem("About...");
    m_menu_help.add(m_itm_about);

    m_menu_bar.add(m_menu_file);
    m_menu_bar.add(m_menu_maze);
    m_menu_bar.add(m_menu_agent);
    m_menu_bar.add(m_menu_help);
  }

  /**
   * Crea la barra de acciones.
   */
  private void createToolbar () {
    m_toolbar = new JToolBar();
    m_toolbar.setFloatable(false);

    m_run = new JButton("Run");
    m_step = new JButton("Step");
    m_pause = new JButton("Pause");
    m_stop = new JButton("Stop");
    m_zoom = new JSlider(1, 100);

    m_toolbar.add(m_run);
    m_toolbar.add(m_step);
    m_toolbar.add(m_pause);
    m_toolbar.add(m_stop);
    m_toolbar.add(new JSeparator(JSeparator.VERTICAL));
    m_toolbar.add(new JLabel("Zoom:"));
    m_toolbar.add(m_zoom);
  }

  /**
   * Cierra el panel de configuración.
   */
  public void closeConfigurationPanel () {
    // TODO Se requiere una versión más actualizada de la clase
  }

}
