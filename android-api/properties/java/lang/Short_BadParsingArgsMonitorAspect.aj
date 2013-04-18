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

public aspect Short_BadParsingArgsMonitorAspect implements rvmonitorrt.RVMObject {
	public Short_BadParsingArgsMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Short_BadParsingArgs_MOPLock = new ReentrantLock();
	static Condition Short_BadParsingArgs_MOPLock_cond = Short_BadParsingArgs_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Short_BadParsingArgs_bad_arg2(String s) : (call(* Short.parseShort(String)) && args(s)) && MOP_CommonPointCut();
	before (String s) : Short_BadParsingArgs_bad_arg2(s) {
		Short_BadParsingArgsRuntimeMonitor.bad_arg2Event(s);
	}

	pointcut Short_BadParsingArgs_bad_arg(String s, int radix) : (call(* Short.parseShort(String, int)) && args(s, radix)) && MOP_CommonPointCut();
	before (String s, int radix) : Short_BadParsingArgs_bad_arg(s, radix) {
		Short_BadParsingArgsRuntimeMonitor.bad_argEvent(s, radix);
	}

}
