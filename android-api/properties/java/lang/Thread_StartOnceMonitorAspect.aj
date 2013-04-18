package mop;
import java.io.*;
import java.lang.*;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Thread_StartOnceMonitorAspect implements rvmonitorrt.RVMObject {
	public Thread_StartOnceMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Thread_StartOnce_MOPLock = new ReentrantLock();
	static Condition Thread_StartOnce_MOPLock_cond = Thread_StartOnce_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Thread_StartOnce_start(Thread t) : (call(* Thread+.start()) && target(t)) && MOP_CommonPointCut();
	before (Thread t) : Thread_StartOnce_start(t) {
		Thread_StartOnceRuntimeMonitor.startEvent(t);
	}

}
