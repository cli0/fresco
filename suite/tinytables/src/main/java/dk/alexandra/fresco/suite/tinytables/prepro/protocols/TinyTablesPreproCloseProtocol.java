package dk.alexandra.fresco.suite.tinytables.prepro.protocols;

import dk.alexandra.fresco.framework.network.SCENetwork;
import dk.alexandra.fresco.framework.sce.resources.ResourcePoolImpl;
import dk.alexandra.fresco.framework.value.SBool;
import dk.alexandra.fresco.suite.tinytables.datatypes.TinyTablesElement;
import dk.alexandra.fresco.suite.tinytables.prepro.TinyTablesPreproProtocolSuite;
import dk.alexandra.fresco.suite.tinytables.prepro.datatypes.TinyTablesPreproSBool;

/**
 * <p>
 * This class represents a close protocol in the preprocessing phase of the TinyTables protocol.
 * </p>
 * 
 * <p>
 * Here the one player, the inputter, knows the input value <i>b</i>, and he picks a random mask
 * <i>r</i> and sends <i>e = b + r</i> to the other player, who simply assigns <code>false</code> to
 * his share of the mask.
 * </p>
 * 
 * @author Jonas Lindstrøm (jonas.lindstrom@alexandra.dk)
 *
 */
public class TinyTablesPreproCloseProtocol extends TinyTablesPreproProtocol<SBool> {

  private int inputter;
  private TinyTablesPreproSBool out;

  public TinyTablesPreproCloseProtocol(int id, int inputter) {
    this.id = id;
    this.inputter = inputter;
  }

  public TinyTablesPreproCloseProtocol(int id, int inputter, SBool out) {
    this.id = id;
    this.inputter = inputter;
    this.out = (TinyTablesPreproSBool) out;
  }

  @Override
  public EvaluationStatus evaluate(int round, ResourcePoolImpl resourcePool, SCENetwork network) {
    TinyTablesPreproProtocolSuite ps =
        TinyTablesPreproProtocolSuite.getInstance(resourcePool.getMyId());

    out = (out == null) ? new TinyTablesPreproSBool() : out;
    if (resourcePool.getMyId() == inputter) {
      /*
       * The masking parameter r is additively shared among the players. If you are the inputter,
       * you are responsible for picking a random share.
       */
      TinyTablesElement r = new TinyTablesElement(resourcePool.getSecureRandom().nextBoolean());
      out.setValue(r);

      // We store the share for the online phase
      ps.getStorage().storeMaskShare(id, r);

    } else {
      /*
       * All other players set a trivial (false) share.
       */
      out.setValue(new TinyTablesElement(false));
    }
    return EvaluationStatus.IS_DONE;
  }

  @Override
  public SBool out() {
    return out;
  }

}
