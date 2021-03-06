package dk.alexandra.fresco.suite.spdz;

import dk.alexandra.fresco.framework.sce.resources.storage.FilebasedStreamedStorageImpl;
import dk.alexandra.fresco.framework.sce.resources.storage.InMemoryStorage;
import dk.alexandra.fresco.framework.sce.resources.storage.Storage;
import dk.alexandra.fresco.framework.sce.resources.storage.StreamedStorage;
import dk.alexandra.fresco.framework.sce.resources.storage.exceptions.NoMoreElementsException;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzElement;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzTriple;
import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestStorage {

  private BigInteger modulus;

  @Before
  public void init() {
    modulus = new BigInteger(
        "6703903964971298549787012499123814115273848577471136527425966013026501536706464354255445443244279389455058889493431223951165286470575994074291745908195329");
  }

  @Test
  public void testInMemoryStorage() {
    Storage storage = new InMemoryStorage();
    testStorage(storage);
    testStoreBigInteger(storage);
  }


  @Test
  public void testFilebasedStorage() throws NoMoreElementsException {
    StreamedStorage storage = new FilebasedStreamedStorageImpl(new InMemoryStorage());
    testStorage(storage);
    testStoreBigInteger(storage);
    testStreamedStorage(storage);
    File f = new File("testName");
    if (f.exists()) {
      f.delete();
    }
  }

  private void testStreamedStorage(StreamedStorage storage) throws NoMoreElementsException {
    storage.putNext("testName", BigInteger.TEN);
    Serializable o = storage.getNext("testName");
    Assert.assertEquals(BigInteger.TEN, o);
  }

  public void testStorage(Storage storage) {
    SpdzElement a = new SpdzElement(BigInteger.ONE, BigInteger.ZERO, modulus);
    SpdzTriple o1 = new SpdzTriple(a, a, a);

    storage.putObject("test", "key", o1);

    SpdzTriple o2 = storage.getObject("test", "key");
    Assert.assertEquals(o1, o2);
  }

  public void testStoreBigInteger(Storage storage) {
    BigInteger o1 = BigInteger.ONE;

    storage.putObject("test", "key", o1);

    BigInteger o2 = storage.getObject("test", "key");
    Assert.assertEquals(o1, o2);
  }
}
