package mop;
import java.util.*;
import java.io.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Properties_ManipulateAfterLoadMonitorAspect implements rvmonitorrt.RVMObject {
	public Properties_ManipulateAfterLoadMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Properties_ManipulateAfterLoad_MOPLock = new ReentrantLock();
	static Condition Properties_ManipulateAfterLoad_MOPLock_cond = Properties_ManipulateAfterLoad_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Properties_ManipulateAfterLoad_manipulate(InputStream i) : ((call(* InputStream+.read(..)) || call(* InputStream+.available(..)) || call(* InputStream+.reset(..)) || call(* InputStream+.skip(..))) && target(i) && !target(ByteArrayInputStream) && !target(StringBufferInputStream)) && MOP_CommonPointCut();
	before (InputStream i) : Properties_ManipulateAfterLoad_manipulate(i) {
		Properties_ManipulateAfterLoadRuntimeMonitor.manipulateEvent(i);
	}

	pointcut Properties_ManipulateAfterLoad_close(InputStream i) : (call(* Properties+.loadFromXML(InputStream)) && args(i) && !args(ByteArrayInputStream) && !args(StringBufferInputStream)) && MOP_CommonPointCut();
	after (InputStream i) : Properties_ManipulateAfterLoad_close(i) {
		Properties_ManipulateAfterLoadRuntimeMonitor.closeEvent(i);
	}

}
