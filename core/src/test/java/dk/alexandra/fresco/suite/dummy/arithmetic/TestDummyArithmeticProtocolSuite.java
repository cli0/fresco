package dk.alexandra.fresco.suite.dummy.arithmetic;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.network.NetworkingStrategy;
import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.lib.arithmetic.BasicArithmeticTests;
import dk.alexandra.fresco.lib.arithmetic.MiMCTests;
import dk.alexandra.fresco.lib.arithmetic.ParallelAndSequenceTests.TestSumAndProduct;
import dk.alexandra.fresco.lib.arithmetic.SearchingTests;
import dk.alexandra.fresco.lib.arithmetic.SortingTests;
import dk.alexandra.fresco.lib.collections.Matrix;
import dk.alexandra.fresco.lib.collections.io.CloseListTests;
import dk.alexandra.fresco.lib.collections.io.CloseMatrixTests;
import dk.alexandra.fresco.lib.collections.permute.PermuteRows;
import dk.alexandra.fresco.lib.collections.permute.PermuteRowsTests;
import dk.alexandra.fresco.lib.collections.relational.LeakyAggregationTests;
import dk.alexandra.fresco.lib.collections.shuffle.ShuffleRowsTests;
import dk.alexandra.fresco.lib.compare.CompareTests;
import dk.alexandra.fresco.lib.conditional.ConditionalSelectTests;
import dk.alexandra.fresco.lib.conditional.ConditionalSwapNeighborsTests;
import dk.alexandra.fresco.lib.conditional.ConditionalSwapRowsTests;
import dk.alexandra.fresco.lib.conditional.SwapIfTests;
import dk.alexandra.fresco.lib.debug.ArithmeticDebugTests;
import dk.alexandra.fresco.lib.list.EliminateDuplicatesTests;
import dk.alexandra.fresco.lib.lp.LPBuildingBlockTests;
import dk.alexandra.fresco.lib.math.integer.binary.BinaryOperationsTests;
import dk.alexandra.fresco.lib.math.integer.division.DivisionTests;
import dk.alexandra.fresco.lib.math.integer.exp.ExponentiationTests;
import dk.alexandra.fresco.lib.math.integer.linalg.LinAlgTests;
import dk.alexandra.fresco.lib.math.integer.log.LogTests;
import dk.alexandra.fresco.lib.math.integer.min.MinTests;
import dk.alexandra.fresco.lib.math.integer.sqrt.SqrtTests;
import dk.alexandra.fresco.lib.math.integer.stat.StatisticsTests;
import dk.alexandra.fresco.lib.math.polynomial.PolynomialTests;
import dk.alexandra.fresco.lib.statistics.CreditRaterTest;
import dk.alexandra.fresco.lib.statistics.DeaSolver.AnalysisType;
import dk.alexandra.fresco.lib.statistics.DeaSolverTests.RandomDataDeaTest;
import dk.alexandra.fresco.lib.statistics.DeaSolverTests.TestDeaFixed1;
import dk.alexandra.fresco.logging.PerformanceLogger;
import java.math.BigInteger;
import java.util.ArrayList;
import org.junit.Test;


public class TestDummyArithmeticProtocolSuite extends AbstractDummyArithmeticTest {

  @Test
  public void test_Input_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestInput<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_OutputToTarget_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestOutputToSingleParty<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_AddPublicValue_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestAddPublicValue<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_KnownSInt_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestKnownSInt<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_MultAndAdd_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestSimpleMultAndAdd<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void testSumAndOutputSequential() throws Exception {
    runTest(new BasicArithmeticTests.TestSumAndMult<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void testSumAndProduct() throws Exception {
    runTest(new TestSumAndProduct<>(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET,
        1);
  }

  @Test
  public void test_MinInfFrac_Sequential() throws Exception {
    runTest(new BasicArithmeticTests.TestMinInfFrac<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_MinInfFrac_SequentialBatched() throws Exception {
    runTest(new BasicArithmeticTests.TestMinInfFrac<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_compareLT_Sequential() throws Exception {
    runTest(new CompareTests.TestCompareLT<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_compareEQ_Sequential() throws Exception {
    runTest(new CompareTests.TestCompareEQ<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_isSorted() throws Exception {
    runTest(new SortingTests.TestIsSorted<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_compareAndSwap() throws Exception {
    runTest(new SortingTests.TestCompareAndSwap<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_Sort() throws Exception {
    runTest(new SortingTests.TestSort<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_Big_Sort() throws Exception {
    runTest(new SortingTests.TestBigSort<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  // Statistics


  // Creditrater
  @Test
  public void test_CreditRater_Single_Value_2_parties() throws Exception {
    int[] values = {2};
    int[][] intervals = {{1, 3}};
    int[][] scores = {{10, 100, 1000}};
    runTest(new CreditRaterTest.TestCreditRater<>(values, intervals, scores),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_CreditRater_Single_Value_3_parties() throws Exception {
    int[] values = {2};
    int[][] intervals = {{1, 3}};
    int[][] scores = {{10, 100, 1000}};
    runTest(new CreditRaterTest.TestCreditRater<>(values, intervals, scores),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 3);
  }

  @Test
  public void test_CreditRater_multi_Value_2_parties() throws Exception {
    int[] values = {2, 2, 2};
    int[][] intervals = {{1, 3}, {0, 5}, {0, 1}};
    int[][] scores = {{10, 100, 1000}, {10, 100, 1000}, {10, 100, 1000}};
    runTest(new CreditRaterTest.TestCreditRater<>(values, intervals, scores),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  // DEASolver
  @Test
  public void test_DEASolver_2_parties() throws Exception {
    runTest(new RandomDataDeaTest<>(5, 2, 10, 1, AnalysisType.INPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_DEASolver_3_parties() throws Exception {
    runTest(new RandomDataDeaTest<>(2, 2, 10, 1, AnalysisType.INPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 3);
  }

  @Test
  public void test_DEASolver_multiple_queries_2_parties() throws Exception {
    runTest(new RandomDataDeaTest<>(5, 2, 10, 2, AnalysisType.INPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_DEASolver_single_input_2_parties() throws Exception {
    runTest(new RandomDataDeaTest<>(1, 2, 10, 1, AnalysisType.INPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_DEASolver_single_input_and_output_2_parties() throws Exception {
    runTest(new RandomDataDeaTest<>(1, 1, 10, 1, AnalysisType.INPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_DEASolver_output_efficiency_2_parties() throws Exception {
    runTest(new RandomDataDeaTest<>(5, 1, 10, 1, AnalysisType.OUTPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_DEASolver_multiple_queries__output_2_parties() throws Exception {
    runTest(new RandomDataDeaTest<>(5, 2, 10, 2, AnalysisType.OUTPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_DEASolver_fixedData1() throws Exception {
    runTest(new TestDeaFixed1<>(AnalysisType.OUTPUT_EFFICIENCY),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  // lib.conditional

  @Test
  public void test_conditional_select_left() throws Exception {
    runTest(ConditionalSelectTests.testSelectLeft(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_conditional_select_right() throws Exception {
    runTest(ConditionalSelectTests.testSelectRight(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_swap_yes() throws Exception {
    runTest(SwapIfTests.testSwapYes(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET,
        1);
  }

  @Test
  public void test_swap_no() throws Exception {
    runTest(SwapIfTests.testSwapNo(), EvaluationStrategy.SEQUENTIAL, NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_swap_rows_yes() throws Exception {
    runTest(ConditionalSwapRowsTests.testSwapYes(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_swap_rows_no() throws Exception {
    runTest(ConditionalSwapRowsTests.testSwapNo(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_swap_neighbors_yes() throws Exception {
    runTest(ConditionalSwapNeighborsTests.testSwapYes(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_swap_neighbors_no() throws Exception {
    runTest(ConditionalSwapNeighborsTests.testSwapNo(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  // lib.collections

  @Test
  public void test_close_empty_list() throws Exception {
    runTest(new CloseListTests.TestCloseEmptyList<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_close_list() throws Exception {
    runTest(new CloseListTests.TestCloseEmptyList<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_close_empty_matrix() throws Exception {
    runTest(new CloseMatrixTests.TestCloseEmptyMatrix<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_close_matrix() throws Exception {
    runTest(new CloseMatrixTests.TestCloseAndOpenMatrix<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Test_Is_Sorted() throws Exception {
    runTest(new SearchingTests.TestIsSorted<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_permute_empty_rows() throws Exception {
    runTest(PermuteRowsTests.permuteEmptyRows(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_permute_rows() throws Exception {
    runTest(PermuteRowsTests.permuteRows(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void test_permute_rows_non_power_of_two() throws Throwable {
    ArrayList<ArrayList<DRes<SInt>>> fakeRows = new ArrayList<>();
    Matrix<DRes<SInt>> fakeMatrix = new Matrix<>(3, 2, fakeRows);
    new PermuteRows(() -> fakeMatrix, new int[] {}, 1, true).buildComputation(null);
  }

  @Test
  public void test_shuffle_rows_two_parties() throws Exception {
    runTest(ShuffleRowsTests.shuffleRowsTwoParties(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_shuffle_rows_three_parties() throws Exception {
    runTest(ShuffleRowsTests.shuffleRowsThreeParties(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 3);
  }

  @Test
  public void test_shuffle_rows_empty() throws Exception {
    runTest(ShuffleRowsTests.shuffleRowsEmpty(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_leaky_aggregate_two() throws Exception {
    runTest(LeakyAggregationTests.aggregate(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_leaky_aggregate_unique_keys_two() throws Exception {
    runTest(LeakyAggregationTests.aggregateUniqueKeys(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_leaky_aggregate_three() throws Exception {
    runTest(LeakyAggregationTests.aggregate(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 3);
  }

  @Test
  public void test_leaky_aggregate_empty() throws Exception {
    runTest(LeakyAggregationTests.aggregateEmpty(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  //

  @Test
  public void test_MiMC_DifferentPlainTexts() throws Exception {
    runTest(new MiMCTests.TestMiMCDifferentPlainTexts<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_MiMC_EncSameEnc() throws Exception {
    runTest(new MiMCTests.TestMiMCEncSameEnc<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_MiMC_EncDec() throws Exception {
    BigInteger mod = new BigInteger(
        "2582249878086908589655919172003011874329705792829223512830659356540647622016841194629645353280137831435903171972747493557");
    runTest(new MiMCTests.TestMiMCEncDec<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1, mod, null);
  }

  @Test
  public void test_MiMC_EncDecFixedRounds() throws Exception {
    BigInteger mod = new BigInteger(
        "2582249878086908589655919172003011874329705792829223512830659356540647622016841194629645353280137831435903171972747493557");
    runTest(new MiMCTests.TestMiMCEncDecFixedRounds<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1, mod, null);
  }

  @Test
  public void test_MiMC_Deterministically() throws Exception {
    BigInteger mod = new BigInteger(
        "2582249878086908589655919172003011874329705792829223512830659356540647622016841194629645353280137831435903171972747493557");
    runTest(new MiMCTests.TestMiMCEncryptsDeterministically<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 1, mod, null);
  }

  // lib.list
  @Test
  public void test_findDuplicatesOne() throws Exception {
    runTest(new EliminateDuplicatesTests.TestFindDuplicatesOne<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  // lib.lp
  @Test
  public void test_LPSolverEntering() throws Exception {
    runTest(new LPBuildingBlockTests.TestEnteringVariable<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_LPSolverBlandEntering() throws Exception {
    runTest(new LPBuildingBlockTests.TestBlandEnteringVariable<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }


  // lib.math.integer.binary
  @Test
  public void test_Right_Shift() throws Exception {
    runTest(new BinaryOperationsTests.TestRightShift<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Bit_Length() throws Exception {
    runTest(new BinaryOperationsTests.TestBitLength<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 1);
  }

  // Math tests

  @Test
  public void test_euclidian_division() throws Exception {
    runTest(new DivisionTests.TestEuclidianDivision<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_euclidian_division_large_divisor() throws Exception {
    runTest(new DivisionTests.TestEuclidianDivisionLargeDivisor<>(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_ss_division() throws Exception {
    runTest(new DivisionTests.TestSecretSharedDivision<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 1);
  }

  @Test
  public void test_Exponentiation() throws Exception {
    runTest(new ExponentiationTests.TestExponentiation<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }


  @Test
  public void test_ExponentiationOpenExponent() throws Exception {
    runTest(new ExponentiationTests.TestExponentiationOpenExponent<>(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_ExponentiationOpenBase() throws Exception {
    runTest(new ExponentiationTests.TestExponentiationOpenBase<>(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test()
  public void test_ExponentiationZeroExponent() throws Exception {
    runTest(new ExponentiationTests.TestExponentiationZeroExponent<>(),
        EvaluationStrategy.SEQUENTIAL_BATCHED, NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_InnerProductClosed() throws Exception {
    runTest(new LinAlgTests.TestInnerProductClosed<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_InnerProductOpen() throws Exception {
    runTest(new LinAlgTests.TestInnerProductOpen<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Logarithm() throws Exception {
    runTest(new LogTests.TestLogarithm<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Minimum_Protocol_2_parties() throws Exception {
    runTest(new MinTests.TestMinimumProtocol<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Min_Inf_Frac_2_parties() throws Exception {
    runTest(new MinTests.TestMinInfFraction<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Min_Inf_Frac_Trivial_2_parties() throws Exception {
    runTest(new MinTests.TestMinInfFractionTrivial<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_sqrt() throws Exception {
    runTest(new SqrtTests.TestSquareRoot<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Exiting_Variable_2_parties() throws Exception {
    runTest(new StatisticsTests.TestStatistics<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Exiting_Variable_3_parties() throws Exception {
    runTest(new StatisticsTests.TestStatistics<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 3);
  }

  @Test
  public void test_Exiting_Variable_No_Mean_2_parties() throws Exception {
    runTest(new StatisticsTests.TestStatisticsNoMean<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_Polynomial_Evaluator_2_parties() throws Exception {
    runTest(new PolynomialTests.TestPolynomialEvaluator<>(), EvaluationStrategy.SEQUENTIAL_BATCHED,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_debug_tools() throws Exception {
    runTest(new ArithmeticDebugTests.TestArithmeticOpenAndPrint<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2);
  }

  @Test
  public void test_performance_logger() throws Exception {
    BigInteger mod = new BigInteger(
        "6703903964971298549787012499123814115273848577471136527425966013026501536706464354255445443244279389455058889493431223951165286470575994074291745908195329");
    runTest(new CompareTests.TestCompareLT<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2, mod, PerformanceLogger.Flag.ALL_OPTS);
    for(Integer pId : DummyArithmeticBuilderFactory.performanceLoggers.keySet()) {
      for(PerformanceLogger pl : DummyArithmeticBuilderFactory.performanceLoggers.get(pId)) {
        pl.printPerformanceLog(pId);
      }
    }
  }
  
  @Test
  public void test_performance_logger_network() throws Exception {
    BigInteger mod = new BigInteger(
        "6703903964971298549787012499123814115273848577471136527425966013026501536706464354255445443244279389455058889493431223951165286470575994074291745908195329");
    runTest(new BasicArithmeticTests.TestInput<>(), EvaluationStrategy.SEQUENTIAL,
        NetworkingStrategy.KRYONET, 2, mod, PerformanceLogger.Flag.ALL_OPTS);
  }
}
