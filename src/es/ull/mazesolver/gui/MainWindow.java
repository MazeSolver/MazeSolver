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
package es.ull.mazesolver.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.toolbar.WebToolBar;
import com.github.rodionmoiseev.c10n.C10N;
import com.github.rodionmoiseev.c10n.C10NConfigBase;
import com.github.rodionmoiseev.c10n.annotations.DefaultC10NAnnotations;
import com.github.rodionmoiseev.c10n.annotations.En;

import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.configuration.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.gui.environment.EnvironmentSet;
import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.translations.ButtonTranslations;
import es.ull.mazesolver.translations.Languages;
import es.ull.mazesolver.translations.MenuTranslations;
import es.ull.mazesolver.translations.SimulatorResultTranslations;
import es.ull.mazesolver.translations.Translatable;
import es.ull.mazesolver.translations.Translations;
import es.ull.mazesolver.util.InteractionMode;
import es.ull.mazesolver.util.SimulationManager;
import es.ull.mazesolver.util.SimulationResults;

/**
 * Ventana principal del programa. Sólo puede haber una, así que implementa el
 * patrón 'singleton'.
 */
public class MainWindow extends JFrame implements Observer, Translatable {
  private static String APP_NAME = "Maze Solver";
  private static String VERSION_STRING = "v1.2";
  private static int DEFAULT_WIDTH = 640;
  private static int DEFAULT_HEIGHT = 480;

  private static int MINIMUM_ZOOM_VAL = 1;
  private static int MAXIMUM_ZOOM_VAL = 100;
  private static double MINIMUM_ZOOM_AUG = 1;
  private static double MAXIMUM_ZOOM_AUG = 10;

  private static final String USER_DOCS_URL = "https://github.com/MazeSolver/MazeSolver/wiki";
  private static final String API_DOCS_URL = "https://mazesolver.github.io/MazeSolver/";
  private static final String ISSUES_URL = "https://github.com/MazeSolver/MazeSolver/labels/bug";

  private static final long serialVersionUID = 1L;
  private static MainWindow s_instance;
  private static Translations s_tr;

  /**
   * Inicializa la interfaz gráfica y la muestra por pantalla.
   *
   * @param args
   *          No utilizados.
   */
  public static void main (String [] args) {
    try {
      UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
    }
    catch (Exception e) {
    }
    MainWindow wnd = MainWindow.getInstance();

    wnd.setTitle(APP_NAME);
    wnd.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    wnd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    wnd.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    wnd.setLocationRelativeTo(null);
    wnd.setVisible(true);
  }

  /**
   * Obtiene la instancia única de la clase o la crea si no existe.
   *
   * @return Instancia única de la clase.
   */
  public static MainWindow getInstance () {
    if (s_instance == null)
      s_instance = new MainWindow();
    return s_instance;
  }

  /**
   * Obtiene la instancia de traductor de la aplicación o la crea si no había
   * sido creada antes.
   *
   * @return El traductor de la aplicación.
   */
  public static Translations getTranslations () {
    if (s_tr == null)
      s_tr = C10N.get(Translations.class);
    return s_tr;
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
  private JLabel m_zoom_lb;
  private JButton m_run, m_step, m_pause, m_stop;
  private JSlider m_zoom;
  private JMenu m_menu_file, m_menu_maze, m_menu_agent, m_menu_sim,
                m_menu_config, m_menu_language, m_menu_help;
  private JMenuItem m_itm_exit;
  private JMenuItem m_itm_maze_new, m_itm_maze_open, m_itm_maze_save,
                    m_itm_maze_copy, m_itm_maze_change, m_itm_env_close;
  private JMenuItem m_itm_agent_new, m_itm_agent_open, m_itm_agent_save,
                    m_itm_agent_copy, m_itm_agent_config, m_itm_agent_remove;
  private JMenuItem m_itm_sim_run, m_itm_sim_step, m_itm_sim_pause,
                    m_itm_sim_stop, m_itm_sim_fastrun;
  private JRadioButtonMenuItem m_itm_mode_sim, m_itm_mode_edit;
  private JMenuItem m_itm_language_spanish, m_itm_language_english,
                    m_itm_language_german, m_itm_language_russian,
                    m_itm_language_french;
  private JMenuItem m_itm_userdoc, m_itm_apidoc, m_itm_bugs, m_itm_about;

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
    C10N.configure(new C10NConfigBase() {
      @Override
      protected void configure () {
        // install default annotations
        install(new DefaultC10NAnnotations());

        bindBundle("translations.Translations");

        /**
         * setup c10n to fallback to @En annotation when locale does not match
         * any other registered annotation, by binding it without specifying the
         * locale.
         */
        bindAnnotation(En.class);
      }
    });
    // Lo llamamos para asegurarnos de que las traducciones están cargadas antes
    // de crear los mensajes de error.
    MainWindow.getTranslations();

    createInterface();
    m_simulation = new SimulationManager(m_environments);
    m_simulation.addObserver(this);

    // Una vez todo está creado traducimos la aplicación
    translate();
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
    final JSplitPane console_split =
        new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, m_split_panel, m_console);
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

    m_menu_file = new JMenu();
    m_menu_maze = new JMenu();
    m_menu_agent = new JMenu();
    m_menu_sim = new JMenu();
    m_menu_config = new JMenu();
    m_menu_language = new JMenu();
    m_menu_help = new JMenu();

    // Menú "File"
    m_itm_exit = new JMenuItem();

    m_menu_file.add(m_itm_exit);

    // Menú "Maze"
    m_itm_maze_new = new JMenuItem();
    m_itm_maze_open = new JMenuItem();
    m_itm_maze_save = new JMenuItem();
    m_itm_maze_copy = new JMenuItem();
    m_itm_maze_change = new JMenuItem();
    m_itm_env_close = new JMenuItem();

    m_menu_maze.add(m_itm_maze_new);
    m_menu_maze.addSeparator();
    m_menu_maze.add(m_itm_maze_open);
    m_menu_maze.add(m_itm_maze_save);
    m_menu_maze.addSeparator();
    m_menu_maze.add(m_itm_maze_copy);
    m_menu_maze.add(m_itm_maze_change);
    m_menu_maze.add(m_itm_env_close);

    // Menú "Agent"
    m_itm_agent_new = new JMenuItem();
    m_itm_agent_open = new JMenuItem();
    m_itm_agent_save = new JMenuItem();
    m_itm_agent_copy = new JMenuItem();
    m_itm_agent_config = new JMenuItem();
    m_itm_agent_remove = new JMenuItem();

    m_menu_agent.add(m_itm_agent_new);
    m_menu_agent.addSeparator();
    m_menu_agent.add(m_itm_agent_open);
    m_menu_agent.add(m_itm_agent_save);
    m_menu_agent.addSeparator();
    m_menu_agent.add(m_itm_agent_copy);
    m_menu_agent.add(m_itm_agent_config);
    m_menu_agent.addSeparator();
    m_menu_agent.add(m_itm_agent_remove);

    // Menú "Simulation"
    m_itm_sim_run = new JMenuItem();
    m_itm_sim_step = new JMenuItem();
    m_itm_sim_pause = new JMenuItem();
    m_itm_sim_stop = new JMenuItem();
    m_itm_sim_fastrun = new JMenuItem();

    m_menu_sim.add(m_itm_sim_run);
    m_menu_sim.add(m_itm_sim_step);
    m_menu_sim.add(m_itm_sim_pause);
    m_menu_sim.add(m_itm_sim_stop);
    m_menu_sim.addSeparator();
    m_menu_sim.add(m_itm_sim_fastrun);

    // Menú "Configuration"
    m_itm_mode_sim = new JRadioButtonMenuItem();
    m_itm_mode_edit = new JRadioButtonMenuItem();

    m_itm_mode_sim.setSelected(true);

    m_menu_config.add(m_itm_mode_sim);
    m_menu_config.add(m_itm_mode_edit);

    ButtonGroup group = new ButtonGroup();
    group.add(m_itm_mode_sim);
    group.add(m_itm_mode_edit);

    // Menú "Language"
    m_itm_language_spanish = new JMenuItem();
    m_itm_language_english = new JMenuItem();
    m_itm_language_german = new JMenuItem();
    m_itm_language_russian = new JMenuItem();
    m_itm_language_french = new JMenuItem();

    m_menu_language.add(m_itm_language_spanish);
    m_menu_language.add(m_itm_language_english);
    m_menu_language.add(m_itm_language_german);
    m_menu_language.add(m_itm_language_russian);
    m_menu_language.add(m_itm_language_french);

    // Menú "Help"
    ImageIcon world = new ImageIcon(getClass().getResource("/images/world.png"));
    ImageIcon info = new ImageIcon(getClass().getResource("/images/info.png"));
    m_itm_userdoc = new JMenuItem("", world);
    m_itm_apidoc = new JMenuItem("", world);
    m_itm_bugs = new JMenuItem("", world);
    m_itm_about = new JMenuItem("", info);

    m_menu_help.add(m_itm_userdoc);
    m_menu_help.add(m_itm_apidoc);
    m_menu_help.add(m_itm_bugs);
    m_menu_help.addSeparator();
    m_menu_help.add(m_itm_about);

    m_menu_bar.add(m_menu_file);
    m_menu_bar.add(m_menu_maze);
    m_menu_bar.add(m_menu_agent);
    m_menu_bar.add(m_menu_sim);
    m_menu_bar.add(m_menu_config);
    m_menu_bar.add(m_menu_language);
    m_menu_bar.add(m_menu_help);

    setupMenuListeners();
  }

  /**
   * Crea la barra de acciones.
   */
  private void createToolbar () {
    m_toolbar = new WebToolBar();
    m_toolbar.setFloatable(false);

    m_run = new JButton();
    m_step = new JButton();
    m_pause = new JButton();
    m_stop = new JButton();
    m_zoom = new JSlider(MINIMUM_ZOOM_VAL, MAXIMUM_ZOOM_VAL);
    m_zoom_lb = new JLabel();

    m_pause.setEnabled(false);
    m_stop.setEnabled(false);
    m_zoom.setValue(MINIMUM_ZOOM_VAL);

    m_toolbar.add(m_run);
    m_toolbar.add(m_step);
    m_toolbar.add(m_pause);
    m_toolbar.add(m_stop);
    m_toolbar.addToEnd(m_zoom_lb);
    m_toolbar.addToEnd(m_zoom);

    setupToolbarListeners();
  }

  /**
   * Crea los ActionListeners para los menús.
   */
  private void setupMenuListeners () {
    // Menú "File"
    // /////////////////////////////////////////////////////////////////////////
    m_itm_exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        dispatchEvent(new WindowEvent(MainWindow.getInstance(), WindowEvent.WINDOW_CLOSING));
      }
    });

    // Menú "Maze"
    // /////////////////////////////////////////////////////////////////////////
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

    m_itm_maze_open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Maze [] mazes = FileDialog.loadMazes();
          for (Maze maze: mazes)
            m_environments.addEnvironment(new Environment(maze));
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), s_tr.message().fileOpenFailed(),
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    m_itm_maze_save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          FileDialog.saveMaze(m_environments.getSelectedEnvironment().getMaze());
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), s_tr.message().fileSaveFailed(),
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, s_tr.message().noEnvironmentSelected(), s_tr
              .message().fileSaveFailed(), JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    m_itm_maze_copy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        Environment env = m_environments.getSelectedEnvironment();
        if (env != null)
          m_environments.addEnvironment(new Environment(env.getMaze()));
        else {
          JOptionPane.showMessageDialog(null, s_tr.message().noEnvironmentSelected(), s_tr
              .message().cloningFailed(), JOptionPane.WARNING_MESSAGE);
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
          JOptionPane.showMessageDialog(null, exc.getMessage(), s_tr.message().mazeChangeFailed(),
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, s_tr.message().noEnvironmentSelected(), s_tr
              .message().mazeChangeFailed(), JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    m_itm_env_close.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        m_environments.removeSelectedEnvironment();
      }
    });

    // Menú "Agent"
    // /////////////////////////////////////////////////////////////////////////
    m_itm_agent_new.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        Environment env = m_environments.getSelectedEnvironment();
        if (env == null) {
          JOptionPane.showMessageDialog(null, s_tr.message().noEnvironmentSelected(), s_tr
              .message().agentCreationFailed(), JOptionPane.WARNING_MESSAGE);
          return;
        }

        Maze maze = env.getMaze();
        AgentSelectorDialog dialog =
            new AgentSelectorDialog(MainWindow.this, (maze.getHeight() * maze.getWidth())
                - env.getAgentCount());
        dialog.setLocationRelativeTo(MainWindow.this);
        Agent [] agents = dialog.showDialog();

        if (agents != null) {
          for (Agent ag: agents)
            m_environments.addAgentToSelectedEnvironment(ag);
        }
      }
    });

    m_itm_agent_open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Environment env = m_environments.getSelectedEnvironment();
          Agent ag = FileDialog.loadAgent(env);

          m_environments.addAgentToSelectedEnvironment(ag);
        }
        catch (IOException exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), s_tr.message().fileOpenFailed(),
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, s_tr.message().noEnvironmentSelected(), s_tr
              .message().fileOpenFailed(), JOptionPane.WARNING_MESSAGE);
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
          JOptionPane.showMessageDialog(null, exc.getMessage(), s_tr.message().fileSaveFailed(),
              JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, s_tr.message().noEnvironmentSelected(), s_tr
              .message().fileSaveFailed(), JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    m_itm_agent_copy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Environment env = m_environments.getSelectedEnvironment();
          m_environments.addAgentToSelectedEnvironment((Agent) env.getSelectedAgent().clone());
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, s_tr.message().noAgentSelected(), s_tr.message()
              .cloningFailed(), JOptionPane.WARNING_MESSAGE);
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
          JOptionPane.showMessageDialog(null, s_tr.message().noAgentSelected(), s_tr.message()
              .agentConfigFailed(), JOptionPane.WARNING_MESSAGE);
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
          JOptionPane.showMessageDialog(null, s_tr.message().noAgentSelected(), s_tr.message()
              .agentRemovalFailed(), JOptionPane.WARNING_MESSAGE);
        }
      }
    });

    // Menú "Simulation"
    m_itm_sim_run.addActionListener(new RunAction());
    m_itm_sim_step.addActionListener(new StepAction());
    m_itm_sim_pause.addActionListener(new PauseAction());
    m_itm_sim_stop.addActionListener(new StopAction());

    m_itm_sim_fastrun.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        FastSimulationDialog dialog = new FastSimulationDialog(MainWindow.this);
        int steps = dialog.showDialog();

        if (steps > 0)
          startFastSimulation(steps);
      }
    });

    // Menú "Configuration"
    m_itm_mode_sim.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        setInteractionModeState(InteractionMode.SIMULATION);
        m_environments.setInteractionMode(InteractionMode.SIMULATION);
      }
    });

    m_itm_mode_edit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        setInteractionModeState(InteractionMode.EDITION);
        m_environments.setInteractionMode(InteractionMode.EDITION);
      }
    });

    // Menú "Languages"
    // /////////////////////////////////////////////////////////////////////////
    class LanguageAction implements ActionListener {
      private String m_lang_code;

      public LanguageAction (String lang_code) {
        m_lang_code = lang_code;
      }

      @Override
      public void actionPerformed (ActionEvent e) {
        s_tr = C10N.get(Translations.class, new Locale(m_lang_code));
        translate();
      }
    }

    m_itm_language_spanish.addActionListener(new LanguageAction("es"));
    m_itm_language_english.addActionListener(new LanguageAction("en"));
    m_itm_language_german.addActionListener(new LanguageAction("de"));
    m_itm_language_russian.addActionListener(new LanguageAction("ru"));
    m_itm_language_french.addActionListener(new LanguageAction("fr"));

    // Menú "Help"
    // /////////////////////////////////////////////////////////////////////////
    class URLLauncher implements ActionListener {
      private String m_url;

      public URLLauncher (String url) {
        m_url = url;
      }

      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          Desktop.getDesktop().browse(URI.create(m_url));
        }
        catch (Exception exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), s_tr.message().urlLaunchFailed(),
              JOptionPane.ERROR_MESSAGE);
        }
      }
    }

    m_itm_userdoc.addActionListener(new URLLauncher(USER_DOCS_URL));
    m_itm_apidoc.addActionListener(new URLLauncher(API_DOCS_URL));
    m_itm_bugs.addActionListener(new URLLauncher(ISSUES_URL));

    m_itm_about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        JOptionPane
            .showMessageDialog(
                null,
                "<html><h2>" + APP_NAME + " " + VERSION_STRING + "</h2>"
                    + "Copyright &copy; 2014 - 2015<br>Sergio M. Afonso Fumero and Kevin I. Robayna Hernández<br><br>"
                    + "This program is free software: you can redistribute it and/or modify<br>"
                    + "it under the terms of the GNU General Public License as published by<br>"
                    + "the Free Software Foundation, either version 3 of the License, or<br>"
                    + "(at your option) any later version.<br><br>"
                    + "This program is distributed in the hope that it will be useful,<br>"
                    + "but WITHOUT ANY WARRANTY; without even the implied warranty of<br>"
                    + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the<br>"
                    + "GNU General Public License for more details.<br><br>"
                    + "You should have received a copy of the GNU General Public License<br>"
                    + "along with this program. If not, see &lt;<a href=\"http://www.gnu.org/licenses/\">http://www.gnu.org/licenses/</a>&gt;.</html>",
                s_tr.menu().about(), JOptionPane.INFORMATION_MESSAGE);
      }
    });
  }

  /**
   * Configura los listeners de los botones en la barra de herramientas.
   */
  private void setupToolbarListeners () {
    m_run.addActionListener(new RunAction());
    m_step.addActionListener(new StepAction());
    m_pause.addActionListener(new PauseAction());
    m_stop.addActionListener(new StopAction());

    m_zoom.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged (ChangeEvent e) {
        JSlider src = (JSlider) e.getSource();

        // Ajuste lineal del zoom
        double a = (MAXIMUM_ZOOM_AUG - MINIMUM_ZOOM_AUG) / (MAXIMUM_ZOOM_VAL - MINIMUM_ZOOM_VAL);
        double b = MAXIMUM_ZOOM_AUG - a * MAXIMUM_ZOOM_VAL;
        m_environments.setZoom(a * src.getValue() + b);
      }
    });
  }

  /**
   * Devuelve una referencia a la consola.
   *
   * @return Referencia a la consola de la ventana.
   */
  public LoggingConsole getConsole () {
    return m_console;
  }

  /**
   * Devuelve una referencia al conjunto de entornos.
   *
   * @return Referencia al conjunto de entornos de la ventana.
   */
  public EnvironmentSet getEnvironments () {
    return m_environments;
  }

  /**
   * Abre el panel de configuración.
   *
   * @param ag_panel
   *          Panel de configuración que se quiere abrir.
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

      m_itm_env_close.setEnabled(false);
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
    m_itm_env_close.setEnabled(true);
    m_itm_agent_remove.setEnabled(true);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
   */
  @Override
  public void update (Observable obs, Object obj) {
    SimulatorResultTranslations tr_sim = s_tr.simulation();

    // La notificación viene del comienzo de la ejecución
    if (m_simulation.isRunning())
      setSimulatingState();
    // La notificación viene de una pausa en la ejecución
    else if (m_simulation.isPaused())
      setPausedContinuedState();
    // La notificación viene de la parada o finalización de la ejecución
    else {
      stopSimulation();
      SimulationResults results = (SimulationResults) obj;

      ArrayList <Environment> envs = m_environments.getEnvironmentList();
      ArrayList <Maze> mazes = new ArrayList <Maze>();
      for (Environment env: envs) {
        if (!mazes.contains(env.getMaze()))
          mazes.add(env.getMaze());
      }

      m_console.writeInfo(tr_sim.title());
      m_console.writeInfo("==================");
      for (int i = 0; i < mazes.size(); i++) {
        Maze maze = mazes.get(i);
        Agent maze_winner = results.getWinner(maze);
        m_console.writeInfo("=== " + tr_sim.maze() + " " + (i + 1) + " (" + maze.getWidth() + "x"
            + maze.getHeight() + ") ===");
        m_console.writeInfo("* " + tr_sim.timeTakenFirst() + ": " + results.timeTakenFirst(maze));
        m_console.writeInfo("* " + tr_sim.timeTakenLast() + ": " + results.timeTakenLast(maze));
        m_console.writeInfo("* " + tr_sim.winner() + ": "
            + (maze_winner != null? maze_winner.getAgentName() : tr_sim.none()));
        m_console.writeInfo("");

        for (int j = 0; j < envs.size(); j++) {
          Environment env = envs.get(j);
          if (env.getMaze() == maze) {
            Agent env_winner = results.getWinner(env);
            m_console.writeInfo("  == " + env.getTitle() + " ==");
            m_console.writeInfo("* " + tr_sim.timeTakenFirst() + ": " + results.timeTakenFirst(env));
            m_console.writeInfo("* " + tr_sim.timeTakenLast() + ": " + results.timeTakenLast(env));
            m_console.writeInfo("* " + tr_sim.winner() + ": "
                + (env_winner != null? env_winner.getAgentName() : tr_sim.none()));
            m_console.writeInfo("");
            m_console.writeInfo("  * " + tr_sim.agentsDetail() + ":");

            for (Map.Entry <Agent, Integer> entry: results.getSteps(env).entrySet()) {
              Agent ag = entry.getKey();
              String finished =
                  maze.containsPoint(new Point(ag.getX(), ag.getY()))? tr_sim.notFinished() : tr_sim
                      .finished();
              m_console.writeInfo("    - " + ag.getAgentName() + ": " + entry.getValue() + " "
                  + tr_sim.steps() + " [" + finished + "]");
            }
          }
        }
      }
      m_console.writeInfo("==================");
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.translations.Translatable#translate()
   */
  @Override
  public void translate () {
    MenuTranslations m_tr = s_tr.menu();
    ButtonTranslations b_tr = s_tr.button();
    Languages m_lang_tr = s_tr.languages();

    m_menu_file.setText(m_tr.file());
    m_menu_maze.setText(m_tr.maze());
    m_menu_agent.setText(m_tr.agent());
    m_menu_sim.setText(m_tr.simulation());
    m_menu_config.setText(m_tr.configuration());
    m_menu_language.setText(m_tr.language());
    m_menu_help.setText(m_tr.help());

    m_itm_exit.setText(m_tr.exit());
    m_itm_maze_new.setText(m_tr.newMaze() + "...");
    m_itm_maze_open.setText(m_tr.openMaze() + "...");
    m_itm_maze_save.setText(m_tr.saveMaze() + "...");
    m_itm_maze_copy.setText(m_tr.copyMaze());
    m_itm_maze_change.setText(m_tr.changeMaze() + "...");
    m_itm_env_close.setText(m_tr.closeMaze());
    m_itm_agent_new.setText(m_tr.newAgent() + "...");
    m_itm_agent_open.setText(m_tr.loadAgent() + "...");
    m_itm_agent_save.setText(m_tr.saveAgent() + "...");
    m_itm_agent_copy.setText(m_tr.cloneAgent());
    m_itm_agent_config.setText(m_tr.configureAgent() + "...");
    m_itm_agent_remove.setText(m_tr.removeAgent());
    m_itm_sim_run.setText(b_tr.run());
    m_itm_sim_step.setText(b_tr.step());
    m_itm_sim_stop.setText(b_tr.stop());
    m_itm_sim_fastrun.setText(b_tr.fastRun() + "...");
    m_itm_mode_sim.setText(m_tr.simulationMode());
    m_itm_mode_edit.setText(m_tr.editMode());
    m_itm_language_spanish.setText(m_lang_tr.spanish());
    m_itm_language_english.setText(m_lang_tr.english());
    m_itm_language_german.setText(m_lang_tr.german());
    m_itm_language_russian.setText(m_lang_tr.russian());
    m_itm_language_french.setText(m_lang_tr.french());
    m_itm_userdoc.setText(m_tr.userDocs());
    m_itm_apidoc.setText(m_tr.apiDocs());
    m_itm_bugs.setText(m_tr.knownProblems());
    m_itm_about.setText(m_tr.about() + "...");

    m_run.setText(b_tr.run());
    m_step.setText(b_tr.step());
    m_stop.setText(b_tr.stop());
    m_zoom_lb.setText(b_tr.zoom() + ":");

    String pauseText = b_tr.kontinue();
    if (m_simulation.isRunning() || m_simulation.isStopped())
      pauseText = b_tr.pause();

    m_pause.setText(pauseText);
    m_itm_sim_pause.setText(pauseText);

    m_console.translate();

    if (m_config_panel != null)
      m_config_panel.translate();
  }

  /**
   * Adapta los menús al estado de "Simulación en curso" y la comienza.
   */
  private void startSimulation () {
    //setSimulatingState();
    m_simulation.startSimulation();
  }

  /**
   * Adapta los menús al estado de "Simulación en curso" comienza una simulación
   * rápida.
   * @param steps
   *          Número máximo de pasos que se van a simular.
   */
  private void startFastSimulation (int steps) {
    if (m_simulation.isPaused()) {
      m_simulation.startFastSimulation(steps);
      setPausedContinuedState();
    }
    else {
      setSimulatingState();
      m_simulation.startFastSimulation(steps);
    }
  }

  /**
   * Pausa o reanuda la simulación dependiendo de su estado y mantiene coherente
   * el estado de los menús.
   */
  private void pauseSimulation () {
    if (m_simulation.isPaused())
      m_simulation.startSimulation();
    else
      m_simulation.pauseSimulation();

    setPausedContinuedState();
  }

  /**
   * Para la simulación y vuelve a dejar los menús en su estado inicial.
   */
  private void stopSimulation () {
    setStoppedState();

    m_simulation.stopSimulation();
    for (Environment env: m_environments.getEnvironmentList()) {
      for (int i = 0; i < env.getAgentCount(); i++)
        env.getAgent(i).resetMemory();
    }
  }

  /**
   * Adapta los menús al estado de "Simulación en curso".
   */
  private void setSimulatingState () {
    // Desactivamos los menús que no se pueden utilizar durante la simulación
    m_itm_maze_new.setEnabled(false);
    m_itm_maze_change.setEnabled(false);
    m_itm_maze_open.setEnabled(false);
    m_itm_maze_copy.setEnabled(false);

    m_run.setEnabled(false);
    m_step.setEnabled(false);
    m_pause.setEnabled(true);
    m_stop.setEnabled(true);

    m_itm_sim_run.setEnabled(false);
    m_itm_sim_step.setEnabled(false);
    m_itm_sim_pause.setEnabled(true);
    m_itm_sim_stop.setEnabled(true);

    m_itm_mode_edit.setEnabled(false);
  }

  /**
   * Adapta los menús al estado de "Simulación pausada" o "Simulación en curso"
   * dependiendo de si la simulación está pausada o en curso.
   */
  private void setPausedContinuedState () {
    if (m_simulation.isPaused()) {
      m_pause.setText(s_tr.button().kontinue());
      m_step.setEnabled(true);

      m_itm_sim_pause.setText(s_tr.button().kontinue());
      m_itm_sim_step.setEnabled(true);
    }
    else {
      m_pause.setText(s_tr.button().pause());
      m_step.setEnabled(false);

      m_itm_sim_pause.setText(s_tr.button().pause());
      m_itm_sim_step.setEnabled(false);
    }
  }

  /**
   * Adapta los menús al estado de "Simulación parada".
   */
  private void setStoppedState () {
    m_itm_maze_new.setEnabled(true);
    m_itm_maze_change.setEnabled(true);
    m_itm_maze_open.setEnabled(true);
    m_itm_maze_copy.setEnabled(true);

    m_pause.setText(s_tr.button().pause());
    m_run.setEnabled(true);
    m_step.setEnabled(true);
    m_pause.setEnabled(false);
    m_stop.setEnabled(false);

    m_itm_sim_pause.setText(s_tr.button().pause());
    m_itm_sim_run.setEnabled(true);
    m_itm_sim_step.setEnabled(true);
    m_itm_sim_pause.setEnabled(false);
    m_itm_sim_stop.setEnabled(false);

    m_itm_mode_edit.setEnabled(true);
  }

  /**
   * Adapta los menús dependiendo del modo de interacción al que se esté
   * cambiando.
   *
   * @param mode
   *          Nuevo modo de interacción.
   */
  private void setInteractionModeState (InteractionMode mode) {
    boolean enable = false;

    switch (mode) {
      case SIMULATION:
        enable = true;
        break;
      case EDITION:
        enable = false;
        break;
    }

    m_run.setEnabled(enable);
    m_step.setEnabled(enable);

    m_menu_sim.setEnabled(enable);
    m_menu_agent.setEnabled(enable);
  }

  private class RunAction implements ActionListener {
    @Override
    public void actionPerformed (ActionEvent e) {
      startSimulation();
    }
  }

  private class StepAction implements ActionListener {
    @Override
    public void actionPerformed (ActionEvent e) {
      m_simulation.stepSimulation();
    }
  }

  private class PauseAction implements ActionListener {
    @Override
    public void actionPerformed (ActionEvent e) {
      pauseSimulation();
    }
  }

  private class StopAction implements ActionListener {
    @Override
    public void actionPerformed (ActionEvent e) {
      stopSimulation();
    }
  }

}
