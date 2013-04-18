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

public aspect ObjectOutput_CloseMonitorAspect implements rvmonitorrt.RVMObject {
	public ObjectOutput_CloseMonitorAspect(){
		Runtime.getRuntime().addShutdownHook(new ObjectOutput_Close_DummyHookThread());
	}

	// Declarations for the Lock
	static ReentrantLock ObjectOutput_Close_MOPLock = new ReentrantLock();
	static Condition ObjectOutput_Close_MOPLock_cond = ObjectOutput_Close_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ObjectOutput_Close_close(ObjectOutput o) : (call(* ObjectOutput+.close(..)) && target(o)) && MOP_CommonPointCut();
	before (ObjectOutput o) : ObjectOutput_Close_close(o) {
		ObjectOutput_CloseRuntimeMonitor.closeEvent(o);
	}

	pointcut ObjectOutput_Close_create() : (call(ObjectOutput+.new(..))) && MOP_CommonPointCut();
	after () returning (ObjectOutput o) : ObjectOutput_Close_create() {
		ObjectOutput_CloseRuntimeMonitor.createEvent(o);
	}

	class ObjectOutput_Close_DummyHookThread extends Thread {
		public void run(){
			ObjectOutput_CloseRuntimeMonitor.endProgEvent();
		}
	}
}
