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

public aspect SortedSet_ComparableMonitorAspect implements rvmonitorrt.RVMObject {
	public SortedSet_ComparableMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock SortedSet_Comparable_MOPLock = new ReentrantLock();
	static Condition SortedSet_Comparable_MOPLock_cond = SortedSet_Comparable_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut SortedSet_Comparable_addall(Collection c) : (call(* Collection+.addAll(Collection)) && target(SortedSet) && args(c)) && MOP_CommonPointCut();
	before (Collection c) : SortedSet_Comparable_addall(c) {
		SortedSet_ComparableRuntimeMonitor.addallEvent(c);
	}

	pointcut SortedSet_Comparable_add(Object e) : ((call(* Collection+.add*(..)) || call(* Queue+.offer*(..))) && target(SortedSet) && args(e)) && MOP_CommonPointCut();
	before (Object e) : SortedSet_Comparable_add(e) {
		SortedSet_ComparableRuntimeMonitor.addEvent(e);
	}

}
