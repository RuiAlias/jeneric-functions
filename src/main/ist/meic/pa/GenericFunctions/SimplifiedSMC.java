package ist.meic.pa.GenericFunctions;

import java.util.Optional;
import java.util.stream.Stream;

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
    callBefores(args);
    Object bestMethodReturn = callPrimary(args);
    callAfters(args);

    return bestMethodReturn;
  }

  abstract protected GFMContainer<T> createGFMContainer(boolean after);

  abstract protected Optional<T> getPrimary(Object[] args);

  abstract protected Stream<T> getBefores(Object[] args);

  abstract protected Stream<T> getAfters(Object[] args);

  private Object callPrimary(Object[] args) {
    return getPrimary(args)
        .orElseThrow(() -> generateNoApplicableMethodsException(args))
        .dynamicCall(args);
  }

  private void callBefores(Object[] args) {
    callOrdered(getBefores(args), args);
  }

  private void callAfters(Object[] args) {
    callOrdered(getAfters(args), args);
  }

  private void callOrdered(Stream<T> orderedMethods, Object[] args) {
    orderedMethods.forEachOrdered(gfm -> gfm.dynamicCall(args));
  }
}
