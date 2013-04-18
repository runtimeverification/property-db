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

public aspect Float_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Float_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Float_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Float_StaticFactory_MOPLock_cond = Float_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Float_StaticFactory_constructor_create() : (call(Float+.new(float))) && MOP_CommonPointCut();
	after () returning (Float f) : Float_StaticFactory_constructor_create() {
		Float_StaticFactoryRuntimeMonitor.constructor_createEvent(f);
	}

}
