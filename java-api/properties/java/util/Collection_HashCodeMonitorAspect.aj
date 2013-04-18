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

public aspect Collection_HashCodeMonitorAspect implements rvmonitorrt.RVMObject {
	public Collection_HashCodeMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collection_HashCode_MOPLock = new ReentrantLock();
	static Condition Collection_HashCode_MOPLock_cond = Collection_HashCode_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collection_HashCode_staticinit() : (staticinitialization(Collection+)) && MOP_CommonPointCut();
	after () : Collection_HashCode_staticinit() {
		Collection_HashCodeRuntimeMonitor.staticinitEvent(thisJoinPoint.getStaticPart().getSignature());
	}

}
