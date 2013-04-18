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

public aspect ProcessBuilder_ThreadSafeMonitorAspect implements rvmonitorrt.RVMObject {
	public ProcessBuilder_ThreadSafeMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ProcessBuilder_ThreadSafe_MOPLock = new ReentrantLock();
	static Condition ProcessBuilder_ThreadSafe_MOPLock_cond = ProcessBuilder_ThreadSafe_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ProcessBuilder_ThreadSafe_safe_oper(ProcessBuilder p) : (call(* ProcessBuilder.*(..)) && target(p)) && MOP_CommonPointCut();
	before (ProcessBuilder p) : ProcessBuilder_ThreadSafe_safe_oper(p) {
		Thread t = Thread.currentThread();
		//ProcessBuilder_ThreadSafe_unsafe_oper
		ProcessBuilder_ThreadSafeRuntimeMonitor.unsafe_operEvent(p, t);
		//ProcessBuilder_ThreadSafe_safe_oper
		ProcessBuilder_ThreadSafeRuntimeMonitor.safe_operEvent(p, t);
	}

}
