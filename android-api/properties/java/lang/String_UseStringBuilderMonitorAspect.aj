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

public aspect String_UseStringBuilderMonitorAspect implements rvmonitorrt.RVMObject {
	public String_UseStringBuilderMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock String_UseStringBuilder_MOPLock = new ReentrantLock();
	static Condition String_UseStringBuilder_MOPLock_cond = String_UseStringBuilder_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut String_UseStringBuilder_constructor_create() : (call(String.new(StringBuilder))) && MOP_CommonPointCut();
	after () returning (String b) : String_UseStringBuilder_constructor_create() {
		String_UseStringBuilderRuntimeMonitor.constructor_createEvent(b);
	}

}
