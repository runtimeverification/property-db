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

public aspect RandomAccessFile_ManipulateAfterCloseMonitorAspect implements rvmonitorrt.RVMObject {
	public RandomAccessFile_ManipulateAfterCloseMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock RandomAccessFile_ManipulateAfterClose_MOPLock = new ReentrantLock();
	static Condition RandomAccessFile_ManipulateAfterClose_MOPLock_cond = RandomAccessFile_ManipulateAfterClose_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut RandomAccessFile_ManipulateAfterClose_close(RandomAccessFile f) : (call(* RandomAccessFile+.close(..)) && target(f)) && MOP_CommonPointCut();
	before (RandomAccessFile f) : RandomAccessFile_ManipulateAfterClose_close(f) {
		RandomAccessFile_ManipulateAfterCloseRuntimeMonitor.closeEvent(f);
	}

	pointcut RandomAccessFile_ManipulateAfterClose_manipulate(RandomAccessFile f) : ((call(* RandomAccessFile+.read*(..)) || call(* RandomAccessFile+.write*(..))) && target(f)) && MOP_CommonPointCut();
	before (RandomAccessFile f) : RandomAccessFile_ManipulateAfterClose_manipulate(f) {
		RandomAccessFile_ManipulateAfterCloseRuntimeMonitor.manipulateEvent(f);
	}

}
