/**
 * @file EnvironmentSet.java
 * @date 25/10/2014
 */
package gui.environment;

import java.util.ArrayList;

import javax.swing.JDesktopPane;

import agent.Agent;

/**
 * Panel principal en el que se encuentran todos los laberintos cargados.
 * Proporciona la gestión de los laberintos.
 */
public class EnvironmentSet extends JDesktopPane {
  private static final long serialVersionUID = 1L;

  private ArrayList <Environment> m_envs;

  /**
   * Constructor. Inicializa la instancia.
   */
  public EnvironmentSet () {
    setVisible(true);
    setOpaque(true);

    m_envs = new ArrayList <Environment>();
  }

  /**
   * @return Entorno seleccionado actualmente o null si no hay ninguno.
   */
  public Environment getSelectedEnvironment () {
    return (Environment) getSelectedFrame();
  }

  /**
   * No se deben eliminar los entornos de la lista, o tendrá un comportamiento
   * erróneo posteriormente.
   * @return Lista de entornos cargados actualmente.
   */
  public final ArrayList<Environment> getEnvironmentList () {
    return m_envs;
  }

  /**
   * @param env Añade un entorno a la lista.
   */
  public void addEnvironment (Environment env) {
    if (env != null) {
      m_envs.add(env);
      add(env);
    }
  }

  /**
   * Elimina el entorno seleccionado actualmente si hay alguno seleccionado.
   */
  public void removeSelectedEnvironment () {
    Environment env = getSelectedEnvironment();
    if (env != null) {
      m_envs.remove(env);
      remove(env);
    }
  }

  /**
   * Añade un agente al entorno seleccionado y actualiza la referencia al
   * entorno por si se trataba de un entorno simple y tras la adición de un
   * agente pasa a ser un entorno múltiple.
   * @param ag Agente que se quiere añadir al entorno actual.
   */
  public void addAgentToSelectedEnvironment (Agent ag) {
    Environment env = getSelectedEnvironment();
    if (env != null) {
      m_envs.remove(env);
      remove(env);
      Environment new_env = env.addAgent(ag);
      m_envs.add(new_env);
      add(new_env);
    }
    else
      throw new IllegalStateException("El usuario no ha seleccionado ningún entorno");
  }

  /**
   * Elimina un agente de un entorno y actualiza la referencia al entorno, por
   * si era un entorno múltiple y tras la eliminación tan sólo queda un agente.
   * @param ag Referencia al agente que se quiere eliminar del entorno.
   * @param env Entorno del cual se quiere eliminar el agente.
   */
  public void removeAgentFromEnvironment (Agent ag, Environment env) {
    if (m_envs.contains(env)) {
      m_envs.remove(env);
      remove(env);
      Environment new_env = env.removeAgent(ag);
      m_envs.add(new_env);
      add(new_env);
    }
    else
      throw new IllegalArgumentException("El entorno no está guardado en el conjunto actual");
  }

  /**
   * Actualiza el zoom de los entornos y sus tamaños.
   * @param zoom Nivel de escala a aplicar a la visualización de los entornos.
   */
  public void setZoom (double zoom) {
    EnvironmentPanel.setZoom(zoom);
    for (Environment i: m_envs)
      i.updateSize();
  }

}
