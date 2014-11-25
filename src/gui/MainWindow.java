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
 * @file MainWindow.java
 * @date 21/10/2014
 */
package gui;

import gui.environment.Environment;
import gui.environment.EnvironmentSet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import maze.Maze;
import util.SimulationManager;
import util.SimulationResults;
import agent.Agent;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.toolbar.WebToolBar;

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
    try {
      UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
    }
    catch (Exception e){}

    MainWindow wnd = MainWindow.getInstance();

    wnd.setTitle(APP_NAME);
    wnd.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    wnd.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    wnd.setLocationRelativeTo(null);
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
  private WebToolBar m_toolbar;

  // Panel de configuración. Por defecto está oculto, pero cuando el usuario
  // vaya a configurar un agente la interfaz adecuada aparecerá en su lugar.
  private AgentConfigurationPanel m_config_panel;

  // Otros elementos en la interfaz
  private JButton m_run, m_step, m_pause, m_stop;
  private JSlider m_zoom;
  private JMenu m_menu_file, m_menu_maze, m_menu_agent, m_menu_help;
  private JMenuItem m_itm_maze_new, m_itm_maze_save, m_itm_maze_open,
                    m_itm_exit;
  private JMenuItem m_itm_maze_clone, m_itm_maze_change, m_itm_maze_close;
  private JMenuItem m_itm_agent_add, m_itm_agent_config, m_itm_agent_clone,
                    m_itm_agent_remove, m_itm_agent_save, m_itm_agent_load;
  private JMenuItem m_itm_about;

  // Representación del modelo
  private EnvironmentSet m_environments;
  private SimulationManager m_simulation;

  // Interacción con el usuario
  private LoggingConsole m_console;

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

    m_console = new LoggingConsole();
    final JSplitPane console_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, m_split_panel, m_console);
    m_console.addPropertyChangeListener("ConsoleDisplay", new PropertyChangeListener() {
      @Override
      public void propertyChange (PropertyChangeEvent e) {
        // Si antes estaba visible, ahora no lo está, así que ajustamos el
        // separador para que se minimice la consola
        if ((Boolean) e.getOldValue())
          console_split.resetToPreferredSizes();
        // Si no, redimensionamos el panel para mostrar la consola
        else
          console_split.setDividerLocation(0.75);
      }
    });

    console_split.setResizeWeight(1);

    global_panel.add(console_split, BorderLayout.CENTER);
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
    m_itm_maze_open = new JMenuItem("Open maze...");
    m_itm_maze_save = new JMenuItem("Save maze...");
    m_itm_exit = new JMenuItem("Exit");

    m_menu_file.add(m_itm_maze_new);
    m_menu_file.addSeparator();
    m_menu_file.add(m_itm_maze_open);
    m_menu_file.add(m_itm_maze_save);
    m_menu_file.addSeparator();
    m_menu_file.add(m_itm_exit);

    // Menú "Maze"
    m_itm_maze_clone = new JMenuItem("Clone maze");
    m_itm_maze_change = new JMenuItem("Change maze...");
    m_itm_maze_close = new JMenuItem("Close maze");

    m_menu_maze.add(m_itm_maze_clone);
    m_menu_maze.add(m_itm_maze_change);
    m_menu_maze.addSeparator();
    m_menu_maze.add(m_itm_maze_close);

    // Menú "Agent"
    m_itm_agent_add = new JMenuItem("New agent...");
    m_itm_agent_clone = new JMenuItem("Clone agent");
    m_itm_agent_config = new JMenuItem("Configure agent...");
    m_itm_agent_load = new JMenuItem("Load agent...");
    m_itm_agent_save = new JMenuItem("Save agent...");
    m_itm_agent_remove = new JMenuItem("Remove agent");

    m_menu_agent.add(m_itm_agent_add);
    m_menu_agent.addSeparator();
    m_menu_agent.add(m_itm_agent_clone);
    m_menu_agent.add(m_itm_agent_config);
    m_menu_agent.addSeparator();
    m_menu_agent.add(m_itm_agent_load);
    m_menu_agent.add(m_itm_agent_save);
    m_menu_agent.addSeparator();
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
    m_toolbar = new WebToolBar();
    m_toolbar.setFloatable(false);

    m_run = new JButton("Run");
    m_step = new JButton("Step");
    m_pause = new JButton("Pause");
    m_stop = new JButton("Stop");
    m_zoom = new JSlider(MINIMUM_ZOOM_VAL, MAXIMUM_ZOOM_VAL);

    m_pause.setEnabled(false);
    m_stop.setEnabled(false);
    m_zoom.setValue(MINIMUM_ZOOM_VAL);

    m_toolbar.add(m_run);
    m_toolbar.add(m_step);
    m_toolbar.add(m_pause);
    m_toolbar.add(m_stop);
    m_toolbar.addToEnd(new JLabel("Zoom:"));
    m_toolbar.addToEnd(m_zoom);

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
        MazeSelectorDialog dialog = new MazeSelectorDialog(MainWindow.getInstance());
        dialog.setLocationRelativeTo(MainWindow.this);
        Maze generated = dialog.showDialog();

        if (generated != null)
          m_environments.addEnvironment(new Environment(generated));
      }
    });

    m_itm_maze_save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          FileDialog.saveMaze(m_environments.getSelectedEnvironment().getMaze());
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "File save failed",
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, "There is no environment selected",
              "File save failed", JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    m_itm_maze_open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Maze[] mazes = FileDialog.loadMazes();
          for (Maze maze: mazes)
            m_environments.addEnvironment(new Environment(maze));
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "File open failed",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    m_itm_exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        dispatchEvent(new WindowEvent(MainWindow.getInstance(),
            WindowEvent.WINDOW_CLOSING));
      }
    });

    // Menú "Maze"
    ///////////////////////////////////////////////////////////////////////////
    m_itm_maze_clone.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        Environment env = m_environments.getSelectedEnvironment();
        if (env != null)
          m_environments.addEnvironment(new Environment(env.getMaze()));
        else {
          JOptionPane.showMessageDialog(null, "There are no mazes selected",
              "Cloning failed", JOptionPane.WARNING_MESSAGE);
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
            Environment new_env = new Environment(maze);

            for (int i = 0; i < actual_env.getAgentCount(); i++)
              new_env.addAgent(actual_env.getAgent(i));

            m_environments.exchangeEnvironments(actual_env, new_env);
          }
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "Maze change failed",
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, "There are no environments selected",
              "Maze change failed", JOptionPane.WARNING_MESSAGE);
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
        if (m_environments.getSelectedEnvironment() == null) {
          JOptionPane.showMessageDialog(null, "There are no environments selected",
              "Agent creation failed", JOptionPane.WARNING_MESSAGE);
          return;
        }

        AgentSelectorDialog dialog = new AgentSelectorDialog(MainWindow.this);
        dialog.setLocationRelativeTo(MainWindow.this);
        Agent[] agents = dialog.showDialog();

        if (agents != null) {
          for (Agent ag: agents)
            m_environments.addAgentToSelectedEnvironment(ag);
        }
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
          JOptionPane.showMessageDialog(null, "There are no agents selected",
              "Agent config failed", JOptionPane.WARNING_MESSAGE);
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
          JOptionPane.showMessageDialog(null, "There are no agents selected",
              "Agent cloning failed", JOptionPane.WARNING_MESSAGE);
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
          JOptionPane.showMessageDialog(null, "There are no agents selected",
              "Agent removing failed", JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    m_itm_agent_load.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Environment env = m_environments.getSelectedEnvironment();
          Agent ag = FileDialog.loadAgent(env);

          m_environments.addAgentToSelectedEnvironment(ag);
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "File load failed",
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, "There is no environment selected",
              "File load failed", JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    m_itm_agent_save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Environment env = m_environments.getSelectedEnvironment();
          FileDialog.saveAgent(env.getSelectedAgent());
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "File save failed",
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, "There is no environment selected",
              "File save failed", JOptionPane.WARNING_MESSAGE);
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
        m_simulation.run();
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
   * Devuelve una referencia a la consola.
   * @return Referencia a la consola de la ventana.
   */
  public LoggingConsole getConsole () {
    return m_console;
  }

  /**
   * Devuelve una referencia al conjunto de entornos.
   * @return Referencia al conjunto de entornos de la ventana.
   */
  public EnvironmentSet getEnvironments () {
    return m_environments;
  }

  /**
   * Abre el panel de configuración.
   * @param ag_panel Panel de configuración que se quiere abrir.
   */
  public void setConfigurationPanel (final AgentConfigurationPanel ag_panel) {
    if (m_config_panel != null)
      closeConfigurationPanel();

    if (ag_panel != null) {
      ((BasicSplitPaneUI) m_split_panel.getUI()).getDivider().setVisible(true);

      m_config_panel = ag_panel;

      ag_panel.addEventListener(new AgentConfigurationPanel.EventListener() {
        @Override
        public void onSuccess (ArrayList <String> msgs) {
          for (String msg: msgs)
            m_console.writeInfo(msg);
          closeConfigurationPanel();
        }

        @Override
        public void onError (ArrayList <String> errors) {
          for (String error: errors)
            m_console.writeError(error);
        }

        @Override
        public void onCancel () {
          closeConfigurationPanel();
        }
      });

      m_split_panel.add(m_config_panel);
      revalidate();
      repaint();

      m_itm_maze_close.setEnabled(false);
      m_itm_agent_remove.setEnabled(false);
    }
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
    m_itm_maze_close.setEnabled(true);
    m_itm_agent_remove.setEnabled(true);
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
    // Esto sucede cuando todos los entornos han terminado de ejecutarse o se ha
    // parado la simulación.
    stopSimulation();
    SimulationResults results = (SimulationResults) obj;

    ArrayList <Environment> envs = m_environments.getEnvironmentList();
    ArrayList <Maze> mazes = new ArrayList<Maze>();
    for (Environment env: envs) {
      if (!mazes.contains(env.getMaze()))
        mazes.add(env.getMaze());
    }

    m_console.writeInfo("SIMULATION RESULTS");
    m_console.writeInfo("==================");
    for (int i = 0; i < mazes.size(); i++) {
      Maze maze = mazes.get(i);
      Agent maze_winner = results.getWinner(maze);
      m_console.writeInfo("=== Maze " + (i+1) + " (" + maze.getWidth() + "x" + maze.getHeight() + ") ===");
      m_console.writeInfo("* Time taken first: " + results.timeTakenFirst(maze));
      m_console.writeInfo("* Time taken last: " + results.timeTakenLast(maze));
      m_console.writeInfo("* Winner: " + (maze_winner != null? maze_winner.getName() : "None"));
      m_console.writeInfo("");

      for (int j = 0; j < envs.size(); j++) {
        Environment env = envs.get(j);
        if (env.getMaze() == maze) {
          Agent env_winner = results.getWinner(env);
          m_console.writeInfo("  == " + env.getTitle() + " ==");
          m_console.writeInfo("  * Time taken first: " + results.timeTakenFirst(env));
          m_console.writeInfo("  * Time taken last: " + results.timeTakenLast(env));
          m_console.writeInfo("  * Winner: " + (env_winner != null? env_winner.getName() : "None"));
          m_console.writeInfo("");
          m_console.writeInfo("  * Agents detail:");

          for (Map.Entry<Agent, Integer> entry: results.getSteps(env).entrySet()) {
            Agent ag = entry.getKey();
            String finished = maze.containsPoint(new Point(ag.getX(), ag.getY()))? "NOT FINISHED" : "FINISHED";
            m_console.writeInfo("    - " + ag.getName() + ": "+
                                entry.getValue() + " steps [" + finished + "]");
          }
        }
      }
    }
    m_console.writeInfo("==================");
  }

}
