package dk.alexandra.fresco.framework;

/**
 * FRESCO's view of a MPC party. 
 */
public class Party {

	private final int id;
	private final int port;
	private final String host;
	//Secret shared key used to communicate with this party. Can be null
	private String secretSharedKey;

	public Party(int id, String host, int port) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.secretSharedKey = null;
	}
	
	/**
	 * 
	 * @param id
	 * @param host
	 * @param port
	 * @param secretSharedKey Base64 encoded aes key
	 */
	public Party(int id, String host, int port, String secretSharedKey) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.secretSharedKey = secretSharedKey;
	}

	public String getHostname() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public int getPartyId() {
		return this.id;
	}
	
	public String getSecretSharedKey() {
		return this.secretSharedKey;
	}
	
	public void setSecretSharedKey(String secretSharedKey) {
		this.secretSharedKey = secretSharedKey;		
	}

	@Override
	public String toString() {
		if(secretSharedKey == null) {
			return "Party(" + this.id + ", " + host + ":" + port + ")";
		} else {
			return "Party(" + this.id + ", " + host + ":" + port +", ssKey: "+secretSharedKey+")";
		}
	}	

}
