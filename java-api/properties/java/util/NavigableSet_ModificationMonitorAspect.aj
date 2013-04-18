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

public aspect NavigableSet_ModificationMonitorAspect implements rvmonitorrt.RVMObject {
	public NavigableSet_ModificationMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock NavigableSet_Modification_MOPLock = new ReentrantLock();
	static Condition NavigableSet_Modification_MOPLock_cond = NavigableSet_Modification_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut NavigableSet_Modification_useiter(Iterator i) : ((call(* Iterator.hasNext(..)) || call(* Iterator.next(..))) && target(i)) && MOP_CommonPointCut();
	before (Iterator i) : NavigableSet_Modification_useiter(i) {
		NavigableSet_ModificationRuntimeMonitor.useiterEvent(i);
	}

	pointcut NavigableSet_Modification_modify2(NavigableSet s2) : ((call(* Collection+.add*(..)) || call(* Collection+.clear(..)) || call(* Collection+.remove*(..)) || call(* Collection+.retain*(..))) && target(s2)) && MOP_CommonPointCut();
	before (NavigableSet s2) : NavigableSet_Modification_modify2(s2) {
		NavigableSet_ModificationRuntimeMonitor.modify2Event(s2);
	}

	pointcut NavigableSet_Modification_modify1(NavigableSet s1) : ((call(* Collection+.add*(..)) || call(* Collection+.clear(..)) || call(* Collection+.remove*(..)) || call(* Collection+.retain*(..))) && target(s1)) && MOP_CommonPointCut();
	before (NavigableSet s1) : NavigableSet_Modification_modify1(s1) {
		NavigableSet_ModificationRuntimeMonitor.modify1Event(s1);
	}

	pointcut NavigableSet_Modification_create(NavigableSet s1) : (call(NavigableSet NavigableSet+.descendingSet()) && target(s1)) && MOP_CommonPointCut();
	after (NavigableSet s1) returning (NavigableSet s2) : NavigableSet_Modification_create(s1) {
		NavigableSet_ModificationRuntimeMonitor.createEvent(s1, s2);
	}

	pointcut NavigableSet_Modification_getiter1(NavigableSet s1) : (call(Iterator Iterable+.iterator()) && target(s1)) && MOP_CommonPointCut();
	after (NavigableSet s1) returning (Iterator i) : NavigableSet_Modification_getiter1(s1) {
		NavigableSet_ModificationRuntimeMonitor.getiter1Event(s1, i);
	}

	pointcut NavigableSet_Modification_getiter2(NavigableSet s2) : (call(Iterator Iterable+.iterator()) && target(s2)) && MOP_CommonPointCut();
	after (NavigableSet s2) returning (Iterator i) : NavigableSet_Modification_getiter2(s2) {
		NavigableSet_ModificationRuntimeMonitor.getiter2Event(s2, i);
	}

}
