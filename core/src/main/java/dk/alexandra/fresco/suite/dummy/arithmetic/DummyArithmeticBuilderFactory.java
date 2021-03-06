package dk.alexandra.fresco.suite.dummy.arithmetic;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.builder.numeric.BuilderFactoryNumeric;
import dk.alexandra.fresco.framework.builder.numeric.Comparison;
import dk.alexandra.fresco.framework.builder.numeric.DefaultComparison;
import dk.alexandra.fresco.framework.builder.numeric.Numeric;
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric;
import dk.alexandra.fresco.framework.network.SCENetwork;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.lib.compare.MiscBigIntegerGenerators;
import dk.alexandra.fresco.lib.field.integer.BasicNumericContext;
import dk.alexandra.fresco.logging.PerformanceLogger;
import dk.alexandra.fresco.logging.arithmetic.ComparisonLoggerDecorator;
import dk.alexandra.fresco.logging.arithmetic.NumericLoggingDecorator;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A {@link BuilderFactoryNumeric} implementation for the Dummy Arithmetic suite. This class has
 * built-in support for logging the amount of different operations (i.e. protocols) that the
 * application asks for.
 *
 */
public class DummyArithmeticBuilderFactory implements BuilderFactoryNumeric {

  /**
   * Map from party ID to performance loggers. 
   */
  public static final ConcurrentMap<Integer, List<PerformanceLogger>> performanceLoggers =
      new ConcurrentHashMap<>();
  private BasicNumericContext factory;
  private MiscBigIntegerGenerators mog;
  private ComparisonLoggerDecorator compDecorator;
  private NumericLoggingDecorator numericDecorator;

  /**
   * Creates a dummy arithmetic builder factory which creates basic numeric operations
   * 
   * @param factory The numeric context we work within. 
   */
  public DummyArithmeticBuilderFactory(BasicNumericContext factory) {
    super();
    this.factory = factory;
    performanceLoggers.put(factory.getMyId(), new ArrayList<>());
  }

  @Override
  public Comparison createComparison(ProtocolBuilderNumeric builder) {
    Comparison comp = new DefaultComparison(this, builder);
    if (compDecorator == null) {
      compDecorator = new ComparisonLoggerDecorator(comp);
      
      performanceLoggers.get(factory.getMyId()).add(compDecorator);  
    } else {
      compDecorator.setDelegate(comp);  
    }
    
    return compDecorator;
  }
  
  @Override
  public BasicNumericContext getBasicNumericContext() {
    return factory; 
  }

  @Override
  public Numeric createNumeric(ProtocolBuilderNumeric builder) {    
    Numeric numeric = new Numeric() {

      @Override
      public DRes<SInt> sub(DRes<SInt> a, BigInteger b) {
        DummyArithmeticNativeProtocol<SInt> c =
            new DummyArithmeticSubtractProtocol(a, () -> new DummyArithmeticSInt(b));
        return builder.append(c);
      }

      @Override
      public DRes<SInt> sub(BigInteger a, DRes<SInt> b) {
        DummyArithmeticSubtractProtocol c =
            new DummyArithmeticSubtractProtocol(() -> new DummyArithmeticSInt(a), b);
        return builder.append(c);
      }

      @Override
      public DRes<SInt> sub(DRes<SInt> a, DRes<SInt> b) {
        DummyArithmeticSubtractProtocol c = new DummyArithmeticSubtractProtocol(a, b);
        return builder.append(c);
      }

      @Override
      public DRes<SInt> randomElement() {
        DummyArithmeticNativeProtocol<SInt> c = new DummyArithmeticNativeProtocol<SInt>() {

          DummyArithmeticSInt elm;

          @Override
          public EvaluationStatus evaluate(int round, DummyArithmeticResourcePool resourcePool,
              SCENetwork network) {
            BigInteger r;
            do {
              r = new BigInteger(factory.getModulus().bitLength(), resourcePool.getRandom());
            } while (r.compareTo(factory.getModulus()) >= 0);
            elm = new DummyArithmeticSInt(r);
            return EvaluationStatus.IS_DONE;
          }

          @Override
          public SInt out() {
            return elm;
          }
        };
        return builder.append(c);
      }

      @Override
      public DRes<SInt> randomBit() {
        DummyArithmeticNativeProtocol<SInt> c = new DummyArithmeticNativeProtocol<SInt>() {

          DummyArithmeticSInt bit;

          @Override
          public EvaluationStatus evaluate(int round, DummyArithmeticResourcePool resourcePool,
              SCENetwork network) {
            bit = new DummyArithmeticSInt(BigInteger.valueOf(resourcePool.getRandom().nextInt(2)));
            return EvaluationStatus.IS_DONE;
          }

          @Override
          public SInt out() {
            return bit;
          }
        };
        return builder.append(c);
      }

      @Override
      public DRes<BigInteger> open(DRes<SInt> secretShare) {
        DummyArithmeticOpenToAllProtocol c = new DummyArithmeticOpenToAllProtocol(secretShare);
        return builder.append(c);
      }

      @Override
      public DRes<BigInteger> open(DRes<SInt> secretShare, int outputParty) {
        DummyArithmeticOpenProtocol c = new DummyArithmeticOpenProtocol(secretShare, outputParty);
        return builder.append(c);
      }

      @Override
      public DRes<SInt> mult(BigInteger a, DRes<SInt> b) {
        DummyArithmeticMultProtocol c =
            new DummyArithmeticMultProtocol(() -> new DummyArithmeticSInt(a), b);
        return builder.append(c);
      }

      @Override
      public DRes<SInt> mult(DRes<SInt> a, DRes<SInt> b) {
        DummyArithmeticMultProtocol c = new DummyArithmeticMultProtocol(a, b);
        return builder.append(c);
      }

      @Override
      public DRes<SInt> known(BigInteger value) {
        DummyArithmeticNativeProtocol<SInt> c = new DummyArithmeticNativeProtocol<SInt>() {

          DummyArithmeticSInt val;

          @Override
          public EvaluationStatus evaluate(int round, DummyArithmeticResourcePool resourcePool,
              SCENetwork network) {
            val = new DummyArithmeticSInt(value);
            return EvaluationStatus.IS_DONE;
          }

          @Override
          public SInt out() {
            return val;
          }

        };
        return builder.append(c);
      }

      @Override
      public DRes<SInt> input(BigInteger value, int inputParty) {
        DummyArithmeticCloseProtocol c = new DummyArithmeticCloseProtocol(inputParty, () -> value);
        return builder.append(c);
      }

      @Override
      public DRes<SInt> add(BigInteger a, DRes<SInt> b) {
        DummyArithmeticAddProtocol c =
            new DummyArithmeticAddProtocol(() -> new DummyArithmeticSInt(a), b);
        return builder.append(c);
      }

      @Override
      public DRes<SInt> add(DRes<SInt> a, DRes<SInt> b) {
        DummyArithmeticAddProtocol c = new DummyArithmeticAddProtocol(a, b);
        return builder.append(c);
      }
    };
    
    if (numericDecorator == null) {
      numericDecorator = new NumericLoggingDecorator(numeric);
      performanceLoggers.get(factory.getMyId()).add(numericDecorator);      
    } else {
      numericDecorator.setDelegate(numeric);
    }    
    return numericDecorator;
  }

  @Override
  public MiscBigIntegerGenerators getBigIntegerHelper() {
    if (mog == null) {
      mog = new MiscBigIntegerGenerators(factory.getModulus());
    }
    return mog;
  }

}
