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

public aspect AutoCloseable_MultipleCloseMonitorAspect implements rvmonitorrt.RVMObject {
	public AutoCloseable_MultipleCloseMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock AutoCloseable_MultipleClose_MOPLock = new ReentrantLock();
	static Condition AutoCloseable_MultipleClose_MOPLock_cond = AutoCloseable_MultipleClose_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut AutoCloseable_MultipleClose_close(AutoCloseable c) : (call(* AutoCloseable+.close(..)) && target(c)) && MOP_CommonPointCut();
	before (AutoCloseable c) : AutoCloseable_MultipleClose_close(c) {
		AutoCloseable_MultipleCloseRuntimeMonitor.closeEvent(c);
	}

}
