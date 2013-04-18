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

public aspect System_NullArrayCopyMonitorAspect implements rvmonitorrt.RVMObject {
	public System_NullArrayCopyMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock System_NullArrayCopy_MOPLock = new ReentrantLock();
	static Condition System_NullArrayCopy_MOPLock_cond = System_NullArrayCopy_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut System_NullArrayCopy_null_arraycopy(Object src, int srcPos, Object dest, int destPos, int length) : (call(* System.arraycopy(Object, int, Object, int, int)) && args(src, srcPos, dest, destPos, length)) && MOP_CommonPointCut();
	before (Object src, int srcPos, Object dest, int destPos, int length) : System_NullArrayCopy_null_arraycopy(src, srcPos, dest, destPos, length) {
		System_NullArrayCopyRuntimeMonitor.null_arraycopyEvent(src, srcPos, dest, destPos, length);
	}

}
