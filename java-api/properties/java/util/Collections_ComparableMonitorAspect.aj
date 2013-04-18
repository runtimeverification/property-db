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

public aspect Collections_ComparableMonitorAspect implements rvmonitorrt.RVMObject {
	public Collections_ComparableMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collections_Comparable_MOPLock = new ReentrantLock();
	static Condition Collections_Comparable_MOPLock_cond = Collections_Comparable_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collections_Comparable_invalid_minmax(Collection col, Comparator comp) : ((call(* Collections.min(Collection, Comparator)) || call(* Collections.max(Collection, Comparator))) && args(col, comp)) && MOP_CommonPointCut();
	before (Collection col, Comparator comp) : Collections_Comparable_invalid_minmax(col, comp) {
		Collections_ComparableRuntimeMonitor.invalid_minmaxEvent(col, comp);
	}

	pointcut Collections_Comparable_invalid_sort(List list, Comparator comp) : (call(void Collections.sort(List, Comparator)) && args(list, comp)) && MOP_CommonPointCut();
	before (List list, Comparator comp) : Collections_Comparable_invalid_sort(list, comp) {
		Collections_ComparableRuntimeMonitor.invalid_sortEvent(list, comp);
	}

}
