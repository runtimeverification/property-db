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

public aspect Closeable_MeaninglessCloseMonitorAspect implements rvmonitorrt.RVMObject {
	public Closeable_MeaninglessCloseMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Closeable_MeaninglessClose_MOPLock = new ReentrantLock();
	static Condition Closeable_MeaninglessClose_MOPLock_cond = Closeable_MeaninglessClose_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Closeable_MeaninglessClose_close() : (call(* Closeable+.close()) && (target(ByteArrayInputStream) || target(ByteArrayOutputStream) || target(CharArrayWriter) || target(StringWriter))) && MOP_CommonPointCut();
	before () : Closeable_MeaninglessClose_close() {
		Closeable_MeaninglessCloseRuntimeMonitor.closeEvent();
	}

}
