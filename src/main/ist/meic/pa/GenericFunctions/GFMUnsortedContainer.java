package ist.meic.pa.GenericFunctions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * GFMContainer implementation using an HashMap to store the methods.
 */
public class GFMUnsortedContainer<E extends GFMethod>
    implements GFMContainer<E> {
  private Map<List<Class<?>>, E> methods;

  public GFMUnsortedContainer() {
    this.methods = new HashMap<List<Class<?>>, E>();
  }

  @Override
  public void add(E gfm) {
    List<Class<?>> params = Arrays.asList(gfm.getParameterTypes());
    this.methods.put(Collections.unmodifiableList(params), gfm);
  }

  @Override
  public Stream<E> stream() {
    return this.methods.values().stream();
  }
}
