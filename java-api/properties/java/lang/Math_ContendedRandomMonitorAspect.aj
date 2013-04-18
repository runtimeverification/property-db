package mop;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Math_ContendedRandomMonitorAspect implements rvmonitorrt.RVMObject {
	public Math_ContendedRandomMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Math_ContendedRandom_MOPLock = new ReentrantLock();
	static Condition Math_ContendedRandom_MOPLock_cond = Math_ContendedRandom_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Math_ContendedRandom_onethread_use() : (call(* Math.random(..))) && MOP_CommonPointCut();
	before () : Math_ContendedRandom_onethread_use() {
		Thread t = Thread.currentThread();
		//Math_ContendedRandom_otherthread_use
		Math_ContendedRandomRuntimeMonitor.otherthread_useEvent(t);
		//Math_ContendedRandom_onethread_use
		Math_ContendedRandomRuntimeMonitor.onethread_useEvent(t);
	}

}
