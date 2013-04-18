package mop;
import java.net.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect URL_SetURLStreamHandlerFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public URL_SetURLStreamHandlerFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock URL_SetURLStreamHandlerFactory_MOPLock = new ReentrantLock();
	static Condition URL_SetURLStreamHandlerFactory_MOPLock_cond = URL_SetURLStreamHandlerFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut URL_SetURLStreamHandlerFactory_set() : (call(* URL.setURLStreamHandlerFactory(..))) && MOP_CommonPointCut();
	before () : URL_SetURLStreamHandlerFactory_set() {
		URL_SetURLStreamHandlerFactoryRuntimeMonitor.setEvent();
	}

}
