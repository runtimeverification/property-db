package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Collection_UnsynchronizedAddAllMonitorAspect implements rvmonitorrt.RVMObject {
	public Collection_UnsynchronizedAddAllMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collection_UnsynchronizedAddAll_MOPLock = new ReentrantLock();
	static Condition Collection_UnsynchronizedAddAll_MOPLock_cond = Collection_UnsynchronizedAddAll_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collection_UnsynchronizedAddAll_modify(Collection s) : ((call(* Collection+.add*(..)) || call(* Collection+.remove*(..)) || call(* Collection+.clear(..)) || call(* Collection+.retain*(..))) && target(s)) && MOP_CommonPointCut();
	before (Collection s) : Collection_UnsynchronizedAddAll_modify(s) {
		Collection_UnsynchronizedAddAllRuntimeMonitor.modifyEvent(s);
	}

	pointcut Collection_UnsynchronizedAddAll_enter(Collection t, Collection s) : (call(boolean Collection+.addAll(..)) && target(t) && args(s)) && MOP_CommonPointCut();
	before (Collection t, Collection s) : Collection_UnsynchronizedAddAll_enter(t, s) {
		Collection_UnsynchronizedAddAllRuntimeMonitor.enterEvent(t, s);
	}

	pointcut Collection_UnsynchronizedAddAll_leave(Collection t, Collection s) : (call(boolean Collection+.addAll(..)) && target(t) && args(s)) && MOP_CommonPointCut();
	after (Collection t, Collection s) : Collection_UnsynchronizedAddAll_leave(t, s) {
		Collection_UnsynchronizedAddAllRuntimeMonitor.leaveEvent(t, s);
	}

}
