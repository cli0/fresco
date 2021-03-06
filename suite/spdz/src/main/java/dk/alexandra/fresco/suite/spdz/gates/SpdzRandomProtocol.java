package dk.alexandra.fresco.suite.spdz.gates;

import dk.alexandra.fresco.framework.network.SCENetwork;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.suite.spdz.SpdzResourcePool;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzSInt;
import dk.alexandra.fresco.suite.spdz.storage.SpdzStorage;

public class SpdzRandomProtocol extends SpdzNativeProtocol<SInt> {

  private SpdzSInt randomElement;

  @Override
  public SpdzSInt out() {
    return randomElement;
  }

  @Override
  public EvaluationStatus evaluate(int round, SpdzResourcePool spdzResourcePool,
      SCENetwork network) {
    SpdzStorage store = spdzResourcePool.getStore();
    this.randomElement = store.getSupplier().getNextRandomFieldElement();
    return EvaluationStatus.IS_DONE;
  }

}
