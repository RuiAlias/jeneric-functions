package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctions.GFMethod;

/**
 * Contains the reflection mechanisms to fetch and invoke the method call() at
 * runtime. Implements the comparison between 2 GFMethods. Implements the
 * applicability criteria given 1 or more arguments. Also defines a new
 * comparator.
 */
public class GFMethodExtended extends GFMethod {
  public int compareToRightLeft(GFMethodExtended other) {
    Class<?>[] thisParameterTypes = getParameterTypes();
    Class<?>[] otherParameterTypes = other.getParameterTypes();

    assert (thisParameterTypes.length == otherParameterTypes.length);

    int diff = 0;

    for (int i = thisParameterTypes.length - 1; diff == 0 && i >= 0; i--) {
      diff = compareTypes(thisParameterTypes[i], otherParameterTypes[i]);
    }

    return diff;
  }

  private static int compareTypes(Class<?> t1, Class<?> t2) {
    boolean moreSpecific = t2.isAssignableFrom(t1);
    boolean lessSpecific = t1.isAssignableFrom(t2);

    if (moreSpecific && !lessSpecific) {
      return -1;
    } else if (lessSpecific && !moreSpecific) {
      return 1;
    } else {
      return t1.hashCode() - t2.hashCode();
    }
  }
}
