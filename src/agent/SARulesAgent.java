/**
 * @file SARulesAgent.java
 * @date 2/11/2014
 */
package agent;

import gui.MainWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import maze.Direction;
import maze.Maze;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import util.Pair;
import agent.rules.RuleAction;
import agent.rules.SituationActionRule;
import agent.rules.parser.SituationActionLexer;
import agent.rules.parser.SituationActionParser;
import agent.rules.parser.SituationActionParser.Sa_ruleContext;

/**
 * Agente cuya lógica se basa en reglas de situación-acción. Sigue una
 * arquitectura de subsunción, donde se aplica la regla de mayor precedencia
 * para la cual la situación se cumple.
 */
public class SARulesAgent extends Agent {
  public static final int MINIMUM_WIDTH = 200;
  public static final int MINIMUM_HEIGHT = 400;
  public static final String DEFAULT_AGENT_SRC =
        "UP FREE -> MOVE UP.\n"
      + "LEFT NOT VISITED & LEFT FREE -> MOVE LEFT.\n"
      + "LEFT FREE | DOWN WALL => STOP.\n"
      + "DOWN VISITED & RIGHT FREE => MOVE RIGHT.\n";

  private ArrayList <SituationActionRule> m_rules;
  private String m_code;

  /**
   * @param maze Laberinto donde se sitúa el agente.
   */
  public SARulesAgent (Maze maze) {
    super(maze);
    m_rules = new ArrayList <SituationActionRule>();
    m_code = DEFAULT_AGENT_SRC;
    compileCode();
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // Recorremos las reglas y nos quedamos con la primera acción para la
    // que se cumple la situación.
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
    if (!m_maze.get(m_pos.x, m_pos.y).hasWall(dir)) {
      Pair<Integer, Integer> mov = Direction.decompose(dir);
      m_pos.x += mov.first;
      m_pos.y += mov.second;
    }
  }

  /* (non-Javadoc)
   * @see agent.Agent#getConfigurationPanel()
   */
  @Override
  public JPanel getConfigurationPanel () {
    JPanel config_panel = new JPanel(new BorderLayout());
    JLabel title = new JLabel("Write your rules here:");
    final JTextArea text = new JTextArea(m_code);

    JPanel buttons = new JPanel(new FlowLayout());
    JButton accept = new JButton("OK");
    JButton cancel = new JButton("Cancel");

    buttons.add(accept);
    buttons.add(cancel);
    config_panel.add(title, BorderLayout.NORTH);
    config_panel.add(text, BorderLayout.CENTER);
    config_panel.add(buttons, BorderLayout.SOUTH);

    accept.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        String prev_code = m_code;
        m_code = text.getText();
        if (!compileCode()) {
          JOptionPane.showMessageDialog(null, "Code couldn't be compiled",
              "Compilation error", JOptionPane.ERROR_MESSAGE);
          m_code = prev_code;
        }
        else
          MainWindow.getInstance().closeConfigurationPanel();
      }
    });

    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        MainWindow.getInstance().closeConfigurationPanel();
      }
    });

    config_panel.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
    return config_panel;
  }

  /**
   * Convierte el código fuente guardado en m_code en la representación de las
   * reglas de situación-acción.
   * @return true si la compilación fue exitosa y false si no.
   */
  protected boolean compileCode () {
    InputStream stream = new ByteArrayInputStream(m_code.getBytes(StandardCharsets.UTF_8));

    try {
      ANTLRInputStream input = new ANTLRInputStream(stream);
      SituationActionLexer lexer = new SituationActionLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      SituationActionParser parser = new SituationActionParser(tokens);

      ArrayList <SituationActionRule> rules = new ArrayList <SituationActionRule>();
      for (Sa_ruleContext i: parser.program().sa_rule())
        rules.add(SituationActionRule.createFromTree(i));

      // TODO Sólo si la compilación fue correcta
      m_rules = rules;
    }
    catch (Exception e) {
      return false;
    }

    return true;
  }

  /* (non-Javadoc)
   * @see agent.Agent#duplicate()
   */
  @Override
  public Object clone () throws CloneNotSupportedException {
    SARulesAgent ag = new SARulesAgent(m_maze);
    ag.m_code = m_code;
    ag.m_pos = (Point) m_pos.clone();
    ag.m_rules = new ArrayList <SituationActionRule>(m_rules.size());
    for (SituationActionRule r: m_rules)
      ag.m_rules.add((SituationActionRule) r.clone());

    return ag;
  }

}
