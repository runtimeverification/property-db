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

public aspect Serializable_NoArgConstructorMonitorAspect implements rvmonitorrt.RVMObject {
	public Serializable_NoArgConstructorMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Serializable_NoArgConstructor_MOPLock = new ReentrantLock();
	static Condition Serializable_NoArgConstructor_MOPLock_cond = Serializable_NoArgConstructor_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Serializable_NoArgConstructor_staticinit() : (staticinitialization(Serializable+)) && MOP_CommonPointCut();
	after () : Serializable_NoArgConstructor_staticinit() {
		Serializable_NoArgConstructorRuntimeMonitor.staticinitEvent();
	}

}
