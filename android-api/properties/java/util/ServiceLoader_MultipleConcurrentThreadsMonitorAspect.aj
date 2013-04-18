package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect ServiceLoader_MultipleConcurrentThreadsMonitorAspect implements rvmonitorrt.RVMObject {
	public ServiceLoader_MultipleConcurrentThreadsMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ServiceLoader_MultipleConcurrentThreads_MOPLock = new ReentrantLock();
	static Condition ServiceLoader_MultipleConcurrentThreads_MOPLock_cond = ServiceLoader_MultipleConcurrentThreads_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ServiceLoader_MultipleConcurrentThreads_gooduse(ServiceLoader s) : ((call(* ServiceLoader+.iterator()) || call(* ServiceLoader+.reload())) && target(s)) && MOP_CommonPointCut();
	before (ServiceLoader s) : ServiceLoader_MultipleConcurrentThreads_gooduse(s) {
		Thread t2 = Thread.currentThread();
		//ServiceLoader_MultipleConcurrentThreads_baduse
		ServiceLoader_MultipleConcurrentThreadsRuntimeMonitor.baduseEvent(s, t2);
		//ServiceLoader_MultipleConcurrentThreads_gooduse
		ServiceLoader_MultipleConcurrentThreadsRuntimeMonitor.gooduseEvent(s, t2);
	}

	pointcut ServiceLoader_MultipleConcurrentThreads_create() : (call(ServiceLoader ServiceLoader+.load*(..))) && MOP_CommonPointCut();
	after () returning (ServiceLoader s) : ServiceLoader_MultipleConcurrentThreads_create() {
		Thread t2 = Thread.currentThread();
		ServiceLoader_MultipleConcurrentThreadsRuntimeMonitor.createEvent(t2, s);
	}

}
