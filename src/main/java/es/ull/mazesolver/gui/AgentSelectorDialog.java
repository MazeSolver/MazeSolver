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
 * @file AgentSelectorDialog.java
 * @date 15/11/2014
 */
package es.ull.mazesolver.gui;

import es.ull.mazesolver.agent.AStarAgent;
import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.agent.DStarAgent;
import es.ull.mazesolver.agent.HillClimbAgent;
import es.ull.mazesolver.agent.PATableAgent;
import es.ull.mazesolver.agent.RecursiveAgent;
import es.ull.mazesolver.agent.SARulesAgent;
import es.ull.mazesolver.agent.SimulatedAnnealingAgent;
import es.ull.mazesolver.agent.WallFollowerAgent;
import es.ull.mazesolver.gui.configuration.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.translations.Translations;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Interfaz gráfica para permitir al usuario elegir qué agente añadir al
 * entorno.
 */
public class AgentSelectorDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private static final TreeMap<String, Class<? extends Agent>> ALGORITHMS = new TreeMap<>();

    static {
        ALGORITHMS.put("Perception-Action Tables", PATableAgent.class);
        ALGORITHMS.put("Situation-Action Rules", SARulesAgent.class);
        ALGORITHMS.put("A*", AStarAgent.class);
        ALGORITHMS.put("D*", DStarAgent.class);
        ALGORITHMS.put("Hill Climbing", HillClimbAgent.class);
        ALGORITHMS.put("Simulated Annealing", SimulatedAnnealingAgent.class);
        ALGORITHMS.put("Recursive Backtracking", RecursiveAgent.class);
        ALGORITHMS.put("Wall Follower", WallFollowerAgent.class);
    }

    private int max_agents;
    private JComboBox<String> m_agents;
    private JSpinner m_amount;

    private Agent[] m_result;
    private Agent m_template_agent = null;

    /**
     * Crea el diálogo de creación de agentes.
     *
     * @param parent          Ventana padre del diálogo.
     * @param available_cells Número de celdas vacías actualmente en el laberinto.
     */
    public AgentSelectorDialog(Window parent, int available_cells) {
        super(parent);
        max_agents = available_cells - 1;

        buildInterface();
        setResizable(false);
        setModal(true);
    }

    /**
     * Muestra el diálogo por pantalla y devuelve la lista de agentes que se deben
     * crear como consecuencia de la selección del usuario.
     *
     * @return Lista de agentes que se quieren añadir al entorno o null si no se
     * quiere añadir ninguno.
     */
    public Agent[] showDialog() {
        setVisible(true);
        return m_result;
    }

    /**
     * Construye la interfaz gráfica y configura los listeners.
     */
    private void buildInterface() {
        final Translations tr = MainWindow.getTranslations();

        setTitle(tr.agent().createNewAgent());
        setLayout(new BorderLayout());

        JLabel ags = new JLabel(tr.agent().algorithm() + ":");
        JLabel amo = new JLabel(tr.agent().amount() + ":");

        JPanel global = new JPanel(new BorderLayout(5, 5));
        JPanel labels = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel controls = new JPanel(new GridLayout(2, 1, 5, 5));

        m_agents = new JComboBox<String>(ALGORITHMS.keySet().toArray(new String[ALGORITHMS.size()]));
        m_amount = new JSpinner(new SpinnerNumberModel(1, 1, max_agents, 1));

        labels.add(ags);
        controls.add(m_agents);
        labels.add(amo);
        controls.add(m_amount);

        global.add(labels, BorderLayout.WEST);
        global.add(controls, BorderLayout.CENTER);
        global.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton ok = new JButton(tr.button().ok());
        JButton cancel = new JButton(tr.button().cancel());
        JButton config = new JButton(tr.button().configure() + "...");

        JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button_panel.add(ok);
        button_panel.add(cancel);
        button_panel.add(config);

        add(global, BorderLayout.CENTER);
        add(button_panel, BorderLayout.SOUTH);
        pack();

        // Cuando se cambia la clase del combobox hay que actualizar el agente
        // plantilla
        m_agents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_template_agent = createSelectedAgent();
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int amount = (Integer) m_amount.getValue();

                if (m_template_agent == null)
                    m_template_agent = createSelectedAgent();

                m_result = new Agent[amount];
                m_result[0] = m_template_agent;

                // Clonamos el agente en todas las posiciones (todos serán iguales)
                for (int i = 1; i < amount; i++) {
                    m_result[i] = (Agent) m_result[0].clone();
                }

                setVisible(false);
                dispose();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (m_template_agent == null)
                    m_template_agent = createSelectedAgent();

                final AgentConfigurationPanel config_panel = m_template_agent.getConfigurationPanel();
                final JDialog d = new JDialog(AgentSelectorDialog.this,
                        tr.message().configureAgent(), true);
                d.setLocationRelativeTo(AgentSelectorDialog.this);
                d.add(config_panel);
                d.setResizable(false);

                config_panel.addEventListener(new AgentConfigurationPanel.EventListener() {
                    @Override
                    public void onSuccess(ArrayList<String> msgs) {
                        String all_msgs = "";
                        for (String msg : msgs)
                            all_msgs += msg + "\n";

                        if (!all_msgs.isEmpty())
                            JOptionPane.showMessageDialog(null, all_msgs,
                                    tr.message().operationSucceeded(),
                                    JOptionPane.INFORMATION_MESSAGE);

                        d.dispose();
                    }

                    @Override
                    public void onCancel() {
                        d.dispose();
                    }

                    @Override
                    public void onError(ArrayList<String> errors) {
                        String all_errors = "";
                        for (String error : errors)
                            all_errors += error + "\n";

                        if (!all_errors.isEmpty())
                            JOptionPane.showMessageDialog(null, all_errors,
                                    tr.message().operationFailed(), JOptionPane.ERROR_MESSAGE);
                    }
                });

                d.pack();
                d.setVisible(true);
            }
        });
    }

    /**
     * Crea una instancia del agente seleccionado actualmente en el ComboBox.
     * Para que sea posible el agente tiene que tener un constructor que acepte
     * exactamente un parámetro de tipo {@link es.ull.mazesolver.gui.environment.Environment}.
     *
     * @return Instancia del agente seleccionado.
     */
    private Agent createSelectedAgent() {
        Environment env = MainWindow.getInstance().getEnvironments().getSelectedEnvironment();
        String name = (String) m_agents.getSelectedItem();
        try {
            Agent ag = ALGORITHMS.get(name).getConstructor(env.getClass()).newInstance(env);
            return ag;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                 | InvocationTargetException | NoSuchMethodException | SecurityException e2) {
            e2.printStackTrace();
            return null;
        }
    }

}
