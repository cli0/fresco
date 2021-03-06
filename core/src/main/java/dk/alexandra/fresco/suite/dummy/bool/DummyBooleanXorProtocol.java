package dk.alexandra.fresco.suite.dummy.bool;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.network.SCENetwork;
import dk.alexandra.fresco.framework.sce.resources.ResourcePool;
import dk.alexandra.fresco.framework.value.SBool;

/**
 * Implements logical XOR for the Dummy Boolean protocol suite, where all operations are done in the
 * clear.
 *
 */
public class DummyBooleanXorProtocol extends DummyBooleanNativeProtocol<SBool> {

  private DRes<SBool> left;
  private DRes<SBool> right;
  private DummyBooleanSBool out;

  /**
   * Constructs a protocol to XOR the result of two computations.
   * 
   * @param left the left operand
   * @param right the right operand
   */
  public DummyBooleanXorProtocol(DRes<SBool> left, DRes<SBool> right) {
    super();
    this.left = left;
    this.right = right;
    this.out = null;
  }

  @Override
  public EvaluationStatus evaluate(int round, ResourcePool resourcePool, SCENetwork network) {
    out = new DummyBooleanSBool();
    this.out.setValue(
        ((DummyBooleanSBool) left.out()).getValue() ^ ((DummyBooleanSBool) right.out()).getValue());
    return EvaluationStatus.IS_DONE;
  }

  @Override
  public SBool out() {
    return out;
  }
}
