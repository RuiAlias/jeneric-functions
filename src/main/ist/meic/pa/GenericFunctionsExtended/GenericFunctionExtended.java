package ist.meic.pa.GenericFunctionsExtended;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class GenericFunctionExtended {
	private HashMap<List<Class<?>>, GFMethodExtended> cache;
	private static final String NO_APPLICABLE_METHODS =
		      "No methods for generic function %s with args %s of classes %s";
	private String name;
	private GFMSetExtended primaryMethods;
	private GFMSetExtended beforeMethods;
	private GFMSetExtended afterMethods;

	public GenericFunctionExtended(String name) {
		this.name = name;
		this.cache = new HashMap<List<Class<?>>, GFMethodExtended>();
		primaryMethods = new GFMSetExtended((gfm1, gfm2) -> gfm1.compareToRightLeft(gfm2));
		beforeMethods = new GFMSetExtended((gfm1, gfm2) -> gfm1.compareToRightLeft(gfm2));
		afterMethods = new GFMSetExtended((gfm1, gfm2) -> -gfm1.compareToRightLeft(gfm2));
	}

	public void addMethod(GFMethodExtended gfm) {
		cache.clear();
		this.primaryMethods.add(gfm);
	}

	public void addBeforeMethod(GFMethodExtended gfm) {
		this.beforeMethods.add(gfm);
	}

	public void addAfterMethod(GFMethodExtended gfm) {
		this.afterMethods.add(gfm);
	}

	public Object call(Object... args) {
	    callBefores(args);

	    Object bestMethodReturn = callPrimary(args);

	    callAfters(args);

	    return bestMethodReturn;
	  }

	private void callAfters(Object... args) {
		afterMethods.stream()
	        .filter(gfm -> gfm.isApplicable(args))
	        .forEachOrdered(gfm -> gfm.dynamicCall(args));
	}

	private void callBefores(Object... args) {
		beforeMethods.stream()
	        .filter(gfm -> gfm.isApplicable(args))
	        .forEachOrdered(gfm -> gfm.dynamicCall(args));
	}
	
	private Object callPrimary(Object... args) {
		List<Class<?>> types = Arrays.asList(argsTypes(args));

		GFMethodExtended effective = null;
		if (cache.containsKey(types)) {
			effective = cache.get(types);
		} else {
			effective = primaryMethods.stream().filter(gfm -> gfm.isApplicable(args)).findFirst()
					.orElseThrow(() -> generateNoApplicableMethodsException(args));
			insertIntoCache(effective, types);
		}
		return effective.dynamicCall(args);
	}

	private void insertIntoCache(GFMethodExtended effective, List<Class<?>> types) {
		cache.put(Collections.unmodifiableList(types), effective);
	}

	protected IllegalArgumentException generateNoApplicableMethodsException(Object... args) {
		final String arguments = Arrays.deepToString(args);
		final String types = Arrays.deepToString(argsTypes(args));
		final String message = String.format(NO_APPLICABLE_METHODS, this.name, arguments, types);

		return new IllegalArgumentException(message);
	}

	protected static Class<?>[] argsTypes(Object[] args) {
		Class<?>[] types = new Class<?>[args.length];

		for (int i = 0; i < args.length; i++) {
			types[i] = args[i].getClass();
		}

		return types;
	}
}
