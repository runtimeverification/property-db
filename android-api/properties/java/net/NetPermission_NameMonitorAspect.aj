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

public aspect NetPermission_NameMonitorAspect implements rvmonitorrt.RVMObject {
	public NetPermission_NameMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock NetPermission_Name_MOPLock = new ReentrantLock();
	static Condition NetPermission_Name_MOPLock_cond = NetPermission_Name_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut NetPermission_Name_construct(String name) : ((call(NetPermission.new(String)) || call(NetPermission.new(String, String))) && args(name, ..)) && MOP_CommonPointCut();
	before (String name) : NetPermission_Name_construct(name) {
		NetPermission_NameRuntimeMonitor.constructEvent(name);
	}

}
