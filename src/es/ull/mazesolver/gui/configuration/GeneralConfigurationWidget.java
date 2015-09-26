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
 * @file GeneralConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.alee.extended.colorchooser.WebColorChooserField;

import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.translations.AgentSelectorTranslations;
import es.ull.mazesolver.translations.Translatable;

/**
 * Panel de configuración con los atributos comunes a todos los agentes.
 */
public class GeneralConfigurationWidget extends JPanel implements Translatable {
  private static final long serialVersionUID = 1L;

  private JLabel m_name_text, m_color_text;
  private JTextField m_name;
  private WebColorChooserField m_color;

  /**
   * Crea el widget de entrada para los atributos comunes a todos los agentes.
   */
  public GeneralConfigurationWidget () {
    m_name_text = new JLabel();
    m_color_text = new JLabel();
    m_name = new JTextField();

    m_color = new WebColorChooserField();
    m_color.setPipetteEnabled(false);

    JPanel label_panel = new JPanel(new GridLayout(2, 1));
    label_panel.add(m_name_text);
    label_panel.add(m_color_text);

    JPanel content_panel = new JPanel(new GridLayout(2, 1));
    content_panel.add(m_name);
    content_panel.add(m_color);

    setLayout(new BorderLayout(5, 0));
    add(label_panel, BorderLayout.WEST);
    add(content_panel, BorderLayout.CENTER);
  }

  /**
   * Cambia el color actual seleccionado.
   * @param color Nuevo color.
   */
  public void setAgentColor (Color color) {
    if (color != null)
      m_color.setColor(color);
  }

  /**
   * @return El color seleccionado en el panel.
   */
  public Color getAgentColor () {
    return m_color.getColor();
  }

  /**
   * Cambia el nombre visualizado.
   * @param name Nuevo nombre.
   */
  public void setAgentName (String name) {
    m_name.setText(name);
  }

  /**
   * @return El nombre escrito en el campo correspondiente.
   */
  public String getAgentName () {
    return m_name.getText();
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.translations.Translatable#translate()
   */
  @Override
  public void translate () {
    AgentSelectorTranslations tr = MainWindow.getTranslations().agent();
    m_name_text.setText(tr.name() + ":");
    m_color_text.setText(tr.color() + ":");
  }

}
