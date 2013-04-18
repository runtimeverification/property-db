package mop;
import java.io.*;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Object_MonitorOwnerMonitorAspect implements rvmonitorrt.RVMObject {
	public Object_MonitorOwnerMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Object_MonitorOwner_MOPLock = new ReentrantLock();
	static Condition Object_MonitorOwner_MOPLock_cond = Object_MonitorOwner_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Object_MonitorOwner_bad_wait(Object o) : (call(* Object+.wait(..)) && target(o) && if(!Thread.holdsLock(o))) && MOP_CommonPointCut();
	before (Object o) : Object_MonitorOwner_bad_wait(o) {
		Object_MonitorOwnerRuntimeMonitor.bad_waitEvent(o);
	}

	pointcut Object_MonitorOwner_bad_notify(Object o) : ((call(* Object+.notify(..)) || call(* Object+.notifyAll(..))) && target(o) && if(!Thread.holdsLock(o))) && MOP_CommonPointCut();
	before (Object o) : Object_MonitorOwner_bad_notify(o) {
		Object_MonitorOwnerRuntimeMonitor.bad_notifyEvent(o);
	}

}
