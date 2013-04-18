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

public aspect Map_CollectionViewAddMonitorAspect implements rvmonitorrt.RVMObject {
	public Map_CollectionViewAddMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Map_CollectionViewAdd_MOPLock = new ReentrantLock();
	static Condition Map_CollectionViewAdd_MOPLock_cond = Map_CollectionViewAdd_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Map_CollectionViewAdd_add(Collection c) : ((call(* Collection+.add(..)) || call(* Collection+.addAll(..))) && target(c)) && MOP_CommonPointCut();
	before (Collection c) : Map_CollectionViewAdd_add(c) {
		Map_CollectionViewAddRuntimeMonitor.addEvent(c);
	}

	pointcut Map_CollectionViewAdd_getset(Map m) : ((call(Set Map+.keySet()) || call(Set Map+.entrySet()) || call(Collection Map+.values())) && target(m)) && MOP_CommonPointCut();
	after (Map m) returning (Collection c) : Map_CollectionViewAdd_getset(m) {
		Map_CollectionViewAddRuntimeMonitor.getsetEvent(m, c);
	}

}
