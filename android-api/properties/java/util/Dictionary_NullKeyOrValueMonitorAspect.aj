package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Dictionary_NullKeyOrValueMonitorAspect implements rvmonitorrt.RVMObject {
	public Dictionary_NullKeyOrValueMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Dictionary_NullKeyOrValue_MOPLock = new ReentrantLock();
	static Condition Dictionary_NullKeyOrValue_MOPLock_cond = Dictionary_NullKeyOrValue_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Dictionary_NullKeyOrValue_putnull(Dictionary d, Object key, Object value) : (call(* Dictionary+.put(..)) && args(key, value) && target(d)) && MOP_CommonPointCut();
	before (Dictionary d, Object key, Object value) : Dictionary_NullKeyOrValue_putnull(d, key, value) {
		Dictionary_NullKeyOrValueRuntimeMonitor.putnullEvent(d, key, value);
	}

}
