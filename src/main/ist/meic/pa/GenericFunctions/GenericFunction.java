package ist.meic.pa.GenericFunctions;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * SimplifiedSMC implementation using UnsortedContainers as data structures. For
 * every call() filters and then sorts the resulting methods.
 */
public class GenericFunction extends SimplifiedSMC<GFMethod> {
  public GenericFunction(String name) {
    super(name);
  }

  @Override
  protected GFMContainer<GFMethod> createGFMContainer(boolean after) {
    return new GFMUnsortedContainer<GFMethod>();
  }

  @Override
  protected Optional<GFMethod> getApplicablePrimary(Object[] args) {
    return primaryMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted()
        .findFirst();
  }

  @Override
  protected Stream<GFMethod> getApplicableBefores(Object[] args) {
    return beforeMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted();
  }

  @Override
  protected Stream<GFMethod> getApplicableAfters(Object[] args) {
    return afterMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted((gfm1, gfm2) -> -gfm1.compareTo(gfm2));
  }
}
