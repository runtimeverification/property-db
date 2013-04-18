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

public aspect SortedSet_StandardConstructorsMonitorAspect implements rvmonitorrt.RVMObject {
	public SortedSet_StandardConstructorsMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock SortedSet_StandardConstructors_MOPLock = new ReentrantLock();
	static Condition SortedSet_StandardConstructors_MOPLock_cond = SortedSet_StandardConstructors_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut SortedSet_StandardConstructors_staticinit() : (staticinitialization(SortedSet+)) && MOP_CommonPointCut();
	after () : SortedSet_StandardConstructors_staticinit() {
		SortedSet_StandardConstructorsRuntimeMonitor.staticinitEvent(thisJoinPoint.getStaticPart().getSignature());
	}

}
