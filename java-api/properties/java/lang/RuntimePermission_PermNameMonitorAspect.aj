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

public aspect RuntimePermission_PermNameMonitorAspect implements rvmonitorrt.RVMObject {
	public RuntimePermission_PermNameMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock RuntimePermission_PermName_MOPLock = new ReentrantLock();
	static Condition RuntimePermission_PermName_MOPLock_cond = RuntimePermission_PermName_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut RuntimePermission_PermName_constructor_runtimeperm(String name) : (call(RuntimePermission.new(String)) && args(name)) && MOP_CommonPointCut();
	after (String name) returning (RuntimePermission r) : RuntimePermission_PermName_constructor_runtimeperm(name) {
		RuntimePermission_PermNameRuntimeMonitor.constructor_runtimepermEvent(name, r);
	}

}
