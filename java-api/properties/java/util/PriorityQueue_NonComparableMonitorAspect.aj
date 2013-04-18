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

public aspect PriorityQueue_NonComparableMonitorAspect implements rvmonitorrt.RVMObject {
	public PriorityQueue_NonComparableMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock PriorityQueue_NonComparable_MOPLock = new ReentrantLock();
	static Condition PriorityQueue_NonComparable_MOPLock_cond = PriorityQueue_NonComparable_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut PriorityQueue_NonComparable_insertnull_4(Collection c) : (call(* Collection+.addAll(Collection)) && target(PriorityQueue) && args(c)) && MOP_CommonPointCut();
	before (Collection c) : PriorityQueue_NonComparable_insertnull_4(c) {
		PriorityQueue_NonComparableRuntimeMonitor.insertnullEvent(c);
	}

	pointcut PriorityQueue_NonComparable_insertnull_3(Object e) : ((call(* Collection+.add*(..)) || call(* Queue+.offer*(..))) && target(PriorityQueue) && args(e)) && MOP_CommonPointCut();
	before (Object e) : PriorityQueue_NonComparable_insertnull_3(e) {
		PriorityQueue_NonComparableRuntimeMonitor.insertnullEvent(e);
	}

}
