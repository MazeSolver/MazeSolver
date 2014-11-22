/**
 * @file AgentConfigurationPanel.java
 * @date 22/11/2014
 */
package gui;

import java.util.ArrayList;

import javax.swing.JPanel;

import agent.Agent;

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

  protected Agent m_agent;

  /**
   * Construye el panel de configuración de agentes y lo enlaza permanentemente
   * a un agente, que es el que se va a configurar.
   * @param agent Agente que se quiere configurar.
   */
  public AgentConfigurationPanel (Agent agent) {
    if (agent == null)
      throw new IllegalArgumentException("El agente especificado es inválido");

    m_agent = agent;
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
   * Tras un intento fallido de aceptar la configuración, se puede consultar en
   * esta función cuál o cuáles fueron los errores que impidieron el guardado
   * de la nueva configuración en el agente.
   * @return Una lista con las cadenas que describen al usuario los distintos
   *         errores que han sucedido. Estará vacía si no se llama tras una
   *         salida con error de la función {@link accept()}.
   */
  public abstract ArrayList<String> getErrors ();
}
