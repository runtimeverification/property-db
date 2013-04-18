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

public aspect Enum_NoExtraWhiteSpaceMonitorAspect implements rvmonitorrt.RVMObject {
	public Enum_NoExtraWhiteSpaceMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Enum_NoExtraWhiteSpace_MOPLock = new ReentrantLock();
	static Condition Enum_NoExtraWhiteSpace_MOPLock_cond = Enum_NoExtraWhiteSpace_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Enum_NoExtraWhiteSpace_valueOf(Class c, String name) : (call(* Enum+.valueOf(Class, String)) && args(c, name)) && MOP_CommonPointCut();
	before (Class c, String name) : Enum_NoExtraWhiteSpace_valueOf(c, name) {
		Enum_NoExtraWhiteSpaceRuntimeMonitor.valueOfEvent(c, name);
	}

}
