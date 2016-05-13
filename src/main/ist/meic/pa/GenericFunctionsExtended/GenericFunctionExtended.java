package ist.meic.pa.GenericFunctionsExtended;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import ist.meic.pa.GenericFunctions.GFMContainer;
import ist.meic.pa.GenericFunctions.SimplifiedSMC;

/**
 * SimplifiedSMC implementation using SortedContainers as data structures. For
 * every call() filters the methods.
 */
public class GenericFunctionExtended extends SimplifiedSMC<GFMethodExtended> {
  private Map<List<Class<?>>, GFMethodExtended> primaryMethodCache;

  public GenericFunctionExtended(String name) {
    super(name);
    this.primaryMethodCache = new HashMap<List<Class<?>>, GFMethodExtended>();
  }

  @Override
  protected GFMContainer<GFMethodExtended> createGFMContainer(boolean after) {
    if (!after) {
      return new GFMSortedContainer<GFMethodExtended>(
          (gfm1, gfm2) -> gfm1.compareToRightLeft(gfm2));
    } else {
      return new GFMSortedContainer<GFMethodExtended>(
          (gfm1, gfm2) -> -gfm1.compareToRightLeft(gfm2));
    }
  }

  @Override
  public void addMethod(GFMethodExtended gfm) {
    primaryMethodCache.clear();
    super.addMethod(gfm);
  }

  @Override
  protected Optional<GFMethodExtended> getApplicablePrimary(Object[] args) {
    List<Class<?>> types = Arrays.asList(argsTypes(args));

    GFMethodExtended primaryMethod;

    if (primaryMethodCache.containsKey(types)) {
      primaryMethod = primaryMethodCache.get(types);
    } else {
      primaryMethod = primaryMethods.stream()
          .filter(gfm -> gfm.isApplicable(args))
          .findFirst()
          .orElseThrow(() -> generateNoApplicableMethodsException(args));

      insertIntoCache(primaryMethod, types);
    }

    return Optional.of(primaryMethod);
  }

  @Override
  protected Stream<GFMethodExtended> getApplicableBefores(Object[] args) {
    return beforeMethods.stream().filter(gfm -> gfm.isApplicable(args));
  }

  @Override
  protected Stream<GFMethodExtended> getApplicableAfters(Object[] args) {
    return afterMethods.stream().filter(gfm -> gfm.isApplicable(args));
  }

  private void insertIntoCache(GFMethodExtended primaryMethod,
      List<Class<?>> types) {
    primaryMethodCache.put(Collections.unmodifiableList(types), primaryMethod);
  }
}
