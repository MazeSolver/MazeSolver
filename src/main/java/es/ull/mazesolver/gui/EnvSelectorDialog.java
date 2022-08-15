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
 * @file EnvSelectorDialog.java
 * @date 2/10/2015
 */
package es.ull.mazesolver.gui;

import com.alee.extended.layout.VerticalFlowLayout;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.gui.environment.EnvironmentSet;
import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.translations.Translations;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Interfaz gráfica para crear o modificar entornos.
 */
public class EnvSelectorDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final int MIN_WINDOW_WIDTH = 300;

    private EnvironmentSet m_envs;
    private Environment m_result;

    private JTextField m_name;
    private JRadioButton m_new_maze, m_open_maze, m_loaded_maze;
    private JComboBox<String> m_loaded_envs;
    private JButton m_ok, m_cancel;

    /**
     * Crea el diálogo de creación de entornos.
     *
     * @param parent Ventana padre del diálogo.
     * @param envs   Conjunto de entornos cargados.
     */
    public EnvSelectorDialog(Window parent, EnvironmentSet envs) {
        this(parent, envs, null);
    }

    /**
     * Crea el diálogo de modificación de entornos.
     *
     * @param parent  Ventana padre del diálogo.
     * @param envs    Conjunto de entornos cargados.
     * @param current Entorno que se quiere modificar.
     */
    public EnvSelectorDialog(Window parent, EnvironmentSet envs, Environment current) {
        super(parent);
        m_envs = envs;
        m_result = current;

        setupInterface();
    }

    /**
     * Muestra el diálogo y devuelve el entorno creado utilizando la
     * configuración elegida por el usuario.
     *
     * @return El entorno creado o modificado o {@code null} si el usuario cancela
     * la creación del entorno.
     */
    public Environment showDialog() {
        setVisible(true);
        return m_result;
    }

    /**
     * Crea la interfaz de usuario y configura los controladores.
     */
    private void setupInterface() {
        buildInterface();
        setupListeners();
        setResizable(false);
        setModal(true);
    }

    /**
     * Crea los elementos y layouts de la interfaz gráfica.
     */
    private void buildInterface() {
        Translations tr = MainWindow.getTranslations();

        // Name
        String name = m_result != null ? m_result.getEnvName() :
                tr.menu().environment() + " " + (Environment.getInstancesCreated() + 1);
        m_name = new JTextField(name);

        JPanel name_panel = new JPanel(new BorderLayout(5, 5));
        name_panel.setBorder(BorderFactory.createEmptyBorder(10, 7, 0, 7));
        name_panel.add(new JLabel(tr.agent().name() + ":"), BorderLayout.WEST);
        name_panel.add(m_name, BorderLayout.CENTER);

        // Maze
        ButtonGroup group = new ButtonGroup();
        m_new_maze = new JRadioButton(tr.env().newMaze());
        m_open_maze = new JRadioButton(tr.env().openMaze());
        m_loaded_maze = new JRadioButton(tr.env().loadedMaze());

        group.add(m_new_maze);
        group.add(m_open_maze);
        group.add(m_loaded_maze);

        // This combo box contains the names of the currently loaded environments
        m_loaded_envs = new JComboBox<String>();
        for (Environment env : m_envs.getEnvironmentList())
            m_loaded_envs.addItem(env.getEnvName());

        JPanel loaded_maze_panel = new JPanel(new BorderLayout());
        loaded_maze_panel.add(m_loaded_maze, BorderLayout.WEST);
        loaded_maze_panel.add(m_loaded_envs, BorderLayout.CENTER);

        JPanel maze_panel = new JPanel(new VerticalFlowLayout(VerticalFlowLayout.CENTER, 0, 3));
        maze_panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 5,
                0, 5), BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), tr.env().maze())));

        maze_panel.add(m_new_maze);
        maze_panel.add(m_open_maze);
        maze_panel.add(loaded_maze_panel);

        if (m_envs.getEnvironmentCount() == 0)
            m_loaded_maze.setEnabled(false);

        // Buttons
        m_ok = new JButton(tr.button().ok());
        m_cancel = new JButton(tr.button().cancel());

        JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        button_panel.add(m_ok);
        button_panel.add(m_cancel);

        setTitle(m_result == null ? tr.menu().newEnv() : tr.menu().configEnv());
        setLayout(new BorderLayout(5, 5));
        setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, 0));

        add(name_panel, BorderLayout.NORTH);
        add(maze_panel, BorderLayout.CENTER);
        add(button_panel, BorderLayout.SOUTH);

        if (m_result == null) {
            m_new_maze.setSelected(true);
            m_loaded_envs.setEnabled(false);
        } else {
            m_loaded_maze.setSelected(true);
            m_loaded_envs.setSelectedItem(m_result.getEnvName());
        }

        pack();
    }

    /**
     * Configura los controladores de la interfaz gráfica.
     */
    private void setupListeners() {
        final Translations tr = MainWindow.getTranslations();

        ActionListener radio_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_loaded_envs.setEnabled(e.getSource() == m_loaded_maze);
            }
        };

        m_new_maze.addActionListener(radio_listener);
        m_open_maze.addActionListener(radio_listener);
        m_loaded_maze.addActionListener(radio_listener);

        m_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Comprobamos que el nombre del entorno no está repetido ni vacío
                String name = m_name.getText();
                boolean valid_name = !name.isEmpty();

                if (valid_name) {
                    for (int i = 0; i < m_loaded_envs.getItemCount(); ++i) {
                        if (m_loaded_envs.getItemAt(i).equals(name)) {
                            if (m_envs.getEnvironmentList().get(i) != m_result)
                                valid_name = false;
                            break;
                        }
                    }
                }

                if (!valid_name) {
                    JOptionPane.showMessageDialog(null, name.isEmpty() ?
                            tr.exception().unnamedEnv() : tr.exception().repeatedEnvName());
                    return;
                }

                // Creamos el entorno resultante de manera distinta dependiendo del
                // origen del laberinto
                boolean exit_dialog = true;
                EnvSelectorDialog that = EnvSelectorDialog.this;
                m_result = null;

                // Crear laberinto aleatorio
                if (m_new_maze.isSelected()) {
                    MazeSelectorDialog dialog = new MazeSelectorDialog(that);
                    dialog.setLocationRelativeTo(that);
                    Maze generated = dialog.showDialog();

                    if (generated != null)
                        m_result = new Environment(generated, name);
                    else
                        exit_dialog = false;
                }
                // Abrir laberinto desde un fichero
                else if (m_open_maze.isSelected()) {
                    try {
                        Maze maze = FileDialog.loadMaze();

                        if (maze != null)
                            m_result = new Environment(maze, name);
                        else
                            exit_dialog = false;
                    } catch (IOException exc) {
                        JOptionPane.showMessageDialog(null, exc.getMessage(),
                                tr.message().fileOpenFailed(),
                                JOptionPane.ERROR_MESSAGE);
                        exit_dialog = false;
                    }
                }
                // Utilizar laberinto contenido en otro entorno
                else /* m_loaded_maze.isSelected() */ {
                    String env_name = (String) m_loaded_envs.getSelectedItem();
                    for (Environment env : m_envs.getEnvironmentList()) {
                        if (env.getEnvName().equals(env_name)) {
                            m_result = new Environment(env.getMaze(), name);
                            break;
                        }
                    }
                }

                if (exit_dialog) {
                    setVisible(false);
                    dispose();
                }
            }
        });

        m_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

    }

}
