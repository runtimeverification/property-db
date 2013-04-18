package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect ListIterator_hasNextPreviousMonitorAspect implements rvmonitorrt.RVMObject {
	public ListIterator_hasNextPreviousMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ListIterator_hasNextPrevious_MOPLock = new ReentrantLock();
	static Condition ListIterator_hasNextPrevious_MOPLock_cond = ListIterator_hasNextPrevious_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ListIterator_hasNextPrevious_previous(ListIterator i) : (call(* ListIterator.previous()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_hasNextPrevious_previous(i) {
		ListIterator_hasNextPreviousRuntimeMonitor.previousEvent(i);
	}

	pointcut ListIterator_hasNextPrevious_next(ListIterator i) : (call(* ListIterator.next()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_hasNextPrevious_next(i) {
		ListIterator_hasNextPreviousRuntimeMonitor.nextEvent(i);
	}

	pointcut ListIterator_hasNextPrevious_hasnexttrue(ListIterator i) : (call(* ListIterator.hasNext()) && target(i)) && MOP_CommonPointCut();
	after (ListIterator i) returning (boolean b) : ListIterator_hasNextPrevious_hasnexttrue(i) {
		//ListIterator_hasNextPrevious_hasnexttrue
		ListIterator_hasNextPreviousRuntimeMonitor.hasnexttrueEvent(i, b);
		//ListIterator_hasNextPrevious_hasnextfalse
		ListIterator_hasNextPreviousRuntimeMonitor.hasnextfalseEvent(i, b);
	}

	pointcut ListIterator_hasNextPrevious_hasprevioustrue(ListIterator i) : (call(* ListIterator.hasPrevious()) && target(i)) && MOP_CommonPointCut();
	after (ListIterator i) returning (boolean b) : ListIterator_hasNextPrevious_hasprevioustrue(i) {
		//ListIterator_hasNextPrevious_hasprevioustrue
		ListIterator_hasNextPreviousRuntimeMonitor.hasprevioustrueEvent(i, b);
		//ListIterator_hasNextPrevious_haspreviousfalse
		ListIterator_hasNextPreviousRuntimeMonitor.haspreviousfalseEvent(i, b);
	}

}
