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

public aspect StringBuffer_SingleThreadUsageMonitorAspect implements rvmonitorrt.RVMObject {
	public StringBuffer_SingleThreadUsageMonitorAspect(){
		Runtime.getRuntime().addShutdownHook(new StringBuffer_SingleThreadUsage_DummyHookThread());
	}

	// Declarations for the Lock
	static ReentrantLock StringBuffer_SingleThreadUsage_MOPLock = new ReentrantLock();
	static Condition StringBuffer_SingleThreadUsage_MOPLock_cond = StringBuffer_SingleThreadUsage_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut StringBuffer_SingleThreadUsage_use(StringBuffer s) : (call(* StringBuffer.*(..)) && target(s)) && MOP_CommonPointCut();
	before (StringBuffer s) : StringBuffer_SingleThreadUsage_use(s) {
		Thread t = Thread.currentThread();
		StringBuffer_SingleThreadUsageRuntimeMonitor.useEvent(s, t);
	}

	pointcut StringBuffer_SingleThreadUsage_init() : (call(StringBuffer.new(..))) && MOP_CommonPointCut();
	after () returning (StringBuffer s) : StringBuffer_SingleThreadUsage_init() {
		Thread t = Thread.currentThread();
		StringBuffer_SingleThreadUsageRuntimeMonitor.initEvent(t, s);
	}

	class StringBuffer_SingleThreadUsage_DummyHookThread extends Thread {
		public void run(){
			StringBuffer_SingleThreadUsageRuntimeMonitor.endprogramEvent();
		}
	}
}
