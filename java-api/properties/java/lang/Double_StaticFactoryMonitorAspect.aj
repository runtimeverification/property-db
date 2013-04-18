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

public aspect Double_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Double_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Double_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Double_StaticFactory_MOPLock_cond = Double_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Double_StaticFactory_constructor_create() : (call(Double+.new(double))) && MOP_CommonPointCut();
	after () returning (Double d) : Double_StaticFactory_constructor_create() {
		Double_StaticFactoryRuntimeMonitor.constructor_createEvent(d);
	}

}
