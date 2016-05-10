package ist.meic.pa.GenericFunctions;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

public class GFMSet{
  private SortedSet<GFMethod> methods;

  public GFMSet() {
    this.methods = new TreeSet<GFMethod>();
  }

  public GFMSet(Comparator<GFMethod> comparator) {
    this.methods = new TreeSet<GFMethod>(comparator);
  }

  public void add(GFMethod gfm) {
    if (this.methods.contains(gfm)) {
      this.methods.remove(gfm);
    }

    this.methods.add(gfm);
  }

  public Stream<GFMethod> stream() {
    return this.methods.stream();
  }
}
