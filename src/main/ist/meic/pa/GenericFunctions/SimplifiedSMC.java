package ist.meic.pa.GenericFunctions;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Defines the method combination protocol and stores the primary, before and
 * after methods.
 */
public abstract class SimplifiedSMC<T extends GFMethod>
    extends AbstractGenericFunction {
  protected GFMContainer<T> primaryMethods;
  protected GFMContainer<T> beforeMethods;
  protected GFMContainer<T> afterMethods;

  public SimplifiedSMC(String name) {
    super(name);
    this.primaryMethods = createGFMContainer(false);
    this.beforeMethods = createGFMContainer(false);
    this.afterMethods = createGFMContainer(true);
  }

  public void addMethod(T gfm) {
    this.primaryMethods.add(gfm);
  }

  public void addBeforeMethod(T gfm) {
    this.beforeMethods.add(gfm);
  }

  public void addAfterMethod(T gfm) {
    this.afterMethods.add(gfm);
  }

  public Object call(Object... args) {
    GFMethod primary = getApplicablePrimary(args)
        .orElseThrow(() -> generateNoApplicableMethodsException(args));

    callBefores(args);
    Object bestMethodReturn = primary.dynamicCall(args);
    callAfters(args);

    return bestMethodReturn;
  }

  abstract protected GFMContainer<T> createGFMContainer(boolean after);

  abstract protected Optional<T> getApplicablePrimary(Object[] args);

  abstract protected Stream<T> getApplicableBefores(Object[] args);

  abstract protected Stream<T> getApplicableAfters(Object[] args);

  private void callBefores(Object[] args) {
    callOrdered(getApplicableBefores(args), args);
  }

  private void callAfters(Object[] args) {
    callOrdered(getApplicableAfters(args), args);
  }

  private void callOrdered(Stream<T> orderedMethods, Object[] args) {
    orderedMethods.forEachOrdered(gfm -> gfm.dynamicCall(args));
  }
}
