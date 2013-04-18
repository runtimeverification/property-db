package mop;
import java.util.*;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Iterator_RemoveOnceMonitorAspect implements rvmonitorrt.RVMObject {
	public Iterator_RemoveOnceMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Iterator_RemoveOnce_MOPLock = new ReentrantLock();
	static Condition Iterator_RemoveOnce_MOPLock_cond = Iterator_RemoveOnce_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Iterator_RemoveOnce_next(Iterator i) : (call(* Iterator+.next()) && target(i)) && MOP_CommonPointCut();
	before (Iterator i) : Iterator_RemoveOnce_next(i) {
		Iterator_RemoveOnceRuntimeMonitor.nextEvent(i);
	}

	pointcut Iterator_RemoveOnce_remove(Iterator i) : (call(void Iterator+.remove()) && target(i)) && MOP_CommonPointCut();
	before (Iterator i) : Iterator_RemoveOnce_remove(i) {
		Iterator_RemoveOnceRuntimeMonitor.removeEvent(i);
	}

}
