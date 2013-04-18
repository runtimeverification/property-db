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

public aspect ClassLoader_UnsafeClassDefinitionMonitorAspect implements rvmonitorrt.RVMObject {
	public ClassLoader_UnsafeClassDefinitionMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ClassLoader_UnsafeClassDefinition_MOPLock = new ReentrantLock();
	static Condition ClassLoader_UnsafeClassDefinition_MOPLock_cond = ClassLoader_UnsafeClassDefinition_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ClassLoader_UnsafeClassDefinition_defineClass(String name) : (call(* ClassLoader+.defineClass(String, ..)) && args(name, ..)) && MOP_CommonPointCut();
	before (String name) : ClassLoader_UnsafeClassDefinition_defineClass(name) {
		ClassLoader_UnsafeClassDefinitionRuntimeMonitor.defineClassEvent(name);
	}

}
