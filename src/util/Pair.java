/**
 * @file Pair.java
 * @date 29/10/2014
 */
package util;

/**
 * Clase que representa a un par de valores.
 */
public class Pair<T1, T2> {
  public T1 first;
  public T2 second;

  public Pair(T1 f, T2 s) {
    first = f;
    second = s;
  }
}
