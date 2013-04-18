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

public aspect Reader_UnmarkedResetMonitorAspect implements rvmonitorrt.RVMObject {
	public Reader_UnmarkedResetMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Reader_UnmarkedReset_MOPLock = new ReentrantLock();
	static Condition Reader_UnmarkedReset_MOPLock_cond = Reader_UnmarkedReset_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Reader_UnmarkedReset_reset(Reader r) : (call(* Reader+.reset(..)) && target(r) && if(r instanceof BufferedReader || r instanceof LineNumberReader)) && MOP_CommonPointCut();
	before (Reader r) : Reader_UnmarkedReset_reset(r) {
		Reader_UnmarkedResetRuntimeMonitor.resetEvent(r);
	}

	pointcut Reader_UnmarkedReset_mark(Reader r) : (call(* Reader+.mark(..)) && target(r) && if(r instanceof BufferedReader || r instanceof LineNumberReader)) && MOP_CommonPointCut();
	before (Reader r) : Reader_UnmarkedReset_mark(r) {
		Reader_UnmarkedResetRuntimeMonitor.markEvent(r);
	}

}
