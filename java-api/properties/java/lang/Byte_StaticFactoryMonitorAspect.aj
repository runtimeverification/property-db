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

public aspect Byte_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Byte_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Byte_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Byte_StaticFactory_MOPLock_cond = Byte_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Byte_StaticFactory_constructor_create() : (call(Byte+.new(byte))) && MOP_CommonPointCut();
	after () returning (Byte b) : Byte_StaticFactory_constructor_create() {
		Byte_StaticFactoryRuntimeMonitor.constructor_createEvent(b);
	}

}
