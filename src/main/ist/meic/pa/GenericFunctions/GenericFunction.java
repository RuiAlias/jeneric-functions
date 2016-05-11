package ist.meic.pa.GenericFunctions;

import java.util.Optional;
import java.util.stream.Stream;

public class GenericFunction extends SimplifiedSMC<GFMethod> {
  public GenericFunction(String name) {
    super(name);
  }

  @Override
  protected GFMContainer<GFMethod> createGFMContainer(
      boolean after) {
    return new GFMMap<GFMethod>();
  }

  @Override
  protected Optional<GFMethod> getPrimary(Object[] args) {
    return primaryMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted()
        .findFirst();
  }

  @Override
  protected Stream<GFMethod> getBefores(Object[] args) {
    return beforeMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted();
  }

  @Override
  protected Stream<GFMethod> getAfters(Object[] args) {
    return afterMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted((gfm1, gfm2) -> -gfm1.compareTo(gfm2));
  }
}
