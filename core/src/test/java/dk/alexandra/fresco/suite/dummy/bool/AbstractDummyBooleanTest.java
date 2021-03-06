package dk.alexandra.fresco.suite.dummy.bool;

import dk.alexandra.fresco.framework.ProtocolEvaluator;
import dk.alexandra.fresco.framework.TestThreadRunner;
import dk.alexandra.fresco.framework.builder.binary.ProtocolBuilderBinary;
import dk.alexandra.fresco.framework.configuration.NetworkConfiguration;
import dk.alexandra.fresco.framework.configuration.TestConfiguration;
import dk.alexandra.fresco.framework.network.KryoNetNetwork;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.sce.SecureComputationEngine;
import dk.alexandra.fresco.framework.sce.SecureComputationEngineImpl;
import dk.alexandra.fresco.framework.sce.evaluator.BatchEvaluationStrategy;
import dk.alexandra.fresco.framework.sce.evaluator.BatchedProtocolEvaluator;
import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy;
import dk.alexandra.fresco.framework.sce.resources.ResourcePoolImpl;
import dk.alexandra.fresco.framework.util.DetermSecureRandom;
import dk.alexandra.fresco.logging.BatchEvaluationLoggingDecorator;
import dk.alexandra.fresco.logging.NetworkLoggingDecorator;
import dk.alexandra.fresco.logging.PerformanceLogger;
import dk.alexandra.fresco.logging.PerformanceLogger.Flag;
import dk.alexandra.fresco.logging.SCELoggingDecorator;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstract class which handles a lot of boiler plate testing code. This makes running a single test
 * using different parameters quite easy.
 */
public abstract class AbstractDummyBooleanTest {

  protected void runTest(
      TestThreadRunner.TestThreadFactory<ResourcePoolImpl, ProtocolBuilderBinary> f,
      EvaluationStrategy evalStrategy) throws Exception {
    runTest(f, evalStrategy, null);
  }
  
  protected void runTest(
      TestThreadRunner.TestThreadFactory<ResourcePoolImpl, ProtocolBuilderBinary> f,
      EvaluationStrategy evalStrategy, EnumSet<Flag> performanceFlags) throws Exception {    
  
    // The dummy protocol suite has the nice property that it can be run by just one player.
    int noOfParties = 1;
    List<Integer> ports = new ArrayList<Integer>(noOfParties);
    for (int i = 1; i <= noOfParties; i++) {
      ports.add(9000 + i * (noOfParties - 1));
    }

    Map<Integer, NetworkConfiguration> netConf =
        TestConfiguration.getNetworkConfigurations(noOfParties, ports);
    Map<Integer, TestThreadRunner.TestThreadConfiguration<ResourcePoolImpl, ProtocolBuilderBinary>> conf =
        new HashMap<Integer, TestThreadRunner.TestThreadConfiguration<ResourcePoolImpl, ProtocolBuilderBinary>>();
    Map<Integer, List<PerformanceLogger>> pls = new HashMap<>();
    for (int playerId : netConf.keySet()) {
      pls.put(playerId, new ArrayList<>());
      NetworkConfiguration partyNetConf = netConf.get(playerId);

      DummyBooleanProtocolSuite ps = new DummyBooleanProtocolSuite();

      BatchEvaluationStrategy<ResourcePoolImpl> strat = EvaluationStrategy.fromEnum(evalStrategy);
      if(performanceFlags != null && performanceFlags.contains(Flag.LOG_NATIVE_BATCH)) {
        strat = new BatchEvaluationLoggingDecorator<>(strat);
        pls.get(playerId).add((PerformanceLogger) strat);
      }
      ProtocolEvaluator<ResourcePoolImpl, ProtocolBuilderBinary> evaluator = 
          new BatchedProtocolEvaluator<>(strat);
      
      
      Network network = new KryoNetNetwork();
      if(performanceFlags != null && performanceFlags.contains(Flag.LOG_NETWORK)) {
        network = new NetworkLoggingDecorator(network);
        pls.get(playerId).add((PerformanceLogger) network);
      }
      network.init(partyNetConf, 1);
      
      ResourcePoolImpl rp = new ResourcePoolImpl(playerId, noOfParties, network, new Random(),
          new DetermSecureRandom());
      
      SecureComputationEngine<ResourcePoolImpl, ProtocolBuilderBinary> sce = new SecureComputationEngineImpl<>(ps, evaluator);
      if(performanceFlags != null && performanceFlags.contains(Flag.LOG_RUNTIME)) {
        sce = new SCELoggingDecorator<>(sce, ps);
        pls.get(playerId).add((PerformanceLogger) sce);
      }
      TestThreadRunner.TestThreadConfiguration<ResourcePoolImpl, ProtocolBuilderBinary> ttc =
          new TestThreadRunner.TestThreadConfiguration<>(sce, rp);
      conf.put(playerId, ttc);
    }
    TestThreadRunner.run(f, conf);
    for(Integer pId : pls.keySet()) {
      for(PerformanceLogger pl : pls.get(pId)) {
        pl.printPerformanceLog(pId);
        pl.reset();
      }
    }
  }
}
