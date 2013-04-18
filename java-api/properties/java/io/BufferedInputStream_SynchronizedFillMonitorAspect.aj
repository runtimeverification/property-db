package mop;
import java.io.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect BufferedInputStream_SynchronizedFillMonitorAspect implements rvmonitorrt.RVMObject {
	public BufferedInputStream_SynchronizedFillMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock BufferedInputStream_SynchronizedFill_MOPLock = new ReentrantLock();
	static Condition BufferedInputStream_SynchronizedFill_MOPLock_cond = BufferedInputStream_SynchronizedFill_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut BufferedInputStream_SynchronizedFill_fill(BufferedInputStream i) : (call(* BufferedInputStream.fill(..)) && target(i) && !cflow(call(synchronized * *.*(..)))) && MOP_CommonPointCut();
	before (BufferedInputStream i) : BufferedInputStream_SynchronizedFill_fill(i) {
		BufferedInputStream_SynchronizedFillRuntimeMonitor.fillEvent(i);
	}

}
