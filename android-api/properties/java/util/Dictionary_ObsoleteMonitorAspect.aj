package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Dictionary_ObsoleteMonitorAspect implements rvmonitorrt.RVMObject {
	public Dictionary_ObsoleteMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Dictionary_Obsolete_MOPLock = new ReentrantLock();
	static Condition Dictionary_Obsolete_MOPLock_cond = Dictionary_Obsolete_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Dictionary_Obsolete_use(Dictionary d) : (call(* Dictionary+.*(..)) && target(d)) && MOP_CommonPointCut();
	before (Dictionary d) : Dictionary_Obsolete_use(d) {
		Dictionary_ObsoleteRuntimeMonitor.useEvent(d);
	}

}
