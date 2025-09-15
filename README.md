# dummy-resource-manager

Java EE version of Dummy Resource Manager (Dummy XA Resource) for WebSphere Liberty.

**Jakarta EE** version is [here](https://github.com/splendormy/jakarta-dummy-resource-manager).

## How to install (setup)

Place the following files in the specified directories. You might need to create these directories.

* Place `dummy-resource-manager.jar` in `<wlp_root>/usr/extension/lib` directory.
* Place `dummy-resource-manager.mf` in `<wlp_root>/usr/extension/lib/features` directory.

Add the following lines in the WebShpere Liberty Server Configuration file (`server.xml`).

    <featureManager>
        <feature>usr:dummy-resource-manager</feature>
    </featureManager>

## How to use

By invoking `dummyrm.DummyXAUtil.enlist(...)` in a global transaction scope,
you can register a Dummy XA Resource with the global transaction.

To register a Dummy XA Resource, `dummyrm.DummyXAUtil.enlist(...)` uses `com.ibm.tx.jta.TransactionManagerFactory` and `com.ibm.tx.jta.ExtendedTransactionManager` provided by WebSphere Liberty.

`dummyrm.DummyXAUtil.enlist(...)` has following three parameters.

1. name: The name of Dummy XA Resource
1. sleepPosition: Sleep Position of created Dummy XA Resource. Specify using `DummyXAResource.SleepPosition.At_xxx`.
2. sleepDuration: Sleep Duration of created Dummy XA Resource. Specify in millisecond.

A Dummy XA Resource can sleep at the specified method.
If you don't want a created Dummy XA Resource to sleep in any method, specify `DummyXAResource.SleepPosition.At_None`  as the second parameter (sleepPosition). 

Sample codes are below.

    import javax.naming.InitialContext;
    import javax.transaction.UserTransaction;
    import dummyrm.DummyXAResource
    import dummyrm.DummyXAUtil;

    InitialContext context = new InitialContext();
    UserTransaction ut = (UserTransaction) context.lookup( "java:comp/UserTransaction" );
    ut.begin();
    
    DummyXAUtil.enlist( "dummy1",
                        DummyXAResource.SleepPosition.At_Commit,
                        60 * 1000 );
	 
	 // some transactional works
    
    DummyXAUtil.enlist( "dummy2",
                        DummyXAResource.SleepPosition.At_None,
                        0 );
    
    ut.commit();


## Contents of this project

- dummy-resource-manager.mf: Feature Manifest file of dummy-resource-manager.
- dummy-resource-manager.jar: jar file of dummy-resource-manager.
- BundleContent/META-INF
    - MANIFEST.MF: Manifest file of .jar file.
- src/dummyrm
    - DummyXAResource.java: Dummy XA Resource (javax.transaction.xa.XAResource implementation).
    - DummyXAResourceFactory.java: com.ibm.tx.jta.XAResourceFactory implementation.
    - DummyXAResourceInfo.java: Information Object of Dummy XA Resource.
    - DummyXAUtil.java: Utility Class (See above).


## Links
- [javax.transaction.xa.XAResource implementation](https://docs.oracle.com/javase/8/docs/api/javax/transaction/xa/XAResource.html)
- [com.ibm.tx.jta.XAResourceFactory](https://www.ibm.com/docs/en/was-liberty/base?topic=SSEQTP_liberty/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.api.transaction_1.1-javadoc/com/ibm/tx/jta/XAResourceFactory.html)
- [com.ibm.tx.jta.TransactionManagerFactory](https://www.ibm.com/docs/en/was-liberty/base?topic=SSEQTP_liberty/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.api.transaction_1.1-javadoc/com/ibm/tx/jta/TransactionManagerFactory.html)
- [com.ibm.tx.jta.ExtendedTransactionManager](https://www.ibm.com/docs/en/was-liberty/base?topic=SSEQTP_liberty/com.ibm.websphere.javadoc.liberty.doc/com.ibm.websphere.appserver.api.transaction_1.1-javadoc/com/ibm/tx/jta/ExtendedTransactionManager.html)
