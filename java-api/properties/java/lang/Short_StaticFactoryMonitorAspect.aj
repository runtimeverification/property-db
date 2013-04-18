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

public aspect Short_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Short_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Short_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Short_StaticFactory_MOPLock_cond = Short_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Short_StaticFactory_constructor_create() : (call(Short+.new(short))) && MOP_CommonPointCut();
	after () returning (Short l) : Short_StaticFactory_constructor_create() {
		Short_StaticFactoryRuntimeMonitor.constructor_createEvent(l);
	}

}
