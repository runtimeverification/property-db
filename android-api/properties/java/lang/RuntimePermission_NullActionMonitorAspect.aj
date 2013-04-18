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

public aspect RuntimePermission_NullActionMonitorAspect implements rvmonitorrt.RVMObject {
	public RuntimePermission_NullActionMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock RuntimePermission_NullAction_MOPLock = new ReentrantLock();
	static Condition RuntimePermission_NullAction_MOPLock_cond = RuntimePermission_NullAction_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut RuntimePermission_NullAction_constructor_runtimeperm(String name, String actions) : (call(RuntimePermission.new(String, String)) && args(name, actions)) && MOP_CommonPointCut();
	after (String name, String actions) returning (RuntimePermission r) : RuntimePermission_NullAction_constructor_runtimeperm(name, actions) {
		RuntimePermission_NullActionRuntimeMonitor.constructor_runtimepermEvent(name, actions, r);
	}

}
