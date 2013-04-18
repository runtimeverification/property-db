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

public aspect Short_BadDecodeArgMonitorAspect implements rvmonitorrt.RVMObject {
	public Short_BadDecodeArgMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Short_BadDecodeArg_MOPLock = new ReentrantLock();
	static Condition Short_BadDecodeArg_MOPLock_cond = Short_BadDecodeArg_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Short_BadDecodeArg_decode(Short s, String nm) : (call(* Short.decode(String)) && args(nm) && target(s)) && MOP_CommonPointCut();
	before (Short s, String nm) : Short_BadDecodeArg_decode(s, nm) {
		Short_BadDecodeArgRuntimeMonitor.decodeEvent(s, nm);
	}

}
