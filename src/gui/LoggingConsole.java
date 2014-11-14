/**
 * @file LoggingConsole.java
 * @date 14/11/2014
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Clase que representa un panel de logging donde se pueden mostrar errores,
 * información, etc. al usuario de manera permanente.
 */
public class LoggingConsole extends JPanel {
  private static final long serialVersionUID = 1L;

  private JButton m_clear_button;
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

    m_text = new JTextPane();
    m_text.setEditable(false);
    m_text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

    m_error_style = m_text.addStyle("Error", null);
    m_info_style = m_text.addStyle("Info", null);
    m_warning_style = m_text.addStyle("Warning", null);

    StyleConstants.setForeground(m_error_style, Color.RED);
    StyleConstants.setForeground(m_info_style, Color.BLACK);
    StyleConstants.setForeground(m_warning_style, Color.YELLOW);

    JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
    top.add(m_clear_button);

    setLayout(new BorderLayout());
    add(top, BorderLayout.NORTH);
    add(new JScrollPane(m_text), BorderLayout.CENTER);
  }

  /**
   * Muestra en la consola un mensaje de información.
   * @param info Mensaje que se desea mostrar.
   */
  public void writeInfo (String info) {
    StyledDocument doc = m_text.getStyledDocument();
    try {
      doc.insertString(doc.getLength(), info + "\n", m_info_style);
    }
    catch (BadLocationException e) {}
  }

  /**
   * Muestra en la consola un mensaje de advertencia.
   * @param warning Mensaje que se desea mostrar.
   */
  public void writeWarning (String warning) {
    StyledDocument doc = m_text.getStyledDocument();
    try {
      doc.insertString(doc.getLength(), warning + "\n", m_warning_style);
    }
    catch (BadLocationException e) {}
  }

  /**
   * Muestra en la consola un mensaje de error.
   * @param error Mensaje que se desea mostrar.
   */
  public void writeError (String error) {
    StyledDocument doc = m_text.getStyledDocument();
    try {
      doc.insertString(doc.getLength(), error + "\n", m_error_style);
    }
    catch (BadLocationException e) {}
  }

  /**
   * Elimina el texto de la consola.
   */
  public void clear () {
    m_text.setText("");
  }

}
