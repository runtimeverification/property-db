package mop;
import java.lang.*;
import javax.swing.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect ShutdownHook_UnsafeSwingCallMonitorAspect implements rvmonitorrt.RVMObject {
	public ShutdownHook_UnsafeSwingCallMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ShutdownHook_UnsafeSwingCall_MOPLock = new ReentrantLock();
	static Condition ShutdownHook_UnsafeSwingCall_MOPLock_cond = ShutdownHook_UnsafeSwingCall_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ShutdownHook_UnsafeSwingCall_unregister(Thread t) : (call(* Runtime+.removeShutdownHook(..)) && args(t)) && MOP_CommonPointCut();
	before (Thread t) : ShutdownHook_UnsafeSwingCall_unregister(t) {
		boolean MOP_skipAroundAdvice = false;
		ShutdownHook_UnsafeSwingCallRuntimeMonitor.unregisterEvent(t);
	}

	pointcut ShutdownHook_UnsafeSwingCall_register(Thread t) : (call(* Runtime+.addShutdownHook(..)) && args(t)) && MOP_CommonPointCut();
	before (Thread t) : ShutdownHook_UnsafeSwingCall_register(t) {
		boolean MOP_skipAroundAdvice = false;
		ShutdownHook_UnsafeSwingCallRuntimeMonitor.registerEvent(t);
	}

	pointcut ShutdownHook_UnsafeSwingCall_swingcall_3() : (call(* SwingUtilities+.invokeAndWait(..)) || call(* SwingUtilities+.invokeLater(..)) || call(* SwingWorker+.execute(..))) && MOP_CommonPointCut();
	void around () : ShutdownHook_UnsafeSwingCall_swingcall_3() {
		boolean MOP_skipAroundAdvice = false;
		Thread t = Thread.currentThread();
		ShutdownHook_UnsafeSwingCallRuntimeMonitor.swingcallEvent(t);
		if(MOP_skipAroundAdvice){
			return;
		} else {
			proceed();
		}
	}

	pointcut ShutdownHook_UnsafeSwingCall_swingcall_4() : (call(* SwingWorker+.get(..))) && MOP_CommonPointCut();
	Object around () : ShutdownHook_UnsafeSwingCall_swingcall_4() {
		boolean MOP_skipAroundAdvice = false;
		Thread t = Thread.currentThread();
		ShutdownHook_UnsafeSwingCallRuntimeMonitor.swingcallEvent(t);
		if(MOP_skipAroundAdvice){
			return null;
		} else {
			return proceed();
		}
	}

	static HashMap<Thread, Runnable> ShutdownHook_UnsafeSwingCall_start_ThreadToRunnable = new HashMap<Thread, Runnable>();
	static Thread ShutdownHook_UnsafeSwingCall_start_MainThread = null;

	after (Runnable r) returning (Thread t): ((call(Thread+.new(Runnable+,..)) && args(r,..))|| (initialization(Thread+.new(ThreadGroup+, Runnable+,..)) && args(ThreadGroup, r,..))) && MOP_CommonPointCut() {
		while (!ShutdownHook_UnsafeSwingCall_MOPLock.tryLock()) {
			Thread.yield();
		}
		ShutdownHook_UnsafeSwingCall_start_ThreadToRunnable.put(t, r);
		ShutdownHook_UnsafeSwingCall_MOPLock.unlock();
	}

	before (Thread t_1): ( execution(void Thread+.run()) && target(t_1) ) && MOP_CommonPointCut() {
		if(Thread.currentThread() == t_1) {
			Thread t = Thread.currentThread();
			ShutdownHook_UnsafeSwingCallRuntimeMonitor.startEvent(t);
		}
	}

	before (Runnable r): ( execution(void Runnable+.run()) && !execution(void Thread+.run()) && target(r) ) && MOP_CommonPointCut() {
		while (!ShutdownHook_UnsafeSwingCall_MOPLock.tryLock()) {
			Thread.yield();
		}
		if(ShutdownHook_UnsafeSwingCall_start_ThreadToRunnable.get(Thread.currentThread()) == r) {
			Thread t = Thread.currentThread();
			ShutdownHook_UnsafeSwingCallRuntimeMonitor.startEvent(t);
		}
		ShutdownHook_UnsafeSwingCall_MOPLock.unlock();
	}

	before (): (execution(void *.main(..)) ) && MOP_CommonPointCut() {
		if(ShutdownHook_UnsafeSwingCall_start_MainThread == null){
			ShutdownHook_UnsafeSwingCall_start_MainThread = Thread.currentThread();
			Thread t = Thread.currentThread();
			ShutdownHook_UnsafeSwingCallRuntimeMonitor.startEvent(t);
		}
	}

}
