package ist.meic.pa.GenericFunctionsExtended;

import ist.meic.pa.GenericFunctions.GFMethod;

public class GFMethodExtended extends GFMethod{
  
  public int compareToRightLeft(GFMethod other) {

	    Class<?>[] thisParameterTypes = getParameterTypes();
	    Class<?>[] otherParameterTypes = other.getParameterTypes();

	    assert(thisParameterTypes.length == otherParameterTypes.length);

	    int diff = 0;

	    
	    for (int i = thisParameterTypes.length - 1; diff == 0 && i >= 0; i--) {
	      diff = compareTypes(thisParameterTypes[i], otherParameterTypes[i]);
	    }
	    return diff;
	  }
}
