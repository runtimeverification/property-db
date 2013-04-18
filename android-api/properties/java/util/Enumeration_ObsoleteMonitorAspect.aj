package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Enumeration_ObsoleteMonitorAspect implements rvmonitorrt.RVMObject {
	public Enumeration_ObsoleteMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Enumeration_Obsolete_MOPLock = new ReentrantLock();
	static Condition Enumeration_Obsolete_MOPLock_cond = Enumeration_Obsolete_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Enumeration_Obsolete_use(Enumeration e) : (call(* Enumeration+.*(..)) && target(e)) && MOP_CommonPointCut();
	before (Enumeration e) : Enumeration_Obsolete_use(e) {
		Enumeration_ObsoleteRuntimeMonitor.useEvent(e);
	}

}
