package dummyrm;

import java.io.Serializable;

/**
 * DummyXAResourceInfo, XAResourceInfo for DummyXAResource.
 */
public class DummyXAResourceInfo implements Serializable {

	private static final long serialVersionUID = 6921750516715608880L;

	/**
	 * The name of Dummy XA Resource.
	 */
	private String name;

	/**
	 * Default constructor.
	 */
	DummyXAResourceInfo() {
	}
	
	/**
	 * Constructor.
	 */
	DummyXAResourceInfo( String name ) {
		this.name = name;
	}

	/**
	 * Obtains the name of Dummy XA Resource.
	 */
	String getName() {
		return this.name;
	}

	/**
	 * Sets the name of Dummy XA Resource.
	 */
	void setName( String name ) {
		this.name = name;
	}

	// Helper methods ----------------------------------------------------------------------------------

	public boolean equals( Object obj ) {
		if ( obj instanceof DummyXAResourceInfo ) {
			return ((DummyXAResourceInfo) obj).getName().equals( this.name );
		} else {
			return false;
		}
	}

	public String toString() {
		return this.name;
	}

}
