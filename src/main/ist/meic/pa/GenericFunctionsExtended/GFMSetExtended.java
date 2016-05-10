package ist.meic.pa.GenericFunctionsExtended;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

public class GFMSetExtended{
  private SortedSet<GFMethodExtended> methods;

  public GFMSetExtended() {
    this.methods = new TreeSet<GFMethodExtended>();
  }

  public GFMSetExtended(Comparator<GFMethodExtended> comparator) {
    this.methods = new TreeSet<GFMethodExtended>(comparator);
  }

  public void add(GFMethodExtended gfm) {
    if (this.methods.contains(gfm)) {
      this.methods.remove(gfm);
    }

    this.methods.add(gfm);
  }

  public Stream<GFMethodExtended> stream() {
    return this.methods.stream();
  }
}
