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

public aspect ListIterator_RemoveOnceMonitorAspect implements rvmonitorrt.RVMObject {
	public ListIterator_RemoveOnceMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ListIterator_RemoveOnce_MOPLock = new ReentrantLock();
	static Condition ListIterator_RemoveOnce_MOPLock_cond = ListIterator_RemoveOnce_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ListIterator_RemoveOnce_previous(ListIterator i) : (call(* ListIterator+.previous()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_RemoveOnce_previous(i) {
		ListIterator_RemoveOnceRuntimeMonitor.previousEvent(i);
	}

	pointcut ListIterator_RemoveOnce_next(ListIterator i) : (call(* Iterator+.next()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_RemoveOnce_next(i) {
		ListIterator_RemoveOnceRuntimeMonitor.nextEvent(i);
	}

	pointcut ListIterator_RemoveOnce_remove(ListIterator i) : (call(void Iterator+.remove()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_RemoveOnce_remove(i) {
		ListIterator_RemoveOnceRuntimeMonitor.removeEvent(i);
	}

}
