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
 * @file LoggingConsole.java
 * @date 14/11/2014
 */
package es.ull.mazesolver.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.alee.laf.button.WebButton;

/**
 * Clase que representa un panel de logging donde se pueden mostrar errores,
 * información, etc. al usuario de manera permanente.
 */
public class LoggingConsole extends JPanel {
  private static final long serialVersionUID = 1L;

  private JButton m_clear_button;
  private JButton m_save_button;
  private JTextPane m_text;

  private Style m_error_style;
  private Style m_info_style;
  private Style m_warning_style;

  /**
   * Construye una instancia de consola.
   */
  public LoggingConsole () {
    m_clear_button = new JButton("Clear");
    m_clear_button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        clear();
      }
    });

    m_save_button = new JButton("Save to file...");
    m_save_button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        try {
          FileDialog.saveLog(m_text.getText());
        }
        catch (IOException e1) {
          JOptionPane.showMessageDialog(null, e1.getMessage(), "Log save failed",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    m_text = new JTextPane();
    m_text.setEditable(false);
    m_text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

    m_error_style = m_text.addStyle("Error", null);
    m_info_style = m_text.addStyle("Info", null);
    m_warning_style = m_text.addStyle("Warning", null);

    StyleConstants.setForeground(m_error_style, Color.RED);
    StyleConstants.setForeground(m_info_style, Color.BLACK);
    StyleConstants.setForeground(m_warning_style, Color.YELLOW);

    final JScrollPane text_scroll = new JScrollPane(m_text);
    text_scroll.setVisible(false);

    JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
    top.add(m_clear_button);
    top.add(m_save_button);

    final WebButton collapser = new WebButton();
    collapser.setUndecorated(true);
    collapser.setIcon(UIManager.getIcon("Tree.collapsedIcon"));

    collapser.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        if (text_scroll.isVisible())
          collapser.setIcon(UIManager.getIcon("Tree.collapsedIcon"));
        else
          collapser.setIcon(UIManager.getIcon("Tree.expandedIcon"));

        text_scroll.setVisible(!text_scroll.isVisible());
        firePropertyChange("ConsoleDisplay", !text_scroll.isVisible(), text_scroll.isVisible());
      }
    });

    top.add(Box.createGlue());
    top.add(collapser);

    setLayout(new BorderLayout());
    add(top, BorderLayout.NORTH);
    add(text_scroll, BorderLayout.CENTER);
    setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Log"));
  }

  /**
   * Muestra en la consola un mensaje de información.
   *
   * @param info
   *          Mensaje que se desea mostrar.
   */
  public void writeInfo (String info) {
    StyledDocument doc = m_text.getStyledDocument();
    try {
      doc.insertString(doc.getLength(), info + "\n", m_info_style);
    }
    catch (BadLocationException e) {
    }
  }

  /**
   * Muestra en la consola un mensaje de advertencia.
   *
   * @param warning
   *          Mensaje que se desea mostrar.
   */
  public void writeWarning (String warning) {
    StyledDocument doc = m_text.getStyledDocument();
    try {
      doc.insertString(doc.getLength(), warning + "\n", m_warning_style);
    }
    catch (BadLocationException e) {
    }
  }

  /**
   * Muestra en la consola un mensaje de error.
   *
   * @param error
   *          Mensaje que se desea mostrar.
   */
  public void writeError (String error) {
    StyledDocument doc = m_text.getStyledDocument();
    try {
      doc.insertString(doc.getLength(), error + "\n", m_error_style);
    }
    catch (BadLocationException e) {
    }
  }

  /**
   * Elimina el texto de la consola.
   */
  public void clear () {
    m_text.setText("");
  }

}
