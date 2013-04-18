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

public aspect Reader_ReadAheadLimitMonitorAspect implements rvmonitorrt.RVMObject {
	public Reader_ReadAheadLimitMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Reader_ReadAheadLimit_MOPLock = new ReentrantLock();
	static Condition Reader_ReadAheadLimit_MOPLock_cond = Reader_ReadAheadLimit_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Reader_ReadAheadLimit_badreset(Reader r) : (call(* Reader+.reset(..)) && target(r) && if(r instanceof BufferedReader || r instanceof LineNumberReader)) && MOP_CommonPointCut();
	before (Reader r) : Reader_ReadAheadLimit_badreset(r) {
		//Reader_ReadAheadLimit_goodreset
		Reader_ReadAheadLimitRuntimeMonitor.goodresetEvent(r);
		//Reader_ReadAheadLimit_badreset
		Reader_ReadAheadLimitRuntimeMonitor.badresetEvent(r);
	}

	pointcut Reader_ReadAheadLimit_mark(Reader r, int l) : (call(* Reader+.mark(int)) && target(r) && args(l) && if(r instanceof BufferedReader || r instanceof LineNumberReader)) && MOP_CommonPointCut();
	before (Reader r, int l) : Reader_ReadAheadLimit_mark(r, l) {
		Reader_ReadAheadLimitRuntimeMonitor.markEvent(r, l);
	}

	pointcut Reader_ReadAheadLimit_read_3(Reader r) : (call(* Reader+.read()) && target(r) && if(r instanceof BufferedReader || r instanceof LineNumberReader)) && MOP_CommonPointCut();
	after (Reader r) returning (int n) : Reader_ReadAheadLimit_read_3(r) {
		Reader_ReadAheadLimitRuntimeMonitor.readEvent(r, n);
	}

	pointcut Reader_ReadAheadLimit_read_4(Reader r) : (call(* Reader+.read(char[], ..)) && target(r) && if(r instanceof BufferedReader || r instanceof LineNumberReader)) && MOP_CommonPointCut();
	after (Reader r) returning (int n) : Reader_ReadAheadLimit_read_4(r) {
		Reader_ReadAheadLimitRuntimeMonitor.readEvent(r, n);
	}

}
