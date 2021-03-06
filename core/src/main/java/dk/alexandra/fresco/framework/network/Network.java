package dk.alexandra.fresco.framework.network;

import dk.alexandra.fresco.framework.configuration.NetworkConfiguration;
import java.io.IOException;

/**
 * A player's view of a network. Should be used when sending messages between MPC parties.
 * 
 */
public interface Network {

  /**
   * Initializes the network using the given {@code conf} and prepares {@code channelAmount}
   * channels towards each party. This method does not actually connect any parties to each other.
   * For that, call {@link #connect(int)}.
   * 
   * @param conf The network configuration to use.
   * @param channelAmount The amount of channels towards each party.
   */
  void init(NetworkConfiguration conf, int channelAmount);

  /**
   * Attempts to connect with other players.
   * 
   * Blocks until connection is successful.
   * 
   * @param timeoutMillis The amount of milliseconds before an IOException is thrown in case of
   *        connection failure.
   */
  void connect(int timeoutMillis) throws IOException;

  /**
   * Send data to other party with id partyId. This network will automatically figure out the size
   * on the receiving end.
   * 
   * @param channelId the channel to send data over.
   * @param partyId the party to send data to
   * @param data the data to send
   * @throws IOException thrown if the connection has problems.
   */
  void send(int channelId, int partyId, byte[] data) throws IOException;

  /**
   * Blocking call that only returns once the data has been fully received and deserialized.
   * 
   * @param channelId the channel to receive from
   * @param partyId the party to receive from
   * @return the data send by the given partyId through the given channel
   * @throws IOException if the channel times out or other connection issues occurs.
   */
  byte[] receive(int channelId, int partyId) throws IOException;

  /**
   * Shuts down the network - when returning from this method it is expected that the ports used are
   * accesible again.
   * 
   * @throws IOException If the connection could not be shut down.
   */
  public void close() throws IOException;
}
