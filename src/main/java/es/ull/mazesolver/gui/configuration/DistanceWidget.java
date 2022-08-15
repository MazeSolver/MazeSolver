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
 * @file DistanceConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import es.ull.mazesolver.agent.distance.DistanceCalculator.DistanceType;
import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.translations.Translatable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Panel de configuración con la selección de un tipo de algoritmo de cálculo
 * de distancias.
 */
public class DistanceWidget extends JPanel implements Translatable {
    private static final long serialVersionUID = 1L;

    private JComboBox<DistanceType> m_combo;
    private JLabel m_dist_calc_text;

    /**
     * Crea el widget de selección del tipo de cálculo de distancias.
     *
     * @param dist El tipo de distancia seleccionada por defecto.
     */
    public DistanceWidget(DistanceType dist) {
        m_combo = new JComboBox<DistanceType>(DistanceType.values());
        m_combo.setSelectedItem(dist);

        m_dist_calc_text = new JLabel();

        setLayout(new BorderLayout(5, 0));
        add(m_dist_calc_text, BorderLayout.WEST);
        add(m_combo, BorderLayout.CENTER);
    }

    /**
     * @return El tipo de distancia que ha seleccionado el usuario.
     */
    public DistanceType getSelectedType() {
        return (DistanceType) m_combo.getSelectedItem();
    }

    /* (non-Javadoc)
     * @see es.ull.mazesolver.translations.Translatable#translate()
     */
    @Override
    public void translate() {
        m_dist_calc_text.setText(MainWindow.getTranslations().agent().distanceCalculator() + ":");
    }

}
