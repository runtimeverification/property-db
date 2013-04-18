package mop;
import java.util.*;
import java.util.concurrent.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Deque_OfferRatherThanAddMonitorAspect implements rvmonitorrt.RVMObject {
	public Deque_OfferRatherThanAddMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Deque_OfferRatherThanAdd_MOPLock = new ReentrantLock();
	static Condition Deque_OfferRatherThanAdd_MOPLock_cond = Deque_OfferRatherThanAdd_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Deque_OfferRatherThanAdd_add(Deque q) : ((call(* Deque+.addFirst(..)) || call(* Deque+.addLast(..)) || call(* Deque+.add(..)) || call(* Deque+.push(..))) && target(q)) && MOP_CommonPointCut();
	before (Deque q) : Deque_OfferRatherThanAdd_add(q) {
		Deque_OfferRatherThanAddRuntimeMonitor.addEvent(q);
	}

	pointcut Deque_OfferRatherThanAdd_create() : (call(LinkedBlockingDeque+.new(int))) && MOP_CommonPointCut();
	after () returning (Deque q) : Deque_OfferRatherThanAdd_create() {
		Deque_OfferRatherThanAddRuntimeMonitor.createEvent(q);
	}

}
