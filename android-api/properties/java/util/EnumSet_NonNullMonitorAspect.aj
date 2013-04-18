package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect EnumSet_NonNullMonitorAspect implements rvmonitorrt.RVMObject {
	public EnumSet_NonNullMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock EnumSet_NonNull_MOPLock = new ReentrantLock();
	static Condition EnumSet_NonNull_MOPLock_cond = EnumSet_NonNull_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut EnumSet_NonNull_insertnull_4(Collection c) : (call(* EnumSet+.addAll(Collection)) && args(c)) && MOP_CommonPointCut();
	before (Collection c) : EnumSet_NonNull_insertnull_4(c) {
		EnumSet_NonNullRuntimeMonitor.insertnullEvent(c);
	}

	pointcut EnumSet_NonNull_insertnull_3(Object e) : (call(* EnumSet+.add(Object)) && args(e)) && MOP_CommonPointCut();
	before (Object e) : EnumSet_NonNull_insertnull_3(e) {
		EnumSet_NonNullRuntimeMonitor.insertnullEvent(e);
	}

}
