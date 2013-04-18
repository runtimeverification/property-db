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

public aspect Enum_UserFriendlyNameMonitorAspect implements rvmonitorrt.RVMObject {
	public Enum_UserFriendlyNameMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Enum_UserFriendlyName_MOPLock = new ReentrantLock();
	static Condition Enum_UserFriendlyName_MOPLock_cond = Enum_UserFriendlyName_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Enum_UserFriendlyName_name() : (call(* Enum+.name())) && MOP_CommonPointCut();
	before () : Enum_UserFriendlyName_name() {
		Enum_UserFriendlyNameRuntimeMonitor.nameEvent();
	}

}
