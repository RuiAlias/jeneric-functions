package ist.meic.pa.GenericFunctions;

import java.util.stream.Stream;

public interface GFMContainer<E> {
  public void add(E gfm);

  public Stream<E> stream();
}
