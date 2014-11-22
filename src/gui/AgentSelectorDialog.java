/**
 * @file AgentSelectorDialog.java
 * @date 15/11/2014
 */
package gui;

import gui.environment.Environment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import agent.Agent;
import agent.PATableAgent;
import agent.SARulesAgent;

/**
 * Interfaz gráfica para permitir al usuario elegir qué agente añadir al
 * entorno.
 */
public class AgentSelectorDialog extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final int MAX_AGENTS_AMOUNT = 10;
  private static final TreeMap <String, Class<? extends Agent>> ALGORITHMS = new TreeMap<>();
  static {
    ALGORITHMS.put("Perception-Action Tables", PATableAgent.class);
    ALGORITHMS.put("Situation-Action Rules", SARulesAgent.class);
    ALGORITHMS.put("Logical (Prolog)", null);
    ALGORITHMS.put("A*", null);
    ALGORITHMS.put("RTA*", null);
    ALGORITHMS.put("Hill Climbing", null);
    ALGORITHMS.put("Simulated Annealing", null);
  }

  private JComboBox<String> m_agents;
  private JSpinner m_amount;

  private Agent[] m_result;

  public AgentSelectorDialog (Window parent) {
    super(parent,"Create a new agent");

    m_agents = new JComboBox<String>(ALGORITHMS.keySet().toArray(new String[ALGORITHMS.size()]));
    m_amount = new JSpinner(new SpinnerNumberModel(1, 1, MAX_AGENTS_AMOUNT, 1));

    buildInterface();
    setResizable(false);
    setModal(true);
  }

  public Agent[] showDialog () {
    setVisible(true);
    return m_result;
  }

  private void buildInterface () {
    setLayout(new BorderLayout());

    JLabel ags = new JLabel("Algorithm:");
    JLabel amo = new JLabel("Amount:");

    JPanel global = new JPanel(new BorderLayout(5, 5));
    JPanel labels = new JPanel(new GridLayout(2, 1, 5, 5));
    JPanel controls = new JPanel(new GridLayout(2, 1, 5, 5));

    labels.add(ags);
    controls.add(m_agents);
    labels.add(amo);
    controls.add(m_amount);

    global.add(labels, BorderLayout.WEST);
    global.add(controls, BorderLayout.CENTER);
    global.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JButton ok = new JButton("OK");
    JButton cancel = new JButton("Cancel");

    JPanel button_panel = new JPanel(new FlowLayout());
    button_panel.add(ok);
    button_panel.add(cancel);

    add(global, BorderLayout.CENTER);
    add(button_panel, BorderLayout.SOUTH);
    pack();

    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        Environment env = MainWindow.getInstance().getEnvironments().getSelectedEnvironment();
        String ag_name = (String) m_agents.getSelectedItem();
        int amount = (Integer) m_amount.getValue();
        m_result = new Agent[amount];

        try {
          m_result[0] = ALGORITHMS.get(ag_name).getConstructor(env.getClass()).newInstance(env);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e2) {
          e2.printStackTrace();
          return;
        }

        // Clonamos el agente en todas las posiciones (todos serán iguales)
        for (int i = 1; i < amount; i++) {
          try {
            m_result[i] = (Agent) m_result[0].clone();
          }
          catch (CloneNotSupportedException e1) {}
        }

        setVisible(false);
        dispose();
      }
    });

    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        setVisible(false);
        dispose();
      }
    });
  }

}
