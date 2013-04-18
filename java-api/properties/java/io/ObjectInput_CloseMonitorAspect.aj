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

public aspect ObjectInput_CloseMonitorAspect implements rvmonitorrt.RVMObject {
	public ObjectInput_CloseMonitorAspect(){
		Runtime.getRuntime().addShutdownHook(new ObjectInput_Close_DummyHookThread());
	}

	// Declarations for the Lock
	static ReentrantLock ObjectInput_Close_MOPLock = new ReentrantLock();
	static Condition ObjectInput_Close_MOPLock_cond = ObjectInput_Close_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ObjectInput_Close_close(ObjectInput i) : (call(* ObjectInput+.close(..)) && target(i)) && MOP_CommonPointCut();
	before (ObjectInput i) : ObjectInput_Close_close(i) {
		ObjectInput_CloseRuntimeMonitor.closeEvent(i);
	}

	pointcut ObjectInput_Close_create() : (call(ObjectInput+.new(..))) && MOP_CommonPointCut();
	after () returning (ObjectInput i) : ObjectInput_Close_create() {
		ObjectInput_CloseRuntimeMonitor.createEvent(i);
	}

	class ObjectInput_Close_DummyHookThread extends Thread {
		public void run(){
			ObjectInput_CloseRuntimeMonitor.endProgEvent();
		}
	}
}
