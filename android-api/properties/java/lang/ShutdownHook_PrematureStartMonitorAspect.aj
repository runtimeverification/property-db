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

public aspect ShutdownHook_PrematureStartMonitorAspect implements rvmonitorrt.RVMObject {
	public ShutdownHook_PrematureStartMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ShutdownHook_PrematureStart_MOPLock = new ReentrantLock();
	static Condition ShutdownHook_PrematureStart_MOPLock_cond = ShutdownHook_PrematureStart_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ShutdownHook_PrematureStart_userstart(Thread t) : (call(* Thread+.start(..)) && target(t)) && MOP_CommonPointCut();
	before (Thread t) : ShutdownHook_PrematureStart_userstart(t) {
		ShutdownHook_PrematureStartRuntimeMonitor.userstartEvent(t);
	}

	pointcut ShutdownHook_PrematureStart_unregister(Thread t) : (call(* Runtime+.removeShutdownHook(..)) && args(t)) && MOP_CommonPointCut();
	before (Thread t) : ShutdownHook_PrematureStart_unregister(t) {
		ShutdownHook_PrematureStartRuntimeMonitor.unregisterEvent(t);
	}

	pointcut ShutdownHook_PrematureStart_good_register(Thread t) : (call(* Runtime+.addShutdownHook(..)) && args(t)) && MOP_CommonPointCut();
	before (Thread t) : ShutdownHook_PrematureStart_good_register(t) {
		//ShutdownHook_PrematureStart_bad_register
		ShutdownHook_PrematureStartRuntimeMonitor.bad_registerEvent(t);
		//ShutdownHook_PrematureStart_good_register
		ShutdownHook_PrematureStartRuntimeMonitor.good_registerEvent(t);
	}

}
