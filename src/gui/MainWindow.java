/**
 * @file MainWindow.java
 * @date 21/10/2014
 */
package gui;

import gui.environment.Environment;
import gui.environment.EnvironmentSet;
import gui.environment.SimpleEnvironment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import maze.Maze;
import maze.algorithm.Kruskal;
import agent.Agent;
import agent.SARulesAgent;

/**
 * Ventana principal del programa. Sólo puede haber una, así que implementa el
 * patrón 'singleton'.
 */
public class MainWindow extends JFrame {
  private static String APP_NAME = "Maze Solver";
  private static int DEFAULT_WIDTH = 640;
  private static int DEFAULT_HEIGHT = 480;

  private static int MINIMUM_ZOOM_VAL = 1;
  private static int MAXIMUM_ZOOM_VAL = 100;
  private static double MINIMUM_ZOOM_AUG = 1;
  private static double MAXIMUM_ZOOM_AUG = 3;

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
    if (m_instance == null)
      m_instance = new MainWindow();
    return m_instance;
  }

  // Panel que contiene tanto el panel con los laberintos como el panel de
  // configuración, de tal manera que se pueden cambiar de tamaño.
  private JSplitPane m_split_panel;

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
  private JMenuItem m_itm_maze_new, m_itm_maze_save, m_itm_maze_open,
                    m_itm_exit;
  private JMenuItem m_itm_maze_clone, m_itm_maze_change, m_itm_maze_close;
  private JMenuItem m_itm_agent_add, m_itm_agent_config, m_itm_agent_remove;
  private JMenuItem m_itm_about;

  EnvironmentSet m_environments;

  /**
   * Constructor de la clase. Crea la interfaz y configura su estado interno
   * para permitir el uso del programa.
   */
  private MainWindow () {
    createInterface();
  }

  /**
   * Construye los objetos de los que se compone la interfaz.
   */
  private void createInterface () {
    setLayout(new BorderLayout());

    createMenus();
    createToolbar();

    JPanel global_panel = new JPanel(new BorderLayout());
    global_panel.add(m_toolbar, BorderLayout.NORTH);
    m_environments = new EnvironmentSet();

    m_split_panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, null, m_environments);

    global_panel.add(m_split_panel, BorderLayout.CENTER);
    add(m_menu_bar, BorderLayout.NORTH);
    add(global_panel, BorderLayout.CENTER);

    closeConfigurationPanel();

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
    m_itm_maze_new = new JMenuItem("New maze...");
    m_itm_maze_save = new JMenuItem("Save maze...");
    m_itm_maze_open = new JMenuItem("Open maze...");
    m_itm_exit = new JMenuItem("Exit");

    m_menu_file.add(m_itm_maze_new);
    m_menu_file.addSeparator();
    m_menu_file.add(m_itm_maze_save);
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

    setupMenuListeners();
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
    m_zoom = new JSlider(MINIMUM_ZOOM_VAL, MAXIMUM_ZOOM_VAL);

    m_toolbar.add(m_run);
    m_toolbar.add(m_step);
    m_toolbar.add(m_pause);
    m_toolbar.add(m_stop);
    m_toolbar.add(new JSeparator(JSeparator.VERTICAL));
    m_toolbar.add(new JLabel("Zoom:"));
    m_toolbar.add(m_zoom);

    setupToolbarListeners();
  }

  /**
   * Crea los ActionListeners para los menús.
   */
  private void setupMenuListeners () {
    // Menú "File"
    ///////////////////////////////////////////////////////////////////////////
    m_itm_maze_new.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Mostrar interfaz para elegir las dimensiones y algoritmo para
        // generar el laberinto

        // XXX Sólo para pruebas
        m_environments.addEnvironment(new SimpleEnvironment(new Maze(new Kruskal(30, 30))));
      }
    });

    m_itm_maze_save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO LLamar al método para mostrar la interfaz para guardar el
        // laberinto y luego guardarlo
      }
    });

    m_itm_maze_open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Llamar al método para cargar un fichero de laberinto
      }
    });

    m_itm_exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        System.exit(0);
      }
    });

    // Menú "Maze"
    ///////////////////////////////////////////////////////////////////////////
    m_itm_maze_clone.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        Environment env = m_environments.getSelectedEnvironment();
        if (env != null)
          m_environments.addEnvironment(new SimpleEnvironment(env.getMaze()));
        else {
          // TODO Reportar error
        }
      }
    });

    m_itm_maze_change.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Llamar al método para cargar un fichero de laberinto
        // También hay que añadir los agentes al nuevo laberinto
      }
    });

    m_itm_maze_close.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        m_environments.removeSelectedEnvironment();
        repaint();
      }
    });

    // Menú "Agent"
    ///////////////////////////////////////////////////////////////////////////
    m_itm_agent_add.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Mostrar la interfaz para la selección de un tipo de agente y
        // agregarlo al entorno en alguna posición disponible
      }
    });

    m_itm_agent_config.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Agent ag = m_environments.getSelectedEnvironment().getSelectedAgent();
          setConfigurationPanel(ag.getConfigurationPanel());
        }
        catch (Exception exc) {
          // TODO Mostrar error (No se ha podido acceder al agente seleccionado)
          // No hay agente seleccionado o no hay entorno seleccionado

          // XXX Sólo para pruebas
          Agent ag = new SARulesAgent(m_environments.getSelectedEnvironment());
          setConfigurationPanel(ag.getConfigurationPanel());
        }
      }
    });

    m_itm_agent_remove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Environment env = m_environments.getSelectedEnvironment();
          m_environments.removeAgentFromEnvironment(env.getSelectedAgent(), env);
        }
        catch (Exception exc) {
          // TODO Mostrar error (No se ha podido acceder al agente seleccionado)
          // No hay agente seleccionado o no hay entorno seleccionado
        }
      }
    });

    // Menú "Help"
    ///////////////////////////////////////////////////////////////////////////
    m_itm_about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Mostrar interfaz con: Hecho por X, Y, Z. Sistemas Inteligentes
        // 2014, etc
      }
    });
  }

  private void setupToolbarListeners () {
    m_run.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Lanzar un hilo / timer / algo que regularmente ejecute un paso
        // de simulación en todos los entornos. Si la simulación está pausada,
        // se reanuda.
      }
    });

    m_step.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        for (Environment env: m_environments.getEnvironmentList())
          env.runStep();
      }
    });

    m_pause.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Pausar el hilo / timer / algo que se esté ejecutando si hay
        // alguno. No hacer nada si no hay nada ejecutándose o está pausado.
      }
    });

    m_stop.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Parar y eliminar el hilo / timer / algo y borrar la memoria de
        // los agentes
        for (Environment env: m_environments.getEnvironmentList()) {
          for (Agent ag: env.getAgents())
            ag.resetMemory();
        }
      }
    });

    m_zoom.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged (ChangeEvent e) {
        JSlider src = (JSlider) e.getSource();

        // Ajuste lineal del zoom
        double a = (MAXIMUM_ZOOM_AUG - MINIMUM_ZOOM_AUG) /
                   (MAXIMUM_ZOOM_VAL - MINIMUM_ZOOM_VAL);
        double b = MAXIMUM_ZOOM_AUG - a * MAXIMUM_ZOOM_VAL;
        m_environments.setZoom(a * src.getValue() + b);
      }
    });
  }

  /**
   * Cierra el panel de configuración.
   */
  public void closeConfigurationPanel () {
    ((BasicSplitPaneUI) m_split_panel.getUI()).getDivider().setVisible(false);
    if (m_config_panel != null) {
      m_split_panel.remove(m_config_panel);
      m_config_panel = null;
      revalidate();
      repaint();
    }
  }

  /**
   * Abre el panel de configuración.
   * @param panel Panel de configuración que se quiere abrir.
   */
  public void setConfigurationPanel (JPanel panel) {
    if (panel != null) {
      ((BasicSplitPaneUI) m_split_panel.getUI()).getDivider().setVisible(true);

      m_config_panel = panel;
      m_split_panel.add(m_config_panel);
      revalidate();
      repaint();
    }
  }

  /**
   * @return La lista de entornos cargados.
   */
  public EnvironmentSet getEnvironments () {
    return m_environments;
  }

}
