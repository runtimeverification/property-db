package mop;
import java.io.*;
import java.lang.reflect.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Serializable_UIDMonitorAspect implements rvmonitorrt.RVMObject {
	public Serializable_UIDMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Serializable_UID_MOPLock = new ReentrantLock();
	static Condition Serializable_UID_MOPLock_cond = Serializable_UID_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Serializable_UID_staticinit() : (staticinitialization(Serializable+)) && MOP_CommonPointCut();
	after () : Serializable_UID_staticinit() {
		Serializable_UIDRuntimeMonitor.staticinitEvent();
	}

}
