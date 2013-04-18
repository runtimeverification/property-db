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

public aspect Throwable_InitCauseOnceMonitorAspect implements rvmonitorrt.RVMObject {
	public Throwable_InitCauseOnceMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Throwable_InitCauseOnce_MOPLock = new ReentrantLock();
	static Condition Throwable_InitCauseOnce_MOPLock_cond = Throwable_InitCauseOnce_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Throwable_InitCauseOnce_initCause(Throwable t) : (call(* Throwable+.initCause(..)) && target(t)) && MOP_CommonPointCut();
	before (Throwable t) : Throwable_InitCauseOnce_initCause(t) {
		Throwable_InitCauseOnceRuntimeMonitor.initCauseEvent(t);
	}

	pointcut Throwable_InitCauseOnce_createWithoutThrowable() : (call(Throwable+.new()) || call(Throwable+.new(String))) && MOP_CommonPointCut();
	after () returning (Throwable t) : Throwable_InitCauseOnce_createWithoutThrowable() {
		Throwable_InitCauseOnceRuntimeMonitor.createWithoutThrowableEvent(t);
	}

	pointcut Throwable_InitCauseOnce_createWithThrowable() : (call(Throwable+.new(String, Throwable)) || call(Throwable+.new(Throwable))) && MOP_CommonPointCut();
	after () returning (Throwable t) : Throwable_InitCauseOnce_createWithThrowable() {
		Throwable_InitCauseOnceRuntimeMonitor.createWithThrowableEvent(t);
	}

}
