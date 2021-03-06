package dk.alexandra.fresco.demo;

import dk.alexandra.fresco.framework.ProtocolEvaluator;
import dk.alexandra.fresco.framework.TestThreadRunner;
import dk.alexandra.fresco.framework.TestThreadRunner.TestThread;
import dk.alexandra.fresco.framework.TestThreadRunner.TestThreadFactory;
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric;
import dk.alexandra.fresco.framework.configuration.ConfigurationException;
import dk.alexandra.fresco.framework.configuration.NetworkConfiguration;
import dk.alexandra.fresco.framework.configuration.TestConfiguration;
import dk.alexandra.fresco.framework.network.KryoNetNetwork;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.sce.SecureComputationEngineImpl;
import dk.alexandra.fresco.framework.sce.evaluator.BatchedProtocolEvaluator;
import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy;
import dk.alexandra.fresco.framework.sce.resources.storage.FilebasedStreamedStorageImpl;
import dk.alexandra.fresco.framework.sce.resources.storage.InMemoryStorage;
import dk.alexandra.fresco.framework.util.DetermSecureRandom;
import dk.alexandra.fresco.logging.PerformanceLogger;
import dk.alexandra.fresco.suite.spdz.SpdzProtocolSuite;
import dk.alexandra.fresco.suite.spdz.SpdzResourcePool;
import dk.alexandra.fresco.suite.spdz.SpdzResourcePoolImpl;
import dk.alexandra.fresco.suite.spdz.configuration.PreprocessingStrategy;
import dk.alexandra.fresco.suite.spdz.storage.SpdzStorage;
import dk.alexandra.fresco.suite.spdz.storage.SpdzStorageDummyImpl;
import dk.alexandra.fresco.suite.spdz.storage.SpdzStorageImpl;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

public class TestDistanceDemo {

  protected List<PerformanceLogger> runTest(
      TestThreadRunner.TestThreadFactory<SpdzResourcePool, ProtocolBuilderNumeric> f,
      EvaluationStrategy evalStrategy, int noOfParties) throws Exception {
    // Since SCAPI currently does not work with ports > 9999 we use fixed
    // ports
    // here instead of relying on ephemeral ports which are often > 9999.
    List<Integer> ports = new ArrayList<>(noOfParties);
    for (int i = 1; i <= noOfParties; i++) {
      ports.add(9000 + i * (noOfParties - 1));
    }

    Map<Integer, NetworkConfiguration> netConf =
        TestConfiguration.getNetworkConfigurations(noOfParties, ports);
    Map<Integer, TestThreadRunner.TestThreadConfiguration<SpdzResourcePool, ProtocolBuilderNumeric>> conf =
        new HashMap<>();
    List<PerformanceLogger> pls = new ArrayList<>();
    for (int playerId : netConf.keySet()) {
      SpdzProtocolSuite protocolSuite = new SpdzProtocolSuite(150);

      ProtocolEvaluator<SpdzResourcePool, ProtocolBuilderNumeric> evaluator =
          new BatchedProtocolEvaluator<>(EvaluationStrategy.fromEnum(evalStrategy));
      Network network = new KryoNetNetwork();
      network.init(netConf.get(playerId), 1);
      SpdzResourcePool rp = createResourcePool(playerId, noOfParties, network, new Random(),
          new DetermSecureRandom(), PreprocessingStrategy.DUMMY);
      TestThreadRunner.TestThreadConfiguration<SpdzResourcePool, ProtocolBuilderNumeric> ttc =
          new TestThreadRunner.TestThreadConfiguration<SpdzResourcePool, ProtocolBuilderNumeric>(
              new SecureComputationEngineImpl<>(protocolSuite, evaluator),
              rp);
      conf.put(playerId, ttc);
    }
    TestThreadRunner.run(f, conf);
    return pls;
  }

  private SpdzResourcePool createResourcePool(int myId, int size, Network network, Random rand,
      SecureRandom secRand, PreprocessingStrategy preproStrat) {
    SpdzStorage store;
    switch (preproStrat) {
      case DUMMY:
        store = new SpdzStorageDummyImpl(myId, size);
        break;
      case STATIC:
        store = new SpdzStorageImpl(0, size, myId,
            new FilebasedStreamedStorageImpl(new InMemoryStorage()));
        break;
      default:
        throw new ConfigurationException("Unkonwn preprocessing strategy: " + preproStrat);
    }
    return new SpdzResourcePoolImpl(myId, size, network, rand, secRand, store);
  }

  @Test
  public void testDistance() throws Exception {
    final TestThreadFactory<SpdzResourcePool, ProtocolBuilderNumeric> f =
        new TestThreadFactory<SpdzResourcePool, ProtocolBuilderNumeric>() {
          @Override
          public TestThread<SpdzResourcePool, ProtocolBuilderNumeric> next() {
            return new TestThread<SpdzResourcePool, ProtocolBuilderNumeric>() {
              @Override
              public void test() throws Exception {
                int x, y;
                if (conf.getMyId() == 1) {
                  x = 10;
                  y = 10;
                } else {
                  x = 20;
                  y = 15;
                }
                System.out.println("Running with x: " + x + ", y: " + y);
                DistanceDemo distDemo = new DistanceDemo(conf.getMyId(), x, y);
                BigInteger bigInteger = runApplication(distDemo);
                double distance = bigInteger.doubleValue();
                distance = Math.sqrt(distance);
                Assert.assertEquals(11.1803, distance, 0.0001);
              }
            };
          }

      ;
        };
    runTest(f, EvaluationStrategy.SEQUENTIAL_BATCHED, 2);
  }
  
  @Test
  public void testDistanceFromCmdLine() throws Exception{ 
    Runnable p1 = new Runnable() {
      
      @Override
      public void run() {
        DistanceDemo.main(new String[]{"-i", "1", "-p", "1:localhost:8081", "-p", "2:localhost:8082", "-s", "dummyArithmetic",  "-x" ,"10", "-y", "10"});
      }
    };
    
    Runnable p2 = new Runnable() {
      
      @Override
      public void run() {
        DistanceDemo.main(new String[]{"-i", "2", "-p", "1:localhost:8081", "-p", "2:localhost:8082", "-s", "dummyArithmetic",  "-x" ,"20", "-y", "15"});
      }
    }; 
    Thread t1 = new Thread(p1);
    Thread t2 = new Thread(p2);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
  }
}
