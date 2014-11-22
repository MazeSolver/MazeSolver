/**
 * @file AgentConfigurationPanel.java
 * @date 22/11/2014
 */
package gui;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Se trata de un panel de configuración de agentes, que permite al usuario
 * configurar un agente dependiendo del tipo que sea.
 *
 * Los controles para aceptar o cancelar la configuración deben ser implementados
 * desde el exterior y utilizarse para llamar a los métodos "accept()" o
 * "cancel", respectivamente.
 */
public abstract class AgentConfigurationPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  protected ArrayList<String> m_errors;
  protected ArrayList<String> m_success;

  /**
   * Construye la interfaz del panel de configuración de agentes.
   */
  public AgentConfigurationPanel () {
    m_errors = new ArrayList<String>();
    m_success = new ArrayList<String>();

    createGUI();
  }

  /**
   * Tras un intento fallido de aceptar la configuración, se puede consultar en
   * esta función cuál o cuáles fueron los errores que impidieron el guardado
   * de la nueva configuración en el agente.
   * @return Una lista con las cadenas que describen al usuario los distintos
   *         errores que han sucedido. Estará vacía si no se llama tras una
   *         salida con error de la función {@link accept()}.
   */
  public ArrayList<String> getErrorMessages () {
    return m_errors;
  }

  public ArrayList<String> getSuccessMessages () {
    return m_success;
  }

  /**
   * Provoca que la configuración actualmente almacenada en el panel de
   * configuración se guarde en el agente, modificando su comportamiento.
   * @return <ul>
   *           <li><b>true</b> si se pudo guardar el resultado.</li>
   *           <li><b>false</b> si la configuración indicada no es válida.</li>
   *         </ul>
   */
  public abstract boolean accept ();

  /**
   * Cancela la operación de configuración, dejando al agente en su estado
   * de partida.
   */
  public abstract void cancel ();

  /**
   * Crea la interfaz gráfica de usuario, que es la que se mostrará al mismo.
   */
  protected abstract void createGUI ();
}
