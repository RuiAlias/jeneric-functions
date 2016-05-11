package ist.meic.pa.GenericFunctionsExtended;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import ist.meic.pa.GenericFunctions.GFMContainer;
import ist.meic.pa.GenericFunctions.GFMethod;

public class GFMSortedSet<E extends GFMethod> implements GFMContainer<E> {
  private SortedSet<E> methods;

  public GFMSortedSet() {
    this.methods = new TreeSet<E>();
  }

  public GFMSortedSet(Comparator<E> comparator) {
    this.methods = new TreeSet<E>(comparator);
  }

  @Override
  public void add(E gfm) {
    if (this.methods.contains(gfm)) {
      this.methods.remove(gfm);
    }

    this.methods.add(gfm);
  }

  @Override
  public Stream<E> stream() {
    return this.methods.stream();
  }
}
