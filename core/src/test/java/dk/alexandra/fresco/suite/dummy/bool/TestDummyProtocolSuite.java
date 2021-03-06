package dk.alexandra.fresco.suite.dummy.bool;

import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy;
import dk.alexandra.fresco.framework.sce.resources.ResourcePoolImpl;
import dk.alexandra.fresco.lib.bool.BasicBooleanTests;
import dk.alexandra.fresco.lib.bool.ComparisonBooleanTests;
import dk.alexandra.fresco.lib.collections.sort.CollectionsSortingTests;
import dk.alexandra.fresco.lib.compare.CompareTests;
import dk.alexandra.fresco.lib.crypto.BristolCryptoTests;
import dk.alexandra.fresco.lib.debug.BinaryDebugTests;
import dk.alexandra.fresco.lib.field.bool.generic.FieldBoolTests;
import dk.alexandra.fresco.lib.math.bool.add.AddTests;
import dk.alexandra.fresco.lib.math.bool.log.LogTests;
import dk.alexandra.fresco.lib.math.bool.mult.MultTests;
import dk.alexandra.fresco.logging.PerformanceLogger.Flag;
import org.junit.Test;


/**
 * Various tests of the dummy protocol suite.
 *
 * Currently, we simply test that AES works using the dummy protocol suite.
 */
public class TestDummyProtocolSuite extends AbstractDummyBooleanTest {

  // Basic tests for boolean suites
  @Test
  public void test_basic_logic() throws Exception {
    runTest(new BasicBooleanTests.TestInput<>(true), EvaluationStrategy.SEQUENTIAL);
    runTest(new BasicBooleanTests.TestXOR<>(true), EvaluationStrategy.SEQUENTIAL);
    runTest(new BasicBooleanTests.TestAND<>(true), EvaluationStrategy.SEQUENTIAL);
    runTest(new BasicBooleanTests.TestNOT<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  // lib.field.bool.generic
  // Slightly more advanced protocols for lowlevel logic operations
  @Test
  public void test_XNor() throws Exception {
    runTest(new FieldBoolTests.TestXNorFromXorAndNot<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
    runTest(new FieldBoolTests.TestXNorFromOpen<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_OR() throws Exception {
    runTest(new FieldBoolTests.TestOrFromXorAnd<ResourcePoolImpl>(),
        EvaluationStrategy.SEQUENTIAL_BATCHED);
    runTest(new FieldBoolTests.TestOrFromCopyConst<ResourcePoolImpl>(),
        EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_NAND() throws Exception {
    runTest(new FieldBoolTests.TestNandFromAndAndNot<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
    runTest(new FieldBoolTests.TestNandFromOpen<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_AndFromCopy() throws Exception {
    runTest(new FieldBoolTests.TestAndFromCopyConst<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  // lib.math.bool
  @Test
  public void test_One_Bit_Half_Adder() throws Exception {
    runTest(new AddTests.TestOnebitHalfAdder<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_One_Bit_Full_Adder() throws Exception {
    runTest(new AddTests.TestOnebitFullAdder<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_Binary_Adder() throws Exception {
    runTest(new AddTests.TestFullAdder<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_Binary_BitIncrementAdder() throws Exception {
    runTest(new AddTests.TestBitIncrement<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_Binary_Mult() throws Exception {
    runTest(new MultTests.TestBinaryMult<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  // Bristol tests
  @Test
  public void test_Mult32x32_Sequential() throws Exception {
    runTest(new BristolCryptoTests.Mult32x32Test<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_AES_Sequential() throws Exception {
    runTest(new BristolCryptoTests.AesTest<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_AES_SequentialBatched() throws Exception {
    runTest(new BristolCryptoTests.AesTest<>(true), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_DES_Sequential() throws Exception {
    runTest(new BristolCryptoTests.DesTest<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_MD5_Sequential() throws Exception {
    runTest(new BristolCryptoTests.MD5Test<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_SHA1_Sequential() throws Exception {
    runTest(new BristolCryptoTests.Sha1Test<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_SHA256_Sequential() throws Exception {
    runTest(new BristolCryptoTests.Sha256Test<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_basic_logic_all_in_one() throws Exception {
    runTest(new BasicBooleanTests.TestBasicProtocols<>(true), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_comparison() throws Exception {
    runTest(new ComparisonBooleanTests.TestGreaterThan<>(), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_comparison_unequal_length() throws Exception {
    runTest(new ComparisonBooleanTests.TestGreaterThanUnequalLength<>(),
        EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_equality() throws Exception {
    runTest(new ComparisonBooleanTests.TestEquality<>(), EvaluationStrategy.SEQUENTIAL);
  }

  // collections.sort
  @Test
  public void test_Uneven_Odd_Even_Merge_2_parties() throws Exception {
    runTest(new CollectionsSortingTests.TestOddEvenMerge<>(), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_Keyed_Compare_And_Swap_2_parties() throws Exception {
    runTest(new CollectionsSortingTests.TestKeyedCompareAndSwap<>(), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_Compare_And_Swap() throws Exception {
    runTest(new CompareTests.CompareAndSwapTest<>(), EvaluationStrategy.SEQUENTIAL);
  }

  @Test
  public void test_Debug_Marker() throws Exception {
    runTest(new BinaryDebugTests.TestBinaryOpenAndPrint<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }


  @Test
  public void test_Binary_Log_Nice() throws Exception {
    runTest(new LogTests.TestLogNice<>(), EvaluationStrategy.SEQUENTIAL_BATCHED);
  }

  @Test
  public void test_Logging() throws Exception {
    runTest(new CollectionsSortingTests.TestKeyedCompareAndSwap<>(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, Flag.ALL_OPTS);
    DummyBooleanBuilderFactory.loggerInstance.printPerformanceLog(1);
    DummyBooleanBuilderFactory.loggerInstance.reset();
  }
}
