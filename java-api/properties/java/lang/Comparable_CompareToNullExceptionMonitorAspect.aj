package mop;
import java.io.*;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Comparable_CompareToNullExceptionMonitorAspect implements rvmonitorrt.RVMObject {
	public Comparable_CompareToNullExceptionMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Comparable_CompareToNullException_MOPLock = new ReentrantLock();
	static Condition Comparable_CompareToNullException_MOPLock_cond = Comparable_CompareToNullException_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Comparable_CompareToNullException_badexception(Object o) : (call(* Comparable+.compareTo(..)) && args(o) && if(o == null)) && MOP_CommonPointCut();
	after (Object o) throwing (Exception e) : Comparable_CompareToNullException_badexception(o) {
		Comparable_CompareToNullExceptionRuntimeMonitor.badexceptionEvent(o, e);
	}

	pointcut Comparable_CompareToNullException_badcompare(Object o) : (call(* Comparable+.compareTo(..)) && args(o) && if(o == null)) && MOP_CommonPointCut();
	after (Object o) returning (int i) : Comparable_CompareToNullException_badcompare(o) {
		Comparable_CompareToNullExceptionRuntimeMonitor.badcompareEvent(o, i);
	}

}
