package ist.meic.pa.GenericFunctions;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class GenericFunction {
  final private String name;
  private SortedSet<GFMethod> primaryMethods;
  private SortedSet<GFMethod> beforeMethods;
  private SortedSet<GFMethod> afterMethods;
  private static final String NO_APPLICABLE_METHODS =
      "No methods for generic function %s with args %s of classes %s";

  public GenericFunction(String name) {
    this.name = name;
    this.primaryMethods = new TreeSet<GFMethod>();
    this.beforeMethods = new TreeSet<GFMethod>();
    this.afterMethods = new TreeSet<GFMethod>((gfm1, gfm2) -> -gfm1.compareTo(gfm2));
  }

  public void addMethod(GFMethod gfMethod) {
    // System.out.println("Vou fazer contains");
    boolean contains = this.primaryMethods.contains(gfMethod);
    // System.out.println("Terminei contains");

    if (contains) {
      this.primaryMethods.remove(gfMethod);
      // System.out.println("Actualizei o método");
    }

    // System.out.println("Vou adicionar");
    this.primaryMethods.add(gfMethod);
  }

  public void addBeforeMethod(GFMethod gfMethod) {
    if (this.beforeMethods.contains(gfMethod)) {
      this.beforeMethods.remove(gfMethod);
      // System.out.println("Actualizei o método");
    }

    this.beforeMethods.add(gfMethod);
  }

  public void addAfterMethod(GFMethod gfMethod) {
    if (this.afterMethods.contains(gfMethod)) {
      this.afterMethods.remove(gfMethod);
      // System.out.println("Actualizei o método");
    }

    this.afterMethods.add(gfMethod);
  }

  public Object call(Object... args) {
    beforeMethods.stream()
      .filter(gfm -> gfm.isApplicable(args))
      .forEachOrdered((gfm) -> gfm.dynamicCall(args));

    Object bestMethodReturn = primaryMethods.stream()
      .filter(gfm -> gfm.isApplicable(args))
      .findFirst()
      .orElseThrow(() -> generateNoApplicableMethodsException(args))
      .dynamicCall(args);

    afterMethods.stream()
      .filter(gfm -> gfm.isApplicable(args))
      .forEachOrdered((gfm) -> gfm.dynamicCall(args));

    return bestMethodReturn;
  }

  private IllegalArgumentException generateNoApplicableMethodsException(
      Object... args) {
    final String arguments = Arrays.deepToString(args);
    final String types = Arrays.deepToString(argsTypes(args));
    final String message =
        String.format(NO_APPLICABLE_METHODS, this.name, arguments, types);

    // System.out.println("Exception message: " + message);

    return new IllegalArgumentException(message);
  }

  private static Class<?>[] argsTypes(Object[] args) {
    Class<?>[] types = new Class<?>[args.length];

    for (int i = 0; i < args.length; i++) {
      types[i] = args[i].getClass();
    }

    return types;
  }
}
