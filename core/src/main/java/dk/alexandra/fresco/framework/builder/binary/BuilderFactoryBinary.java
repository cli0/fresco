package dk.alexandra.fresco.framework.builder.binary;

import dk.alexandra.fresco.framework.BuilderFactory;
import dk.alexandra.fresco.framework.builder.binary.ProtocolBuilderBinary.SequentialBinaryBuilder;

public interface BuilderFactoryBinary extends BuilderFactory<SequentialBinaryBuilder> {

  BinaryBuilder createBinaryBuilder(ProtocolBuilderBinary builder);

  BasicBinaryFactory createBasicBinaryFactory();

  default ComparisonBuilderBinary createComparison(ProtocolBuilderBinary builder) {
    return new DefaultComparisonBinaryBuilder(this, builder);
  }

  default BinaryBuilderAdvanced createAdvancedBinary(ProtocolBuilderBinary builder) {
    return new DefaultBinaryBuilderAdvanced(this, builder);
  }

  @Override
  default SequentialBinaryBuilder createProtocolBuilder() {
    return ProtocolBuilderBinary.createApplicationRoot(this);
  }
}