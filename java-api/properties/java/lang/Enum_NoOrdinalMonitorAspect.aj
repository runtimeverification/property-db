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

public aspect Enum_NoOrdinalMonitorAspect implements rvmonitorrt.RVMObject {
	public Enum_NoOrdinalMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Enum_NoOrdinal_MOPLock = new ReentrantLock();
	static Condition Enum_NoOrdinal_MOPLock_cond = Enum_NoOrdinal_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Enum_NoOrdinal_ordinal() : (call(* Enum+.ordinal())) && MOP_CommonPointCut();
	before () : Enum_NoOrdinal_ordinal() {
		Enum_NoOrdinalRuntimeMonitor.ordinalEvent();
	}

}
