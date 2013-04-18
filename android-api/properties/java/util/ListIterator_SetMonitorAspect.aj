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

public aspect ListIterator_SetMonitorAspect implements rvmonitorrt.RVMObject {
	public ListIterator_SetMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ListIterator_Set_MOPLock = new ReentrantLock();
	static Condition ListIterator_Set_MOPLock_cond = ListIterator_Set_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ListIterator_Set_set(ListIterator i) : (call(* ListIterator+.set(..)) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_Set_set(i) {
		ListIterator_SetRuntimeMonitor.setEvent(i);
	}

	pointcut ListIterator_Set_previous(ListIterator i) : (call(* ListIterator+.previous()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_Set_previous(i) {
		ListIterator_SetRuntimeMonitor.previousEvent(i);
	}

	pointcut ListIterator_Set_next(ListIterator i) : (call(* Iterator+.next()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_Set_next(i) {
		ListIterator_SetRuntimeMonitor.nextEvent(i);
	}

	pointcut ListIterator_Set_add(ListIterator i) : (call(void ListIterator+.add(..)) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_Set_add(i) {
		ListIterator_SetRuntimeMonitor.addEvent(i);
	}

	pointcut ListIterator_Set_remove(ListIterator i) : (call(void Iterator+.remove()) && target(i)) && MOP_CommonPointCut();
	before (ListIterator i) : ListIterator_Set_remove(i) {
		ListIterator_SetRuntimeMonitor.removeEvent(i);
	}

	pointcut ListIterator_Set_create() : (call(ListIterator Iterable+.listIterator())) && MOP_CommonPointCut();
	after () returning (ListIterator i) : ListIterator_Set_create() {
		ListIterator_SetRuntimeMonitor.createEvent(i);
	}

}
