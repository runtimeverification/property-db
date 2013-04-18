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

public aspect Collection_UnsafeIteratorMonitorAspect implements rvmonitorrt.RVMObject {
	public Collection_UnsafeIteratorMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collection_UnsafeIterator_MOPLock = new ReentrantLock();
	static Condition Collection_UnsafeIterator_MOPLock_cond = Collection_UnsafeIterator_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collection_UnsafeIterator_useiter(Iterator i) : ((call(* Iterator.hasNext(..)) || call(* Iterator.next(..))) && target(i)) && MOP_CommonPointCut();
	before (Iterator i) : Collection_UnsafeIterator_useiter(i) {
		Collection_UnsafeIteratorRuntimeMonitor.useiterEvent(i);
	}

	pointcut Collection_UnsafeIterator_modify(Collection c) : ((call(* Collection+.add*(..)) || call(* Collection+.clear(..)) || call(* Collection+.offer*(..)) || call(* Collection+.pop(..)) || call(* Collection+.push(..)) || call(* Collection+.remove*(..)) || call(* Collection+.retain*(..))) && target(c)) && MOP_CommonPointCut();
	before (Collection c) : Collection_UnsafeIterator_modify(c) {
		Collection_UnsafeIteratorRuntimeMonitor.modifyEvent(c);
	}

	pointcut Collection_UnsafeIterator_create(Collection c) : (call(Iterator Iterable+.iterator()) && target(c)) && MOP_CommonPointCut();
	after (Collection c) returning (Iterator i) : Collection_UnsafeIterator_create(c) {
		Collection_UnsafeIteratorRuntimeMonitor.createEvent(c, i);
	}

}
