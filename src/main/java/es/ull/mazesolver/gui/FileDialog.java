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
 * @file FileDialogs.java
 * @date 8/11/2014
 */
package es.ull.mazesolver.gui;

import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.translations.MessageTranslations;
import es.ull.mazesolver.translations.Translations;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase que contiene los métodos estáticos para mostrar los diálogos para
 * guardar y cargar ficheros.
 */
public class FileDialog {

    /**
     * Extensión de los ficheros donde se almacenan los laberintos.
     */
    public static final String MAZE_EXT = "maze";

    /**
     * Extensión de los ficheros donde se almacenan los registros.
     */
    public static final String LOG_EXT = "log";

    /**
     * Extensión de los ficheros donde se almacenan los agentes.
     */
    public static final String AGENT_EXT = "agent";

    // Esta clase no se puede instanciar
    private FileDialog() {
    }

    /**
     * Muestra un diálogo al usuario para que indique el fichero de salida donde
     * desea guardar el laberinto.
     *
     * @param maze Laberinto que se desea guardar.
     * @throws IOException Si no se pueden obtener permisos de escritura en el fichero
     *                     seleccionado.
     */
    public static void saveMaze(Maze maze) throws IOException {
        Translations tr = MainWindow.getTranslations();

        if (maze == null)
            throw new IllegalArgumentException(tr.exception().invalidMaze());

        JFileChooser chooser = createFileChooser(
                tr.message().mazeFiles() + " (*." + MAZE_EXT + ")", MAZE_EXT);
        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File f_chosen = chooser.getSelectedFile();
            File file = new File(f_chosen.getAbsolutePath() + extension(f_chosen.getName(),
                    MAZE_EXT));

            // Si va a sobreescribir un archivo preguntamos primero
            if (promptOverwrite(file))
                maze.saveFile(file.getAbsolutePath());
        }
    }

    /**
     * Muestra un diálogo para que el usuario seleccione un conjunto de ficheros
     * de los que cargar laberintos.
     *
     * @return El laberinto cargado.
     * @throws IOException Si hay un problema al leer el fichero seleccionado.
     */
    public static Maze[] loadMazes() throws IOException {
        JFileChooser chooser = createFileChooser(
                MainWindow.getTranslations().message().mazeFiles() + " (*." + MAZE_EXT + ")",
                MAZE_EXT);
        chooser.setMultiSelectionEnabled(true);
        int result = chooser.showOpenDialog(null);

        Maze[] mazes = new Maze[0];

        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = chooser.getSelectedFiles();
            mazes = new Maze[files.length];

            for (int i = 0; i < files.length; i++) {
                Maze maze = new Maze(files[i].getAbsolutePath());
                mazes[i] = maze;
            }
        }

        return mazes;
    }

    /**
     * Muestra un diálogo para que el usuario seleccione un fichero del que cargar
     * un laberinto.
     *
     * @return El laberinto cargado.
     * @throws IOException Si hay un problema al leer el fichero seleccionado.
     */
    public static Maze loadMaze() throws IOException {
        JFileChooser chooser = createFileChooser(
                MainWindow.getTranslations().message().mazeFiles() + " (*." + MAZE_EXT + ")",
                MAZE_EXT);
        int result = chooser.showOpenDialog(null);

        Maze maze = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            maze = new Maze(file.getAbsolutePath());
        }

        return maze;
    }

    /**
     * Muestra un diálogo para que el usuario seleccione un fichero en el que
     * guardar el log del programa.
     *
     * @param log Cadena de caracteres con el contenido del log.
     * @throws IOException Si hay un problema al leer el fichero seleccionado.
     */
    public static void saveLog(String log) throws IOException {
        JFileChooser chooser = createFileChooser(
                MainWindow.getTranslations().message().logFiles() + " (*." + LOG_EXT + ")",
                LOG_EXT);
        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File f_chosen = chooser.getSelectedFile();
            File file = new File(f_chosen.getAbsolutePath() + extension(f_chosen.getName(), LOG_EXT));

            if (promptOverwrite(file)) {
                PrintWriter writer = new PrintWriter(file);
                writer.print(log);
                writer.close();
            }
        }
    }

    /**
     * Muestra un diálogo para que el usuario seleccione un fichero del que cargar
     * un agente.
     *
     * @param env Entorno en el que colocar al agente.
     * @return El agente cargado.
     * @throws IOException Si hay un problema al leer el fichero seleccionado.
     */
    public static Agent loadAgent(Environment env) throws IOException {
        JFileChooser chooser = createFileChooser(
                MainWindow.getTranslations().message().agentFiles() + " (*." + AGENT_EXT + ")",
                AGENT_EXT);
        int result = chooser.showOpenDialog(null);

        Agent ag = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            ag = Agent.loadFile(file.getAbsolutePath(), env);
        }

        return ag;
    }

    /**
     * Muestra un diálogo para que el usuario seleccione un fichero donde guardar
     * una configuración de agente.
     *
     * @param agent Agente que se quiere guardar en un fichero.
     * @throws IOException Si no se puede leer el fichero.
     */
    public static void saveAgent(Agent agent) throws IOException {
        JFileChooser chooser = createFileChooser(
                MainWindow.getTranslations().message().agentFiles() + " (*." + AGENT_EXT + ")",
                AGENT_EXT);
        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File f_chosen = chooser.getSelectedFile();
            File file = new File(f_chosen.getAbsolutePath() + extension(f_chosen.getName(), AGENT_EXT));

            if (promptOverwrite(file))
                agent.saveFile(file.getAbsolutePath());
        }
    }

    /**
     * Crea un diálogo para seleccionar ficheros con una extensión concreta.
     *
     * @param description Descripción del formato.
     * @param extension   Extensión de los ficheros.
     * @return Un diálogo para seleccionar ficheros con extensión variable.
     */
    private static JFileChooser createFileChooser(String description, String extension) {
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter(description, extension);
        chooser.setFileFilter(filter);

        return chooser;
    }

    /**
     * Decide si el nombre del fichero especificado necesita la extensión o no y
     * la devuelve si hace falta.
     *
     * @param name      Nombre del fichero que quiere guardar el usuario.
     * @param extension Extensión esperada del fichero.
     * @return Extensión que habría que añadir al final del nombre para que éste
     * sea correcto.
     */
    private static String extension(String name, String extension) {
        String[] parts = name.split("\\.");
        if (parts.length == 1 || !parts[parts.length - 1].equals(extension))
            return "." + extension;
        else
            return "";
    }

    /**
     * Muestra al usuario una notificación si va a hacer una sobrescritura de
     * ficheros y pregunta antes de llevarla a cabo.
     *
     * @param file Fichero donde se desea realizar el guardado.
     * @return Si desea el usuario sobrescribir o no.
     */
    private static boolean promptOverwrite(File file) {
        if (file.exists()) {
            MessageTranslations tr = MainWindow.getTranslations().message();

            int choice =
                    JOptionPane.showConfirmDialog(null, tr.fileExistsOverwrite(),
                            tr.fileExists(), JOptionPane.YES_NO_OPTION);
            return choice == JOptionPane.YES_OPTION;
        }
        return true;
    }

}
