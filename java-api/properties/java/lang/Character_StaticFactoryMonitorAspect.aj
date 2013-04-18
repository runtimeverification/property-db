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

public aspect Character_StaticFactoryMonitorAspect implements rvmonitorrt.RVMObject {
	public Character_StaticFactoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Character_StaticFactory_MOPLock = new ReentrantLock();
	static Condition Character_StaticFactory_MOPLock_cond = Character_StaticFactory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Character_StaticFactory_constructor_create() : (call(Character+.new(char))) && MOP_CommonPointCut();
	after () returning (Character b) : Character_StaticFactory_constructor_create() {
		Character_StaticFactoryRuntimeMonitor.constructor_createEvent(b);
	}

}
