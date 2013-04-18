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

public aspect SortedMap_StandardConstructorsMonitorAspect implements rvmonitorrt.RVMObject {
	public SortedMap_StandardConstructorsMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock SortedMap_StandardConstructors_MOPLock = new ReentrantLock();
	static Condition SortedMap_StandardConstructors_MOPLock_cond = SortedMap_StandardConstructors_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut SortedMap_StandardConstructors_staticinit() : (staticinitialization(SortedMap+)) && MOP_CommonPointCut();
	after () : SortedMap_StandardConstructors_staticinit() {
		SortedMap_StandardConstructorsRuntimeMonitor.staticinitEvent(thisJoinPoint.getStaticPart().getSignature());
	}

}
