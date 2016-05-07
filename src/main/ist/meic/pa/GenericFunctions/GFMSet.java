package ist.meic.pa.GenericFunctions;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

class GFMSet {
  private SortedSet<GFMethod> methods;

  protected GFMSet() {
    this.methods = new TreeSet<GFMethod>();
  }

  protected GFMSet(Comparator<GFMethod> comparator) {
    this.methods = new TreeSet<GFMethod>(comparator);
  }

  protected void add(GFMethod gfm) {
    if (this.methods.contains(gfm)) {
      this.methods.remove(gfm);
    }

    this.methods.add(gfm);
  }

  protected Stream<GFMethod> stream() {
    return this.methods.stream();
  }
}
