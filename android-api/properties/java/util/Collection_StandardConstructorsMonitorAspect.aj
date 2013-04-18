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

public aspect Collection_StandardConstructorsMonitorAspect implements rvmonitorrt.RVMObject {
	public Collection_StandardConstructorsMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collection_StandardConstructors_MOPLock = new ReentrantLock();
	static Condition Collection_StandardConstructors_MOPLock_cond = Collection_StandardConstructors_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collection_StandardConstructors_staticinit() : (staticinitialization(Collection+)) && MOP_CommonPointCut();
	after () : Collection_StandardConstructors_staticinit() {
		Collection_StandardConstructorsRuntimeMonitor.staticinitEvent(thisJoinPoint.getStaticPart().getSignature());
	}

}
