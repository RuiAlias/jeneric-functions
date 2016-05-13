package ist.meic.pa.GenericFunctions;

import java.util.stream.Stream;

/**
 * Defines the interface of the data structures used to store methods.
 */
public interface GFMContainer<E> {
  public void add(E gfm);

  public Stream<E> stream();
}
