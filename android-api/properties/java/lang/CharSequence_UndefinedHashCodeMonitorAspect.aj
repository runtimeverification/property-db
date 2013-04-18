package mop;
import java.io.*;
import java.lang.*;
import java.nio.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect CharSequence_UndefinedHashCodeMonitorAspect implements rvmonitorrt.RVMObject {
	public CharSequence_UndefinedHashCodeMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock CharSequence_UndefinedHashCode_MOPLock = new ReentrantLock();
	static Condition CharSequence_UndefinedHashCode_MOPLock_cond = CharSequence_UndefinedHashCode_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut CharSequence_UndefinedHashCode_hashCode() : (call(* CharSequence+.hashCode(..)) && !target(String) && !target(CharBuffer)) && MOP_CommonPointCut();
	before () : CharSequence_UndefinedHashCode_hashCode() {
		CharSequence_UndefinedHashCodeRuntimeMonitor.hashCodeEvent();
	}

	pointcut CharSequence_UndefinedHashCode_equals() : (call(* CharSequence+.equals(..)) && !target(String) && !target(CharBuffer)) && MOP_CommonPointCut();
	before () : CharSequence_UndefinedHashCode_equals() {
		CharSequence_UndefinedHashCodeRuntimeMonitor.equalsEvent();
	}

}
