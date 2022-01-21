package dummyrm;

import java.io.Serializable;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * DummyXAResource
 */
public class DummyXAResource implements XAResource, Serializable {

	private static final long serialVersionUID = 3790259992210119370L;
	
	/**
	 * Enumeration of Sleep Positions.
	 */
	public enum SleepPosition {
		None,
		At_Start,
		At_End,
		At_Prepare,
		At_Commit,
		At_Rollback,
		At_Forget
	};
	
	/**
	 * DummyXAResourceInfo.
	 */
	private DummyXAResourceInfo info;
	
	/**
	 * Transaction Timeout.
	 * @see #getTransactionTimeout()
	 * @see #setTransactionTimeout(int)
	 */
	private int timeout;
	
	/**
	 * Sleep Position.
	 */
	private SleepPosition sleepPosition;
	
	/**
	 * Sleep Duration.
	 */
	private long sleepDuration;

	/**
	 * Constructor
	 * @param name The name of Dummy XA Resource.
	 */
	public DummyXAResource( String name ) {
		this.info = new DummyXAResourceInfo( name );
		this.timeout = 0;
		this.sleepPosition = SleepPosition.None;
		this.sleepDuration = 30 * 1000;
	}
	
	/**
	 * Obtains DummyXAResourceInfo.
	 */
	public DummyXAResourceInfo getInfo() {
		return info;
	}

	/**
	 * Obtains Sleep Position.
	 */
	public SleepPosition getSleepPosition() {
		return this.sleepPosition;
	}

	/**
	 * Sets Sleep Position.
	 */
	public void setSleepPosition( SleepPosition sleepPosition ) {
		this.sleepPosition = sleepPosition;
	}

	/**
	 * Obtains Sleep Duration in milliseconds.
	 */
	public long getSleepDuration() {
		return this.sleepDuration;
	}

	/**
	 * Sets Sleep Duration in milliseconds.
	 */
	public void setSleepDuration( long sleepDuration ) {
		this.sleepDuration = sleepDuration;
	}
	
	// javax.transaction.xa.XAResource implementation --------------------------------------------------
	// https://docs.oracle.com/javase/8/docs/api/javax/transaction/xa/XAResource.html

	/**
	 * Commits the global transaction specified by xid.
	 * @see javax.transaction.xa.XAResource#commit(javax.transaction.xa.Xid, boolean)
	 */
	public void commit( Xid xid, boolean onePhase ) throws XAException {
		if ( sleepPosition == SleepPosition.At_Commit ) sleep( "commit" );
	}

	/**
	 * Ends the work performed on behalf of a transaction branch.
	 * @see javax.transaction.xa.XAResource#end(javax.transaction.xa.Xid, int)
	 */
	public void end( Xid Xid, int flags ) throws XAException {
		if ( sleepPosition == SleepPosition.At_End ) sleep( "end" );
	}

	/**
	 * Tells the resource manager to forget about a heuristically completed transaction branch.
	 * @see javax.transaction.xa.XAResource#forget(javax.transaction.xa.Xid)
	 */
	public void forget( Xid xid ) throws XAException {
		if ( sleepPosition == SleepPosition.At_Forget ) sleep( "forget" );
	}

	/**
	 * Obtains the current transaction timeout value set for this XAResource instance.
	 * @see javax.transaction.xa.XAResource#getTransactionTimeout()
	 */
	public int getTransactionTimeout() throws XAException {
		return this.timeout;
	}

	/**
	 * This method is called to determine if the resource manager instance represented by the target object is the same as the resource manager instance represented by the parameter xares.
	 * @see javax.transaction.xa.XAResource#isSameRM(javax.transaction.xa.XAResource)
	 */
	public boolean isSameRM( XAResource xares ) throws XAException {
		if ( xares instanceof DummyXAResource ) {
			return getInfo().equals( ((DummyXAResource) xares).getInfo() );
		}
		return false;
	}

	/**
	 * Ask the resource manager to prepare for a transaction commit of the transaction specified in xid.
	 * @see javax.transaction.xa.XAResource#prepare(javax.transaction.xa.Xid)
	 */
	public int prepare( Xid Xid ) throws XAException {
		if ( sleepPosition == SleepPosition.At_Prepare ) sleep( "prepare" );
		return XA_OK;
	}

	/**
	 * Obtains a list of prepared transaction branches from a resource manager.
	 * @see javax.transaction.xa.XAResource#recover(int)
	 */
	public Xid[] recover( int flag ) throws XAException {
		return new Xid[0];
	}

	
	/**
	 * Informs the resource manager to roll back work done on behalf of a transaction branch.
	 * @see javax.transaction.xa.XAResource#rollback(javax.transaction.xa.Xid)
	 */
	public void rollback( Xid xid ) throws XAException {
		if ( sleepPosition == SleepPosition.At_Rollback ) sleep( "rollback" );
	}
	
	/**
	 * Sets the current transaction timeout value for this XAResource instance
	 * @see javax.transaction.xa.XAResource#setTransactionTimeout(int)
	 */
	public boolean setTransactionTimeout( int seconds ) throws XAException {
		this.timeout = seconds;
		return true;
	}

	/**
	 * Starts work on behalf of a transaction branch specified in xid.
	 * @see javax.transaction.xa.XAResource#start(javax.transaction.xa.Xid, int)
	 */
	public void start( Xid Xid, int flags ) throws XAException {
		if ( sleepPosition == SleepPosition.At_Start ) sleep( "start" );
	}

	// Helper methods ----------------------------------------------------------------------------------
	
	private void printMessage( String msg ) {
		System.out.println( "DummyXAResource " + info.getName() + ": " + msg );
	}

	private void sleep( String method ) {
		printMessage( "Sleeping in " + method + ". (" + ( sleepDuration / 1000 ) + "s)" );
		try {
			Thread.sleep( sleepDuration );
		} catch (InterruptedException interruptedexception) {
		}
		printMessage( "Waked up." );
	}

	public String toString() {
		return "DummyXAResource: " + info.toString();
	}
	
}
