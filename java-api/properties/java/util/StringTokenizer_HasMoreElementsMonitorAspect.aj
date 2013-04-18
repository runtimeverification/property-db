package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect StringTokenizer_HasMoreElementsMonitorAspect implements rvmonitorrt.RVMObject {
	public StringTokenizer_HasMoreElementsMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock StringTokenizer_HasMoreElements_MOPLock = new ReentrantLock();
	static Condition StringTokenizer_HasMoreElements_MOPLock_cond = StringTokenizer_HasMoreElements_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut StringTokenizer_HasMoreElements_next(StringTokenizer i) : ((call(* StringTokenizer.nextToken()) || call(* StringTokenizer.nextElement())) && target(i)) && MOP_CommonPointCut();
	before (StringTokenizer i) : StringTokenizer_HasMoreElements_next(i) {
		StringTokenizer_HasMoreElementsRuntimeMonitor.nextEvent(i);
	}

	pointcut StringTokenizer_HasMoreElements_hasnexttrue(StringTokenizer i) : ((call(boolean StringTokenizer.hasMoreTokens()) || call(boolean StringTokenizer.hasMoreElements())) && target(i)) && MOP_CommonPointCut();
	after (StringTokenizer i) returning (boolean b) : StringTokenizer_HasMoreElements_hasnexttrue(i) {
		//StringTokenizer_HasMoreElements_hasnexttrue
		StringTokenizer_HasMoreElementsRuntimeMonitor.hasnexttrueEvent(i, b);
		//StringTokenizer_HasMoreElements_hasnextfalse
		StringTokenizer_HasMoreElementsRuntimeMonitor.hasnextfalseEvent(i, b);
	}

}
