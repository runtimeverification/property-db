package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Collections_UnnecessaryNewSetFromMapMonitorAspect implements rvmonitorrt.RVMObject {
	public Collections_UnnecessaryNewSetFromMapMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collections_UnnecessaryNewSetFromMap_MOPLock = new ReentrantLock();
	static Condition Collections_UnnecessaryNewSetFromMap_MOPLock_cond = Collections_UnnecessaryNewSetFromMap_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collections_UnnecessaryNewSetFromMap_unnecessary() : (call(* Collections.newSetFromMap(Map)) && (args(HashMap) || args(TreeMap))) && MOP_CommonPointCut();
	before () : Collections_UnnecessaryNewSetFromMap_unnecessary() {
		Collections_UnnecessaryNewSetFromMapRuntimeMonitor.unnecessaryEvent();
	}

}
