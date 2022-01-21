package dummyrm;

import java.io.Serializable;

import javax.transaction.xa.XAResource;

import com.ibm.tx.jta.XAResourceFactory;

/**
 * DummyXAResourceFactory
 */
public class DummyXAResourceFactory implements XAResourceFactory, Serializable {

	private static final long serialVersionUID = 2671232681298274892L;

	/**
	 * Default constructor.
	 */
	public DummyXAResourceFactory() {
	}

	// com.ibm.tx.jta.XAResourceFactory implementation -------------------------------------------------
	// https://www.ibm.com/docs/en/was-liberty/base?topic=SSEQTP_liberty/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.api.transaction_1.1-javadoc/com/ibm/tx/jta/XAResourceFactory.html

	/**
	 * Given XAResourceInfo, the XAResourceFactory produce a XAResource object.
	 * @see com.ibm.tx.jta.XAResourceFactory#getXAResource(java.io.Serializable)
	 */
	public XAResource getXAResource( Serializable xaresinfo ) {
		DummyXAResourceInfo inf = (DummyXAResourceInfo) xaresinfo;
		DummyXAResource     res = new DummyXAResource( inf.getName() );
		return res;
	}
	
	/**
	 * Destroy the XAResource object.
	 * @see com.ibm.tx.jta.XAResourceFactory#destroyXAResource(javax.transaction.xa.XAResource)
	 */
	public void destroyXAResource( XAResource xaRes ) {
		return;
	}

	// Helper methods ----------------------------------------------------------------------------------
	
	public boolean equals( Object obj ) {
		if ( obj instanceof DummyXAResourceFactory ) {
			return true;
		} else {
			return false;
		}
	}

}
