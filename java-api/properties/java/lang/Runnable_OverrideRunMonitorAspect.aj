package mop;
import java.io.*;
import java.lang.*;
import java.lang.reflect.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Runnable_OverrideRunMonitorAspect implements rvmonitorrt.RVMObject {
	public Runnable_OverrideRunMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Runnable_OverrideRun_MOPLock = new ReentrantLock();
	static Condition Runnable_OverrideRun_MOPLock_cond = Runnable_OverrideRun_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Runnable_OverrideRun_staticinit() : (staticinitialization(Runnable+)) && MOP_CommonPointCut();
	after () : Runnable_OverrideRun_staticinit() {
		Runnable_OverrideRunRuntimeMonitor.staticinitEvent();
	}

}
