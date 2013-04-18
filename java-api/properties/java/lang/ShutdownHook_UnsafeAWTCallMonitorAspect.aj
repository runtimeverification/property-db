package mop;
import java.lang.*;
import java.awt.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect ShutdownHook_UnsafeAWTCallMonitorAspect implements rvmonitorrt.RVMObject {
	public ShutdownHook_UnsafeAWTCallMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ShutdownHook_UnsafeAWTCall_MOPLock = new ReentrantLock();
	static Condition ShutdownHook_UnsafeAWTCall_MOPLock_cond = ShutdownHook_UnsafeAWTCall_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ShutdownHook_UnsafeAWTCall_unregister(Thread t) : (call(* Runtime+.removeShutdownHook(..)) && args(t)) && MOP_CommonPointCut();
	before (Thread t) : ShutdownHook_UnsafeAWTCall_unregister(t) {
		boolean MOP_skipAroundAdvice = false;
		ShutdownHook_UnsafeAWTCallRuntimeMonitor.unregisterEvent(t);
	}

	pointcut ShutdownHook_UnsafeAWTCall_register(Thread t) : (call(* Runtime+.addShutdownHook(..)) && args(t)) && MOP_CommonPointCut();
	before (Thread t) : ShutdownHook_UnsafeAWTCall_register(t) {
		boolean MOP_skipAroundAdvice = false;
		ShutdownHook_UnsafeAWTCallRuntimeMonitor.registerEvent(t);
	}

	pointcut ShutdownHook_UnsafeAWTCall_awtcall() : (call(* EventQueue.invokeAndWait(..)) || call(* EventQueue.invokeLater(..))) && MOP_CommonPointCut();
	void around () : ShutdownHook_UnsafeAWTCall_awtcall() {
		boolean MOP_skipAroundAdvice = false;
		Thread t = Thread.currentThread();
		ShutdownHook_UnsafeAWTCallRuntimeMonitor.awtcallEvent(t);
		if(MOP_skipAroundAdvice){
			return;
		} else {
			proceed();
		}
	}

	static HashMap<Thread, Runnable> ShutdownHook_UnsafeAWTCall_start_ThreadToRunnable = new HashMap<Thread, Runnable>();
	static Thread ShutdownHook_UnsafeAWTCall_start_MainThread = null;

	after (Runnable r) returning (Thread t): ((call(Thread+.new(Runnable+,..)) && args(r,..))|| (initialization(Thread+.new(ThreadGroup+, Runnable+,..)) && args(ThreadGroup, r,..))) && MOP_CommonPointCut() {
		while (!ShutdownHook_UnsafeAWTCall_MOPLock.tryLock()) {
			Thread.yield();
		}
		ShutdownHook_UnsafeAWTCall_start_ThreadToRunnable.put(t, r);
		ShutdownHook_UnsafeAWTCall_MOPLock.unlock();
	}

	before (Thread t_1): ( execution(void Thread+.run()) && target(t_1) ) && MOP_CommonPointCut() {
		if(Thread.currentThread() == t_1) {
			Thread t = Thread.currentThread();
			ShutdownHook_UnsafeAWTCallRuntimeMonitor.startEvent(t);
		}
	}

	before (Runnable r): ( execution(void Runnable+.run()) && !execution(void Thread+.run()) && target(r) ) && MOP_CommonPointCut() {
		while (!ShutdownHook_UnsafeAWTCall_MOPLock.tryLock()) {
			Thread.yield();
		}
		if(ShutdownHook_UnsafeAWTCall_start_ThreadToRunnable.get(Thread.currentThread()) == r) {
			Thread t = Thread.currentThread();
			ShutdownHook_UnsafeAWTCallRuntimeMonitor.startEvent(t);
		}
		ShutdownHook_UnsafeAWTCall_MOPLock.unlock();
	}

	before (): (execution(void *.main(..)) ) && MOP_CommonPointCut() {
		if(ShutdownHook_UnsafeAWTCall_start_MainThread == null){
			ShutdownHook_UnsafeAWTCall_start_MainThread = Thread.currentThread();
			Thread t = Thread.currentThread();
			ShutdownHook_UnsafeAWTCallRuntimeMonitor.startEvent(t);
		}
	}

}
