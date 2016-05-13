package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctions.GFMethod;

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

    // System.out.println("GFMethod::compareTypes - moreSpecific?" + moreSpecific +
    //                    "\tlessSpecific?" + lessSpecific);

    if (moreSpecific && !lessSpecific) {
      // System.out.println("GFMethod::compareTypes - t1 is more specific on #" +
      //                    i);
      return -1;
    } else if (lessSpecific && !moreSpecific) {
      // System.out.println("GFMethod::compareTypes - t1 is less specific on #" +
      //                    i);
      return 1;
    } else {
      // System.out.println("hc "+t1.hashCode()+" "+t2.hashCode());
      return t1.hashCode() - t2.hashCode();
    }
  }
}
