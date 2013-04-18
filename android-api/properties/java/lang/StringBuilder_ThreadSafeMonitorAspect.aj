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

public aspect StringBuilder_ThreadSafeMonitorAspect implements rvmonitorrt.RVMObject {
	public StringBuilder_ThreadSafeMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock StringBuilder_ThreadSafe_MOPLock = new ReentrantLock();
	static Condition StringBuilder_ThreadSafe_MOPLock_cond = StringBuilder_ThreadSafe_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut StringBuilder_ThreadSafe_safe_oper(StringBuilder b) : (call(* StringBuilder.*(..)) && target(b)) && MOP_CommonPointCut();
	before (StringBuilder b) : StringBuilder_ThreadSafe_safe_oper(b) {
		Thread t = Thread.currentThread();
		//StringBuilder_ThreadSafe_unsafe_oper
		StringBuilder_ThreadSafeRuntimeMonitor.unsafe_operEvent(b, t);
		//StringBuilder_ThreadSafe_safe_oper
		StringBuilder_ThreadSafeRuntimeMonitor.safe_operEvent(b, t);
	}

}
