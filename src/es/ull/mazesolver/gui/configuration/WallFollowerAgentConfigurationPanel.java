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
 * @file WallFollowerAgentConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.list.WebListCellRenderer;

import es.ull.mazesolver.agent.WallFollowerAgent;
import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.translations.EnumTranslations;
import es.ull.mazesolver.util.Rotation;

/**
 * Panel de configuración de agentes {@link WallFollowerAgent}.
 */
public class WallFollowerAgentConfigurationPanel extends SimpleAgentConfigurationPanel {
  private static final long serialVersionUID = 1L;

  private JComboBox <Rotation> m_wall;
  private JLabel wall_text;

  /**
   * Crea el panel de configuración para el agente indicado.
   *
   * @param agent
   *          Agente que se quiere configurar.
   */
  public WallFollowerAgentConfigurationPanel (WallFollowerAgent agent) {
    super(agent);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#createGUI(javax.swing.JPanel)
   */
  @Override
  protected void createGUI (JPanel root) {
    super.createGUI(root);

    WallFollowerAgent agent = (WallFollowerAgent) m_agent;
    wall_text = new JLabel();

    m_wall = new JComboBox <Rotation>(Rotation.values());
    m_wall.setSelectedItem(agent.getRotation());
    m_wall.setRenderer(new RotationRenderer());

    JPanel global = new JPanel(new VerticalFlowLayout());
    global.add(wall_text);
    global.add(m_wall);
    global.setBorder(BorderFactory.createTitledBorder("Wall Follower"));

    root.add(global);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#accept()
   */
  @Override
  protected boolean accept () {
    if (super.accept()) {
      WallFollowerAgent agent = (WallFollowerAgent) m_agent;
      agent.setRotation((Rotation) m_wall.getSelectedItem());
      return true;
    }

    return false;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#translate()
   */
  @Override
  public void translate () {
    super.translate();
    wall_text.setText(MainWindow.getTranslations().agent().wallToFollow() + ":");
  }

  /**
   * Clase que permite mostrar un nombre personalizado para las rotaciones en el
   * contexto de seguir paredes.
   */
  private class RotationRenderer extends WebListCellRenderer {
    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     *
     * @see
     * javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax
     * .swing.JList, java.lang.Object, int, boolean, boolean)
     */
    @SuppressWarnings ("rawtypes")
    @Override
    public Component getListCellRendererComponent (JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      EnumTranslations tr = MainWindow.getTranslations().enums();
      switch ((Rotation) value) {
        case CLOCKWISE:
          value = tr.rightWall();
          break;
        case COUNTER_CLOCKWISE:
          value = tr.leftWall();
          break;
      }

      return super.getListCellRendererComponent(list, value, index, isSelected,
                                                cellHasFocus);
    }

  }

}
