package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect ArrayDeque_NonNullMonitorAspect implements rvmonitorrt.RVMObject {
	public ArrayDeque_NonNullMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ArrayDeque_NonNull_MOPLock = new ReentrantLock();
	static Condition ArrayDeque_NonNull_MOPLock_cond = ArrayDeque_NonNull_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ArrayDeque_NonNull_insertnull(Object e) : ((call(* ArrayDeque.add*(..)) || call(* ArrayDeque.offer*(..)) || call(* ArrayDeque.push(..))) && args(Object+) && args(e)) && MOP_CommonPointCut();
	before (Object e) : ArrayDeque_NonNull_insertnull(e) {
		ArrayDeque_NonNullRuntimeMonitor.insertnullEvent(e);
	}

}
