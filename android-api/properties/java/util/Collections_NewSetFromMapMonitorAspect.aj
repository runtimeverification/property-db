package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Collections_NewSetFromMapMonitorAspect implements rvmonitorrt.RVMObject {
	public Collections_NewSetFromMapMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collections_NewSetFromMap_MOPLock = new ReentrantLock();
	static Condition Collections_NewSetFromMap_MOPLock_cond = Collections_NewSetFromMap_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collections_NewSetFromMap_access(Map map) : (call(* Map+.*(..)) && target(map)) && MOP_CommonPointCut();
	before (Map map) : Collections_NewSetFromMap_access(map) {
		Collections_NewSetFromMapRuntimeMonitor.accessEvent(map);
	}

	pointcut Collections_NewSetFromMap_create(Map map) : (call(* Collections.newSetFromMap(Map)) && args(map)) && MOP_CommonPointCut();
	before (Map map) : Collections_NewSetFromMap_create(map) {
		//Collections_NewSetFromMap_bad_create
		Collections_NewSetFromMapRuntimeMonitor.bad_createEvent(map);
		//Collections_NewSetFromMap_create
		Collections_NewSetFromMapRuntimeMonitor.createEvent(map);
	}

}
