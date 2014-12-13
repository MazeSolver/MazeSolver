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
 * @file WallFollowerAgent.java
 * @date 13/12/2014
 */
package es.ull.mazesolver.agent;

import java.awt.Component;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import es.ull.mazesolver.gui.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.util.Direction;
import es.ull.mazesolver.util.Rotation;

/**
 * <p>
 *   Agente que implementa la funcionalidad de seguir paredes. Una vez se le
 *   especifica una dirección (derecha o izquierda) el agente recorre el
 *   laberinto como si pegara la mano del lado indicado en la pared y
 *   simplemente la siguiera a lo largo de los giros necesarios hasta encontrar
 *   la salida.
 * </p>
 * <p>
 *   Este algoritmo garantiza encontrar la salida del laberinto sólo si se
 *   trata de un laberinto perfecto, aunque el número de pasos que requerirá
 *   será mucho mayor que el de la mayoría de agentes.
 * </p>
 */
public class WallFollowerAgent extends Agent {
  private static final long serialVersionUID = -2234924006984636419L;

  private transient Direction m_last_dir;
  private Rotation m_rot;

  /**
   * Crea el agente en el entorno indicado.
   * @param env Entorno en el que colocar el agente.
   */
  public WallFollowerAgent (Environment env) {
    super(env);
    m_last_dir = Direction.RIGHT;
    m_rot = Rotation.CW;
  }

  /**
   * Indica hacia qué lado girar primero para seguir la pared.
   * @param rot Determina la pared que seguirá el agente.
   * <ul>
   *   <li>{@code Rotation.CLOCKWISE}: Sigue la pared a su derecha.</li>
   *   <li>{@code Rotation.COUNTER_CLOCKWISE}: Sigue la pared a su izquierda.</li>
   * </ul>
   */
  public void setRotation (Rotation rot) {
    m_rot = rot;
  }

  /**
   * @return La pared que el agente sigue.
   */
  public Rotation getRotation () {
    return m_rot;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "Wall Follower";
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // Primero intentamos girar al lado que estamos siguiendo. Si está libre,
    // nos movemos en esa dirección
    Direction new_dir = m_last_dir.rotate(m_rot);
    if (m_env.movementAllowed(m_pos, new_dir)) {
      m_last_dir = new_dir;
      return new_dir;
    }
    // Si no podemos, nos movemos en la dirección contraria hasta encontrar un
    // espacio o dar un giro completo
    else {
      // Restamos la dirección que acabamos de ver y Direction.NONE del total
      // de direcciones posibles
      for (int i = 0; i < Direction.MAX_DIRECTIONS - 2; i++) {
        new_dir = new_dir.rotate(m_rot.getOpposite());
        if (m_env.movementAllowed(m_pos, new_dir)) {
          m_last_dir = new_dir;
          return new_dir;
        }
      }
    }
    // Si no encontramos ninguna posibilidad de movimiento, no nos movemos
    return Direction.NONE;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    m_last_dir = Direction.RIGHT;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#getConfigurationPanel()
   */
  @Override
  public AgentConfigurationPanel getConfigurationPanel () {
    return new AgentConfigurationPanel() {
      private static final long serialVersionUID = 1L;

      private JComboBox <Rotation> m_wall;

      @Override
      protected void createGUI (JPanel root) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        root.add(new JLabel("Wall to follow:"));

        m_wall = new JComboBox <Rotation>(Rotation.values());
        m_wall.setSelectedItem(m_rot);
        m_wall.setRenderer(new RotationRenderer());
        root.add(m_wall);
      }

      @Override
      protected void cancel () {}

      @Override
      protected boolean accept () {
        m_rot = (Rotation) m_wall.getSelectedItem();
        return true;
      }
    };
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#clone()
   */
  @Override
  public Object clone () {
    return new WallFollowerAgent(m_env);
  }

  /**
   * Extrae la información del objeto a partir de una forma serializada del
   * mismo.
   * @param input Flujo de entrada con la información del objeto.
   * @throws ClassNotFoundException Si se trata de un objeto de otra clase.
   * @throws IOException Si no se puede leer el flujo de entrada.
   */
  private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
    input.defaultReadObject();
    m_last_dir = Direction.RIGHT;
  }

  /**
   * Clase que permite mostrar un nombre personalizado para las rotaciones en
   * el contexto de seguir paredes.
   */
  private class RotationRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent (JList <?> list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      switch ((Rotation) value) {
        case CLOCKWISE:
          setText("Right wall");
          break;
        case COUNTER_CLOCKWISE:
          setText("Left wall");
          break;
      }
      return c;
    }
  }
}
