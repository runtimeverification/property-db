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

public aspect Reader_MarkResetMonitorAspect implements rvmonitorrt.RVMObject {
	public Reader_MarkResetMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Reader_MarkReset_MOPLock = new ReentrantLock();
	static Condition Reader_MarkReset_MOPLock_cond = Reader_MarkReset_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Reader_MarkReset_reset(Reader r) : (call(* Reader+.reset(..)) && target(r) && (target(PushbackReader) || target(InputStreamReader) || target(FileReader) || target(PipedReader))) && MOP_CommonPointCut();
	before (Reader r) : Reader_MarkReset_reset(r) {
		Reader_MarkResetRuntimeMonitor.resetEvent(r);
	}

	pointcut Reader_MarkReset_mark(Reader r) : (call(* Reader+.mark(..)) && target(r) && (target(PushbackReader) || target(InputStreamReader) || target(FileReader) || target(PipedReader))) && MOP_CommonPointCut();
	before (Reader r) : Reader_MarkReset_mark(r) {
		Reader_MarkResetRuntimeMonitor.markEvent(r);
	}

}
