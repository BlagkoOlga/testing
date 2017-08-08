# testing

Log after running test:

INFO:[2017-08-08 17:20:33,217]  - Step 1 - Connect to DB
 INFO:[2017-08-08 17:20:34,032]  - Step 2 - create client with balance
 INFO:[2017-08-08 17:20:34,083]  - Step 3 - Get user services
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
 INFO:[2017-08-08 17:20:35,535]  - Step 4 - Get all services
 INFO:[2017-08-08 17:20:35,563]  - Step 5 - found service isn't connected to client and save id and cost
 INFO:[2017-08-08 17:20:35,563]  - Step 6 - connected service to client
Service id 1 INFO:[2017-08-08 17:20:35,589]  - Step 7 - wait for services connected
ID 0
com.jayway.awaitility.core.ConditionTimeoutException: Condition defined as a lambda expression in com.issue.APITest 1 expectation failed.
JSON path count doesn't match.
Expected: 2
  Actual: 0
 within 60 seconds.

	at com.jayway.awaitility.core.ConditionAwaiter.await(ConditionAwaiter.java:122)
	at com.jayway.awaitility.core.AssertionCondition.await(AssertionCondition.java:117)
	at com.jayway.awaitility.core.AssertionCondition.await(AssertionCondition.java:32)
	at com.jayway.awaitility.core.ConditionFactory.until(ConditionFactory.java:764)
	at com.jayway.awaitility.core.ConditionFactory.until(ConditionFactory.java:603)
	at com.issue.APITest.testAddServiceToClient(APITest.java:54)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:108)
	at org.testng.internal.Invoker.invokeMethod(Invoker.java:661)
	at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:869)
	at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1193)
	at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:126)
	at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:109)
	at org.testng.TestRunner.privateRun(TestRunner.java:744)
	at org.testng.TestRunner.run(TestRunner.java:602)
	at org.testng.SuiteRunner.runTest(SuiteRunner.java:380)
	at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:375)
	at org.testng.SuiteRunner.privateRun(SuiteRunner.java:340)
	at org.testng.SuiteRunner.run(SuiteRunner.java:289)
	at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
	at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:86)
	at org.testng.TestNG.runSuitesSequentially(TestNG.java:1301)
	at org.testng.TestNG.runSuitesLocally(TestNG.java:1226)
	at org.testng.TestNG.runSuites(TestNG.java:1144)
	at org.testng.TestNG.run(TestNG.java:1115)
	at org.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:72)
	at org.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:127)

 INFO:[2017-08-08 17:21:35,624]  - After test - clear db

===============================================
Default Suite
Total tests run: 1, Failures: 1, Skips: 0
===============================================


Process finished with exit code 0
