package dk.alexandra.fresco.framework.builder.numeric;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.builder.ComputationDirectory;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.lib.lp.Matrix;
import java.io.PrintStream;
import java.util.List;

public interface Debug extends ComputationDirectory {

  /**
   * When evaluated, opens the given SInt and prints it to the given stream.
   * 
   * @param label Message/headline which appears before the SInts.
   * @param number The SInt to print.
   * @param stream The stream to print to.
   */
  public void openAndPrint(String label, DRes<SInt> number, PrintStream stream);

  /**
   * When evaluated, opens the given SInt vector and prints it to the given stream.
   * 
   * @param label Message/headline which appears before the SInts.
   * @param vector The SInts to print.
   * @param stream The stream to print to.
   */
  public void openAndPrint(String label, List<DRes<SInt>> vector, PrintStream stream);

  /**
   * When evaluated, opens the given SInt matrix and prints it to the given stream.
   * 
   * @param label Message/headline which appears before the SInts.
   * @param matrix The SInts to print.
   * @param stream The stream to print to.
   */
  public void openAndPrint(String label, Matrix<DRes<SInt>> matrix, PrintStream stream);

  /**
   * Prints the given message when evaluated.
   * 
   * @param message The message to print.
   * @param stream The stream to print to.
   */
  public void marker(String message, PrintStream stream);
}
