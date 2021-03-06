package dk.alexandra.fresco.framework;

import dk.alexandra.fresco.framework.builder.ProtocolBuilder;
import dk.alexandra.fresco.framework.sce.resources.ResourcePool;
import dk.alexandra.fresco.suite.ProtocolSuite;
import java.io.IOException;

/**
 * An evaluator is responsible for evaluating each native gate. Every protocol is reduced to a
 * number of native gates. These are then evaluated in batches (the amount of native protocols per
 * batch can be configured by the user). 
 * 
 * @param <ResourcePoolT> The type of resource pool
 * @param <Builder> The type of builder this evaluator supports
 */
public interface ProtocolEvaluator<ResourcePoolT extends ResourcePool, Builder extends ProtocolBuilder> {

  /**
   * Evaluates all gates produced by a GateProducer.
   *
   * @param protocolProducer the protocol producer to evaluate
   * @param resourcePool the resource pool (for other resources than network)
   * @throws IOException inheritly from the network
   */
  void eval(ProtocolProducer protocolProducer, ResourcePoolT resourcePool) throws IOException;

  /**
   * Set the protocol invocation which the gate evaluator should call.
   *
   * @param pii
   */
  void setProtocolInvocation(ProtocolSuite<ResourcePoolT, Builder> pii);

  /**
   * Sets the maximum batch size. If not called, the default will be 4096.
   *
   * @param maxBatchSize
   */
  void setMaxBatchSize(int maxBatchSize);

}
