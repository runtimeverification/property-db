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

public aspect Integer_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Integer_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Integer_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Integer_StaticFactory_MOPLock_cond = Integer_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Integer_StaticFactory_constructor_create() : (call(Integer+.new(int))) && MOP_CommonPointCut();
	after () returning (Integer i) : Integer_StaticFactory_constructor_create() {
		Integer_StaticFactoryRuntimeMonitor.constructor_createEvent(i);
	}

}
