package dk.alexandra.fresco.services;

import java.math.BigInteger;
import java.util.List;

import dk.alexandra.fresco.suite.spdz.datatypes.SpdzElement;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzInputMask;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzSInt;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzTriple;

public interface DataGenerator {

	/**
	 * 
	 * @return The global modulus used.
	 */
	public BigInteger getModulus();

	/**
	 * 
	 * @param partyId
	 *            The Id of the party whose share you want.
	 * @return The share of alpha belonging to the given partyId.
	 */
	public BigInteger getAlpha(int partyId);

	/**
	 * Adds the given shares of triples to a blocking queue.
	 * 
	 * @param triples
	 *            The triples to add
	 * @throws InterruptedException
	 *             Happens if the thread got interrupted while trying to add to
	 *             a full queue.
	 */
	public void addTriples(List<SpdzTriple[]> triples, int thread) throws InterruptedException;

	/**
	 * Fetches the next {@code amount} of triples.
	 * 
	 * @param amount The amount to get from the queue.
	 * @param partyId
	 *            The party whose shares should be given.
	 * @param thread
	 *            The VM thread number
	 * @return An array containing the given number of triples.
	 * @throws InterruptedException
	 *             Happens if the thread got interrupted while trying to fetch from an empty queue.
	 */
	public SpdzTriple[] getTriples(int amount, int partyId, int thread) throws InterruptedException;

	/**
	 * Same behavior as {@link #addTriples(List, int)} but for bits instead.
	 */
	public void addBits(List<SpdzSInt[]> bits, int thread) throws InterruptedException;

	/**
	 * Same behavior as {@link #getTriples(int, int, int)} but for bits instead.
	 */
	public SpdzElement[] getBits(int amount, int partyId, int thread) throws InterruptedException;

	/**
	 * Same behavior as {@link #addTriples(List, int)} but for exponentiation pipes instead.
	 */
	public void addExpPipes(List<SpdzSInt[][]> expPipes, int thread) throws InterruptedException;

	/**
	 * Same behavior as {@link #getTriples(int, int, int)} but for exponentiation pipes instead.
	 */
	public SpdzElement[][] getExpPipes(int amount, int partyId, int thread) throws InterruptedException;

	/**
	 * Same behavior as {@link #addTriples(List, int)} but for input masks instead.
	 */
	public void addInputMasks(int i, List<SpdzInputMask[]> inpMasks, int thread) throws InterruptedException;

	/**
	 * Same behavior as {@link #getTriples(int, int, int)} but for input masks instead.
	 */
	public SpdzInputMask[] getInputMasks(int amount, int partyId, int towardsPartyId, int thread)
			throws InterruptedException;

	/**
	 * Clears cache and resets the generator. All parties must call this before
	 * it happens.
	 * 
	 * @param partyId
	 * @return True if accepted.
	 */
	public Boolean reset(int partyId);
}
