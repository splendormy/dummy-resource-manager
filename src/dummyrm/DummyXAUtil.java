package dummyrm;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.ibm.tx.jta.ExtendedTransactionManager;
import com.ibm.tx.jta.TransactionManagerFactory;

/**
 * DummyXAUtil
 */
public class DummyXAUtil {

	/**
	 * 
	 * @param name The name of Dummy XA Resource.
	 * @param sleepPosition Sleep Position of created Dummy XA Resource.
	 * @param sleepDuration Sleep Duration of created Dummy XA Resource.
	 * @return DummyXAResource
	 */
	public static DummyXAResource enlist( String name,
			                              DummyXAResource.SleepPosition sleepPosition,
			                              int sleepDuration ) throws RollbackException, SystemException {
		
		// Creates DummyXAResource
		DummyXAResource res = new DummyXAResource( name );
		res.setSleepPosition( sleepPosition );
		res.setSleepDuration( sleepDuration );
		
		// TransactionManagerFactory
		// https://www.ibm.com/docs/en/was-liberty/base?topic=SSEQTP_liberty/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.api.transaction_1.1-javadoc/com/ibm/tx/jta/TransactionManagerFactory.html
		// ExtendedTransactionManager
		// https://www.ibm.com/docs/en/was-liberty/base?topic=SSEQTP_liberty/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.api.transaction_1.1-javadoc/com/ibm/tx/jta/ExtendedTransactionManager.html
		
		// Gets ExtendedTransactionManager
		ExtendedTransactionManager etm = TransactionManagerFactory.getTransactionManager();
		
		// Register DummyXAResourceInfo to ExtendedTransactionManager
		int id = etm.registerResourceInfo( DummyXAResourceFactory.class.getName(), res.getInfo() );
		
		// Enlists DummyXAResource.
		etm.enlist( res, id );
		
		return res;
	}

}
