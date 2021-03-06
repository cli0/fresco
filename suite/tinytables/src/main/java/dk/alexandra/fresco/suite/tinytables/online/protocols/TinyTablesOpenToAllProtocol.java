package dk.alexandra.fresco.suite.tinytables.online.protocols;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.MPCException;
import dk.alexandra.fresco.framework.network.SCENetwork;
import dk.alexandra.fresco.framework.network.serializers.BooleanSerializer;
import dk.alexandra.fresco.framework.sce.resources.ResourcePoolImpl;
import dk.alexandra.fresco.framework.value.SBool;
import dk.alexandra.fresco.suite.tinytables.datatypes.TinyTablesElement;
import dk.alexandra.fresco.suite.tinytables.online.TinyTablesProtocolSuite;
import dk.alexandra.fresco.suite.tinytables.online.datatypes.TinyTablesSBool;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class represents an open-to-all protocol in the TinyTables preprocessing phase.
 * </p>
 *
 * <p>
 * Here, each of the two players send his share of the masking parameter <i>r</i> to the other
 * player such that each player can add this to the masked input <i>e = b + r</i> to get the
 * unmasked output <i>b</i>.
 * </p>
 * 
 * @author Jonas Lindstrøm (jonas.lindstrom@alexandra.dk)
 *
 */
public class TinyTablesOpenToAllProtocol extends TinyTablesProtocol<Boolean> {

  private int id;
  private DRes<SBool> toOpen;
  private Boolean opened;

  public TinyTablesOpenToAllProtocol(int id, DRes<SBool> toOpen) {
    super();
    this.id = id;
    this.toOpen = toOpen;
  }

  @Override
  public EvaluationStatus evaluate(int round, ResourcePoolImpl resourcePool, SCENetwork network) {
    TinyTablesProtocolSuite ps = TinyTablesProtocolSuite.getInstance(resourcePool.getMyId());

    /*
     * When opening a value, all players send their shares of the masking value r to the other
     * players, and each player can then calculate the unmasked value as the XOR of the masked value
     * and all the shares of the mask.
     */
    switch (round) {
      case 0:
        TinyTablesElement myR = ps.getStorage().getMaskShare(id);
        network.sendToAll(BooleanSerializer.toBytes(myR.getShare()));
        network.expectInputFromAll();
        return EvaluationStatus.HAS_MORE_ROUNDS;
      case 1:
        List<ByteBuffer> buffers = network.receiveFromAll();
        List<TinyTablesElement> maskShares = new ArrayList<>();
        for (ByteBuffer buffer : buffers) {
          maskShares.add(new TinyTablesElement(BooleanSerializer.fromBytes(buffer)));
        }
        boolean mask = TinyTablesElement.open(maskShares);
        this.opened = ((TinyTablesSBool) toOpen.out()).getValue().getShare() ^ mask;
        return EvaluationStatus.IS_DONE;
      default:
        throw new MPCException("Cannot evaluate rounds larger than 1");
    }
  }

  @Override
  public Boolean out() {
    return opened;
  }

}
