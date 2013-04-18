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

public aspect Console_CloseReaderMonitorAspect implements rvmonitorrt.RVMObject {
	public Console_CloseReaderMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Console_CloseReader_MOPLock = new ReentrantLock();
	static Condition Console_CloseReader_MOPLock_cond = Console_CloseReader_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Console_CloseReader_close(Reader r) : (call(* Reader+.close(..)) && target(r)) && MOP_CommonPointCut();
	before (Reader r) : Console_CloseReader_close(r) {
		Console_CloseReaderRuntimeMonitor.closeEvent(r);
	}

	pointcut Console_CloseReader_getreader() : (call(Reader+ Console+.reader())) && MOP_CommonPointCut();
	after () returning (Reader r) : Console_CloseReader_getreader() {
		Console_CloseReaderRuntimeMonitor.getreaderEvent(r);
	}

}
