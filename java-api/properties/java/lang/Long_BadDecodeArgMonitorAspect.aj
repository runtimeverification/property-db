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

public aspect Long_BadDecodeArgMonitorAspect implements rvmonitorrt.RVMObject {
	public Long_BadDecodeArgMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Long_BadDecodeArg_MOPLock = new ReentrantLock();
	static Condition Long_BadDecodeArg_MOPLock_cond = Long_BadDecodeArg_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Long_BadDecodeArg_decode(Long l, String nm) : (call(* Long.decode(String)) && args(nm) && target(l)) && MOP_CommonPointCut();
	before (Long l, String nm) : Long_BadDecodeArg_decode(l, nm) {
		Long_BadDecodeArgRuntimeMonitor.decodeEvent(l, nm);
	}

}
