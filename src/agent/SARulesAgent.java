/**
 * @file SARulesAgent.java
 * @date 2/11/2014
 */
package agent;

import gui.MainWindow;
import gui.environment.Environment;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultEditorKit;

import maze.Direction;
import maze.Maze;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import util.Pair;
import agent.rules.RuleAction;
import agent.rules.SituationActionRule;
import agent.rules.parser.SituationActionErrorHandler;
import agent.rules.parser.SituationActionLexer;
import agent.rules.parser.SituationActionParser;
import agent.rules.parser.SituationActionParser.Sa_ruleContext;

/**
 * Agente cuya lógica se basa en reglas de situación-acción. Sigue una
 * arquitectura de subsunción, donde se aplica la regla de mayor precedencia
 * para la cual la situación se cumple.
 */
public class SARulesAgent extends Agent {
  public static final int MINIMUM_WIDTH = 300;
  public static final int MINIMUM_HEIGHT = 100;
  public static final String DEFAULT_AGENT_SRC =
        "// Reglas para moverse al primer sitio no visitado donde haya un hueco\n"
      + "// Siempre intenta acercarse a la esquina inferior izquierda\n"
      + "DOWN FREE & DOWN ~VISITED => GO DOWN.\n"
      + "RIGHT FREE & RIGHT ~VISITED => GO RIGHT.\n"
      + "LEFT FREE & LEFT ~VISITED => GO LEFT.\n"
      + "UP FREE & UP ~VISITED => GO UP.\n\n"
      + "// Reglas para mover al agente si todo alrededor está visitado u\n"
      + "// ocupado. Utilizamos varias operaciones lógicas para demostrar\n"
      + "// la flexibilidad del lenguaje. Se pueden traducir como:\n"
      + "//     <dirección> FREE -> MOVE <dirección>.\n"
      + "Up Not Wall and Up ~Agent -> MOVE up.\n"
      + "not (left wall OR left agent) -> move left.\n"
      + "right !wall And right !agent -> move right.\n"
      + "!(down wall or down agent) -> move down.\n";

  private SituationActionErrorHandler m_error_handler;
  private ArrayList <SituationActionRule> m_rules;
  private String m_code;
  private boolean[][] m_visited;

  /**
   * @param env Entorno donde se sitúa el agente.
   */
  public SARulesAgent (Environment env) {
    super(env);
    m_error_handler = new SituationActionErrorHandler();
    m_rules = new ArrayList <SituationActionRule>();
    m_code = DEFAULT_AGENT_SRC;
    compileCode();
  }

  /* (non-Javadoc)
   * @see agent.Agent#setEnvironment(Environment)
   */
  public void setEnvironment (Environment env) {
    super.setEnvironment(env);
    Maze maze = m_env.getMaze();
    m_visited = new boolean[maze.getHeight()][maze.getWidth()];
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // Recorremos las reglas y nos quedamos con la primera acción para la
    // que se cumple la situación (arquitectura de subsunción).
    for (SituationActionRule r: m_rules) {
      RuleAction act = r.getAction(this);
      if (act != null)
        return act.getDirection();
    }
    return Direction.NONE;
  }

  /* (non-Javadoc)
   * @see agent.Agent#doMovement(maze.Direction)
   */
  @Override
  public void doMovement (Direction dir) {
    // Marcamos la celda actual como visitada
    m_visited[m_pos.y][m_pos.x] = true;

    // Si nos podemos mover en la dirección que se nos indica, lo hacemos
    if (m_env.movementAllowed(m_pos, dir)) {
      Pair<Integer, Integer> mov = dir.decompose();
      m_pos.x += mov.first;
      m_pos.y += mov.second;
    }
  }

  /* (non-Javadoc)
   * @see agent.Agent#resetMemory()
   */
  public void resetMemory () {
    for (boolean[] i: m_visited)
      for (int j = 0; j < i.length; j++)
        i[j] = false;
  }

  /* (non-Javadoc)
   * @see agent.Agent#getConfigurationPanel()
   */
  @Override
  public JPanel getConfigurationPanel () {
    JPanel config_panel = new JPanel(new BorderLayout());
    JLabel title = new JLabel("Write your rules here:");

    final JTextArea text = new JTextArea(m_code);
    JScrollPane scroll = new JScrollPane(text);

    // Añadimos un popup para cortar, copiar, pegar y seleccionar todo
    final JPopupMenu popup = new JPopupMenu();
    ActionMap actions = text.getActionMap();
    popup.add(actions.get(DefaultEditorKit.cutAction));
    popup.add(actions.get(DefaultEditorKit.copyAction));
    popup.add(actions.get(DefaultEditorKit.pasteAction));
    popup.addSeparator();
    popup.add(actions.get(DefaultEditorKit.selectAllAction));

    text.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
      }

      public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
      }

      private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
          popup.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });

    JPanel buttons = new JPanel(new FlowLayout());
    JButton accept = new JButton("OK");
    JButton cancel = new JButton("Cancel");

    buttons.add(accept);
    buttons.add(cancel);
    config_panel.add(title, BorderLayout.NORTH);
    config_panel.add(scroll, BorderLayout.CENTER);
    config_panel.add(buttons, BorderLayout.SOUTH);

    accept.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        String prev_code = m_code;
        m_code = text.getText();

        if (!compileCode()) {
          String error_msg = "";
          for (String error: m_error_handler.getErrors())
            error_msg += error + "\n";

          MainWindow.getInstance().getConsole().writeError(error_msg);
          m_code = prev_code;
        }
        else {
          MainWindow wnd = MainWindow.getInstance();
          wnd.getConsole().writeInfo("Code compiled successfully.");
          wnd.closeConfigurationPanel();
        }
      }
    });

    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        MainWindow.getInstance().closeConfigurationPanel();
      }
    });

    Border margins = BorderFactory.createEmptyBorder(2, 2, 2, 2);
    Border etched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    config_panel.setBorder(BorderFactory.createCompoundBorder(etched, margins));
    config_panel.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
    return config_panel;
  }

  /**
   * @param dir Dirección en la que hay que mirar.
   * @return Si la celda adyacente en esa dirección ha sido visitada o no.
   */
  public boolean hasVisited (Direction dir) {
    Pair <Integer, Integer> desp = dir.decompose();
    Point p = new Point(m_pos.x + desp.first, m_pos.y + desp.second);
    Maze maze = m_env.getMaze();

    if (p.x < 0 || p.y < 0 || p.x >= maze.getWidth() || p.y >= maze.getHeight())
      return false;

    return m_visited[p.y][p.x];
  }

  /**
   * Convierte el código fuente guardado en m_code en la representación de las
   * reglas de situación-acción.
   * @return true si la compilación fue exitosa y false si no.
   */
  protected boolean compileCode () {
    m_error_handler.resetErrorList();

    ArrayList <SituationActionRule> rules = new ArrayList <SituationActionRule>();
    InputStream stream = new ByteArrayInputStream(m_code.getBytes(StandardCharsets.UTF_8));

    try {
      ANTLRInputStream input = new ANTLRInputStream(stream);
      SituationActionLexer lexer = new SituationActionLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      SituationActionParser parser = new SituationActionParser(tokens);

      lexer.removeErrorListeners();
      parser.removeErrorListeners();
      lexer.addErrorListener(m_error_handler);
      parser.addErrorListener(m_error_handler);

      rules = new ArrayList <SituationActionRule>();
      for (Sa_ruleContext i: parser.program().sa_rule())
        rules.add(SituationActionRule.createFromTree(i));
    }
    catch (Exception e) {
      return false;
    }

    if (m_error_handler.hasErrors())
      return false;
    else {
      m_rules = rules;
      return true;
    }
  }

  /* (non-Javadoc)
   * @see agent.Agent#duplicate()
   */
  @Override
  public Object clone () throws CloneNotSupportedException {
    SARulesAgent ag = new SARulesAgent(m_env);
    ag.m_code = m_code;
    ag.m_pos = (Point) m_pos.clone();
    ag.m_rules = new ArrayList <SituationActionRule>(m_rules.size());
    for (SituationActionRule r: m_rules)
      ag.m_rules.add((SituationActionRule) r.clone());

    return ag;
  }

}
