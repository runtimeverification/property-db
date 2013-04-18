package mop;
import java.util.*;
import java.lang.reflect.*;
import org.aspectj.lang.Signature;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Map_StandardConstructorsMonitorAspect implements rvmonitorrt.RVMObject {
	public Map_StandardConstructorsMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Map_StandardConstructors_MOPLock = new ReentrantLock();
	static Condition Map_StandardConstructors_MOPLock_cond = Map_StandardConstructors_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Map_StandardConstructors_staticinit() : (staticinitialization(Map+)) && MOP_CommonPointCut();
	after () : Map_StandardConstructors_staticinit() {
		Map_StandardConstructorsRuntimeMonitor.staticinitEvent(thisJoinPoint.getStaticPart().getSignature());
	}

}
