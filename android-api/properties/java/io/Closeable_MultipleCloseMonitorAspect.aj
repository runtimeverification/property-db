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

public aspect Closeable_MultipleCloseMonitorAspect implements rvmonitorrt.RVMObject {
	public Closeable_MultipleCloseMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Closeable_MultipleClose_MOPLock = new ReentrantLock();
	static Condition Closeable_MultipleClose_MOPLock_cond = Closeable_MultipleClose_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Closeable_MultipleClose_close(Closeable c) : (call(* Closeable+.close(..)) && target(c)) && MOP_CommonPointCut();
	before (Closeable c) : Closeable_MultipleClose_close(c) {
		Closeable_MultipleCloseRuntimeMonitor.closeEvent(c);
	}

}
