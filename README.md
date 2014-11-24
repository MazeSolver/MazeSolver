MazeSolver
==========

Proyecto de la asignatura de Sistemas Inteligentes de la Universidad de La Laguna (Computación)

##Descripción
Nuestro objetivo es crear una interfaz gráfica para la creación y visualización de laberintos, la configuración y creación de agentes y la ejecución de simulaciones. Permitiendo así que el usuario tenga un control total. Las funcionalidades que proporcionará la interfaz serán:
* Creación de laberintos con distintos algoritmos y parámetros.
* Guardado de laberintos en ficheros.
* Carga desde fichero y visualización simultánea de varios laberintos.
* Ejecución de simulaciones paso a paso y completas simultáneamente en todos los laberintos cargados.
* Adición de distintos tipos de agentes a los laberintos, que serán los que intenten resolverlos.
* Parametrización de los agentes dependiendo de su tipo.
* Realización de "carreras" entre agentes, donde ambos se sitúan en laberintos iguales pero separados y se ejecutan simultáneamente.
* Colocación de varios agentes en un mismo laberinto. Esto hará que haya interacción entre los distintos agentes, bien para colaborar en la búsqueda de la solución o para competir entre sí en un entorno cambiante.
* Visualización de las estadísticas de la ejecución.

##Recursos que usamos
* [Java Swing](http://docs.oracle.com/javase/tutorial/uiswing/): Lo utilizaremos para crear una interfaz gráfica de usuario que permita además el dibujo en bajo nivel de los elementos dentro del laberinto.
* [Algoritmos de creación de laberintos](http://www.astrolog.org/labyrnth/algrithm.htm): En esta página se encuentra una lista amplia de algoritmos de generación de laberintos, en la que nos basamos para implementar los nuestros.
* [ANTLR](http://www.antlr.org): Se trata de un generador de parseadores que puede proporcionar una interfaz para controlar el parser desde java, además de otros lenguajes. Lo utilizamos para crear un lenguaje de dominio específico para la definición de reglas de situación-acción por el usuario.
* [weblaf](https://github.com/mgarin/weblaf): Se trata de un Look & Feel diferente al cross-platform de java pero que sigue siendo multiplataforma pero tiene un diseño más elegante.
* [scrollabledesktop](): Libreria java para los paneles con scroll.

##Tecnologías de IA
* **Generación de Laberintos**: Mediante algoritmos de generación de árboles se podran generar laberintos perfectos o algoritmos que tengan como objetivo el mismo que el de un algoritmo de generación de árboles como el Prim. Además se gerarán laberintos no perfectos añadiendo al laberinto perfecto N ciclos o N paredes, separando el laberinto en varias componentes conexas.
* **Búsquedas heurísticas** (Hill-Climbing, A*, RTA*, Simulated annealing): Todos estos algoritmos suponen que el agente conoce la posición de la salida del laberinto para poder hacer una medida heurística de la distancia de cada posición a la salida. Además, el algoritmo A* va a requerir que el agente conozca todo el laberinto de antemano, como si tuviera una misma visión del mismo como la nuestra.
* **Tablas de percepción-acción**: Mediante tablas de percepción-acción crearemos agentes reactivos simples que se mueven por el laberinto dependiendo de dónde el agente detecta los obstáculos.
* **Reglas de situación-acción**: Se trata de una forma algo más flexible de definir las tablas de percepción-acción, que en nuestro caso además permiten distinguir una mayor variedad de situaciones (distingue otros agentes de paredes, conoce los lugares por los que ha pasado, ...). La arquitectura utilizada para los agentes basados en esto es la de subsunción, donde la regla de mayor prioridad es la primera y la de menor prioridad la última.
* **Sistemas multiagente**: Como las tablas de percepción-acción y las reglas de situación-acción son muy ineficientes para que un agente pueda encontrar la salida del laberinto por sí mismo, permitimos poner varios agentes en un mismo entorno para que interactúen entre sí. Además podemos crear entornos en los cuales los agentes colaboren entre sí mediante una zona de memoria compartida para que creen el mapa del laberinto y puedan resolverlo todos una vez alguno haya encontrado la salida.
* **Compunicación entre agentes**: Mediante el uso de pizarras los agentes se van a poder comunicar entre sí sin importar que agente sea cada uno, por ejemplo un agente A* se puede comunicar con un Agente de reglas percepción-acción diciendole a este por donde el A* cree que esta el camino.

## [LICENSE](http://www.gnu.org/licenses/gpl-3.0.html) ![LICENSE](http://www.gnu.org/graphics/gplv3-88x31.png)

MazeSolver by his [contributors](https://github.com/kevinrobayna/MazeSolver/graphs/contributors) is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
