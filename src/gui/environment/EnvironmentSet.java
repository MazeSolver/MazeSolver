/**
 * @file EnvironmentSet.java
 * @date 25/10/2014
 */
package gui.environment;

import java.util.ArrayList;

import agent.Agent;

import com.tomtessier.scrollabledesktop.JScrollableDesktopPane;

/**
 * Panel principal en el que se encuentran todos los laberintos cargados.
 * Proporciona la gestión de los laberintos.
 */
public class EnvironmentSet extends JScrollableDesktopPane {
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
   * @return Número de entornos en el conjunto.
   */
  public int getEnvironmentCount () {
    return m_envs.size();
  }

  /**
   * @param env Añade un entorno a la lista.
   */
  public void addEnvironment (Environment env) {
    if (env != null) {
      m_envs.add(env);
      add(env);
      repaint();
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
      repaint();
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
      Environment new_env = env.addAgent(ag);
      exchangeEnvironments(env, new_env);
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
      Environment new_env = env.removeAgent(ag);
      exchangeEnvironments(env, new_env);
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

  /**
   * Intercambia un entorno dentro del conjunto de entornos por otro que no
   * está en dicho conjunto.
   * @param e1 Entorno que se quiere eliminar del conjunto.
   * @param e2 Entorno que se quiere introducir en su lugar.
   */
  public void exchangeEnvironments (Environment e1, Environment e2) {
    // Evitamos intercambiar un entorno por sí mismo
    if (e1 != e2) {
      if (!m_envs.contains(e1) || m_envs.contains(e2))
        throw new IllegalArgumentException("No se pueden intercambiar estos entornos");

      // Quitamos el primer entorno
      m_envs.remove(e1);
      remove(e1);
      // Ponemos el segundo entorno
      m_envs.add(e2);
      add(e2);
      // Lo seleccionamos como entorno activo
      e2.setLocation(e1.getLocation());
      setSelectedFrame(e2);
    }
    repaint();
  }

}
