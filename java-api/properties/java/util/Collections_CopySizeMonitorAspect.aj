package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Collections_CopySizeMonitorAspect implements rvmonitorrt.RVMObject {
	public Collections_CopySizeMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collections_CopySize_MOPLock = new ReentrantLock();
	static Condition Collections_CopySize_MOPLock_cond = Collections_CopySize_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collections_CopySize_bad_copy(List dest, List src) : (call(void Collections.copy(List, List)) && args(dest, src)) && MOP_CommonPointCut();
	before (List dest, List src) : Collections_CopySize_bad_copy(dest, src) {
		Collections_CopySizeRuntimeMonitor.bad_copyEvent(dest, src);
	}

}
