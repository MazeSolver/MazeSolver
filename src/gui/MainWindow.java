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
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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
import maze.algorithm.AldousBroder;
import util.SimulationManager;
import util.SimulationResults;
import agent.Agent;
import agent.SARulesAgent;

/**
 * Ventana principal del programa. Sólo puede haber una, así que implementa el
 * patrón 'singleton'.
 */
public class MainWindow extends JFrame implements Observer {
  private static String APP_NAME = "Maze Solver";
  private static int DEFAULT_WIDTH = 640;
  private static int DEFAULT_HEIGHT = 480;

  private static int MINIMUM_ZOOM_VAL = 1;
  private static int MAXIMUM_ZOOM_VAL = 100;
  private static double MINIMUM_ZOOM_AUG = 1;
  private static double MAXIMUM_ZOOM_AUG = 3;

  private static final long serialVersionUID = 1L;
  private static MainWindow s_instance;

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
    if (s_instance == null)
      s_instance = new MainWindow();
    return s_instance;
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
  private JMenuItem m_itm_agent_add, m_itm_agent_config, m_itm_agent_clone,
                    m_itm_agent_remove;
  private JMenuItem m_itm_about;

  // Representación del modelo
  EnvironmentSet m_environments;
  SimulationManager m_simulation;

  /**
   * Constructor de la clase. Crea la interfaz y configura su estado interno
   * para permitir el uso del programa.
   */
  private MainWindow () {
    createInterface();
    m_simulation = new SimulationManager(m_environments);
    m_simulation.addObserver(this);
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
    m_itm_agent_clone = new JMenuItem("Clone agent");
    m_itm_agent_remove = new JMenuItem("Remove agent");

    m_menu_agent.add(m_itm_agent_add);
    m_menu_agent.add(m_itm_agent_config);
    m_menu_agent.add(m_itm_agent_clone);
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
    m_zoom.setValue(MINIMUM_ZOOM_VAL);

    m_pause.setEnabled(false);
    m_stop.setEnabled(false);

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
        m_environments.addEnvironment(new SimpleEnvironment(new Maze(new AldousBroder(10, 10))));
      }
    });

    m_itm_maze_save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          // TODO Desactivar los controles que no se pueden usar cuando no hay
          // entornos seleccionados para evitar tener que crear un montón de
          // mensajes de error
          FileDialog.saveMaze(m_environments.getSelectedEnvironment().getMaze());
        }
        catch (IOException exc) {
          // TODO Manejar excepción -- Mostrar error
        }
      }
    });

    m_itm_maze_open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Llamar al método para cargar un fichero de laberinto
        try {
          Maze[] mazes = FileDialog.loadMazes();
          for (Maze maze: mazes)
            m_environments.addEnvironment(new SimpleEnvironment(maze));
        }
        catch (IOException exc) {
          // TODO Manejar excepción -- Mostrar error
        }
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
        try {
          Maze maze = FileDialog.loadMaze();

          if (maze != null) {
            Environment actual_env = m_environments.getSelectedEnvironment();
            Environment new_env = new SimpleEnvironment(maze);

            for (int i = 0; i < actual_env.getAgentCount(); i++)
              new_env = new_env.addAgent(actual_env.getAgent(i));

            m_environments.exchangeEnvironments(actual_env, new_env);
          }
        }
        catch (IOException exc) {
          // TODO Reportar error
        }
      }
    });

    m_itm_maze_close.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        m_environments.removeSelectedEnvironment();
      }
    });

    // Menú "Agent"
    ///////////////////////////////////////////////////////////////////////////
    m_itm_agent_add.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        // TODO Mostrar la interfaz para la selección de un tipo de agente y
        // agregarlo al entorno en alguna posición disponible

        // XXX Sólo para pruebas
        m_environments.addAgentToSelectedEnvironment(new SARulesAgent(m_environments.getSelectedEnvironment()));
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

    m_itm_agent_clone.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Environment env = m_environments.getSelectedEnvironment();
          m_environments.addAgentToSelectedEnvironment((Agent) env.getSelectedAgent().clone());
        }
        catch (Exception exc) {
          // TODO Mostrar error (No se ha podido acceder al agente seleccionado)
          // No hay agente seleccionado o no hay entorno seleccionado
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

  /**
   * Configura los listeners de los botones en la barra de herramientas.
   */
  private void setupToolbarListeners () {
    m_run.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        startSimulation();
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
        pauseSimulation();
      }
    });

    m_stop.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        stopSimulation();
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
    if (m_config_panel != null)
      closeConfigurationPanel();

    if (panel != null) {
      ((BasicSplitPaneUI) m_split_panel.getUI()).getDivider().setVisible(true);

      m_config_panel = panel;
      m_split_panel.add(m_config_panel);
      revalidate();
      repaint();
    }
  }

  /**
   * Adapta los menús al estado de "Simulación en curso" y la comienza.
   */
  private void startSimulation () {
    // Desactivamos los menús que no se pueden utilizar durante la simulación
    m_itm_maze_new.setEnabled(false);
    m_itm_maze_change.setEnabled(false);
    m_itm_maze_open.setEnabled(false);
    m_itm_maze_clone.setEnabled(false);

    m_run.setEnabled(false);
    m_step.setEnabled(false);
    m_pause.setEnabled(true);
    m_stop.setEnabled(true);

    m_simulation.startSimulation();
  }

  /**
   * Pausa o reanuda la simulación dependiendo de su estado y mantiene coherente
   * el estado de los menús.
   */
  private void pauseSimulation () {
    if (!m_simulation.isPaused()) {
      m_pause.setText("Continue");
      m_simulation.pauseSimulation();
      m_step.setEnabled(true);
    }
    else {
      m_pause.setText("Pause");
      m_step.setEnabled(false);
      m_simulation.startSimulation();
    }
  }

  /**
   * Para la simulación y vuelve a dejar los menús en su estado inicial.
   */
  private void stopSimulation () {
    m_simulation.stopSimulation();
    for (Environment env: m_environments.getEnvironmentList()) {
      for (int i = 0; i < env.getAgentCount(); i++)
        env.getAgent(i).resetMemory();
    }

    // Volvemos a activar los menús que desactivamos antes
    m_itm_maze_new.setEnabled(true);
    m_itm_maze_change.setEnabled(true);
    m_itm_maze_open.setEnabled(true);
    m_itm_maze_clone.setEnabled(true);

    m_pause.setText("Pause");
    m_run.setEnabled(true);
    m_step.setEnabled(true);
    m_pause.setEnabled(false);
    m_stop.setEnabled(false);
  }

  /* (non-Javadoc)
   * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
   */
  @Override
  public void update (Observable obs, Object obj) {
    // Esto sucede cuando todos los entornos han terminado de ejecutarse.
    // TODO Mostrar estadísticas de ejecución
    SimulationResults results = (SimulationResults) obj;
  }

}
