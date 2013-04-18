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

public aspect Long_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Long_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Long_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Long_StaticFactory_MOPLock_cond = Long_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Long_StaticFactory_constructor_create() : (call(Long+.new(long))) && MOP_CommonPointCut();
	after () returning (Long l) : Long_StaticFactory_constructor_create() {
		Long_StaticFactoryRuntimeMonitor.constructor_createEvent(l);
	}

}
