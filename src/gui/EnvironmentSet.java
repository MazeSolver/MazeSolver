/**
 * @file EnvironmentSet.java
 * @date 25/10/2014
 */
package gui;

import java.util.ArrayList;

import javax.swing.JDesktopPane;

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

}
