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

public aspect Boolean_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Boolean_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Boolean_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Boolean_StaticFactory_MOPLock_cond = Boolean_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Boolean_StaticFactory_constructor_create() : (call(Boolean+.new(boolean))) && MOP_CommonPointCut();
	after () returning (Boolean b) : Boolean_StaticFactory_constructor_create() {
		Boolean_StaticFactoryRuntimeMonitor.constructor_createEvent(b);
	}

}
