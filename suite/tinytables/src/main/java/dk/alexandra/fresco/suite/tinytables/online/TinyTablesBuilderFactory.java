package dk.alexandra.fresco.suite.tinytables.online;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.builder.binary.Binary;
import dk.alexandra.fresco.framework.builder.binary.BuilderFactoryBinary;
import dk.alexandra.fresco.framework.builder.binary.ProtocolBuilderBinary;
import dk.alexandra.fresco.framework.value.SBool;
import dk.alexandra.fresco.suite.tinytables.datatypes.TinyTablesElement;
import dk.alexandra.fresco.suite.tinytables.online.datatypes.TinyTablesSBool;
import dk.alexandra.fresco.suite.tinytables.online.protocols.TinyTablesANDProtocol;
import dk.alexandra.fresco.suite.tinytables.online.protocols.TinyTablesCloseProtocol;
import dk.alexandra.fresco.suite.tinytables.online.protocols.TinyTablesNOTProtocol;
import dk.alexandra.fresco.suite.tinytables.online.protocols.TinyTablesOpenToAllProtocol;
import dk.alexandra.fresco.suite.tinytables.online.protocols.TinyTablesXORProtocol;

public class TinyTablesBuilderFactory implements BuilderFactoryBinary {

  private int counter = 0;

  public TinyTablesBuilderFactory() {}

  private int getNextId() {
    return counter++;
  }

  @Override
  public Binary createBinary(ProtocolBuilderBinary builder) {
    return new Binary() {

      @Override
      public DRes<SBool> xor(DRes<SBool> left, DRes<SBool> right) {
        SBool out = new TinyTablesSBool();
        TinyTablesXORProtocol p = new TinyTablesXORProtocol(left, right, out);
        builder.append(p);
        return p;
      }

      @Override
      public DRes<SBool> randomBit() {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public DRes<Boolean> open(DRes<SBool> toOpen, int towardsPartyId) {
        throw new RuntimeException("Not implemented yet");
      }

      @Override
      public DRes<Boolean> open(DRes<SBool> toOpen) {
        TinyTablesOpenToAllProtocol p = new TinyTablesOpenToAllProtocol(getNextId(), toOpen);
        builder.append(p);
        return p;
      }

      @Override
      public DRes<SBool> not(DRes<SBool> in) {
        SBool out = new TinyTablesSBool();
        TinyTablesNOTProtocol p = new TinyTablesNOTProtocol(in, out);
        builder.append(p);
        return p;
      }

      @Override
      public DRes<SBool> known(boolean known) {
        return () -> new TinyTablesSBool(new TinyTablesElement(known));
      }

      @Override
      public DRes<SBool> input(boolean in, int inputter) {
        TinyTablesCloseProtocol p = new TinyTablesCloseProtocol(getNextId(), inputter, in);
        builder.append(p);
        return p;
      }

      @Override
      public DRes<SBool> and(DRes<SBool> left, DRes<SBool> right) {
        SBool out = new TinyTablesSBool();
        TinyTablesANDProtocol p = new TinyTablesANDProtocol(getNextId(), left, right, out);
        builder.append(p);
        return p;
      }
    };
  }

}
