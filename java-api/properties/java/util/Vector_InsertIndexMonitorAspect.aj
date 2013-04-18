package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Vector_InsertIndexMonitorAspect implements rvmonitorrt.RVMObject {
	public Vector_InsertIndexMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Vector_InsertIndex_MOPLock = new ReentrantLock();
	static Condition Vector_InsertIndex_MOPLock_cond = Vector_InsertIndex_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Vector_InsertIndex_insert(Vector v, int index) : (call(* Vector+.insertElementAt(Object, int)) && target(v) && args(.., index)) && MOP_CommonPointCut();
	before (Vector v, int index) : Vector_InsertIndex_insert(v, index) {
		Vector_InsertIndexRuntimeMonitor.insertEvent(v, index);
	}

}
