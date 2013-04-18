package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect ResourceBundleControl_MutateFormatListMonitorAspect implements rvmonitorrt.RVMObject {
	public ResourceBundleControl_MutateFormatListMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ResourceBundleControl_MutateFormatList_MOPLock = new ReentrantLock();
	static Condition ResourceBundleControl_MutateFormatList_MOPLock_cond = ResourceBundleControl_MutateFormatList_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ResourceBundleControl_MutateFormatList_mutate(List l) : ((call(* Collection+.add*(..)) || call(* Collection+.clear(..)) || call(* Collection+.remove*(..)) || call(* Collection+.retain*(..))) && target(l)) && MOP_CommonPointCut();
	before (List l) : ResourceBundleControl_MutateFormatList_mutate(l) {
		ResourceBundleControl_MutateFormatListRuntimeMonitor.mutateEvent(l);
	}

	pointcut ResourceBundleControl_MutateFormatList_create() : (call(List ResourceBundle.Control.getFormats(..)) || call(List ResourceBundle.Control.getCandidateLocales(..))) && MOP_CommonPointCut();
	after () returning (List l) : ResourceBundleControl_MutateFormatList_create() {
		ResourceBundleControl_MutateFormatListRuntimeMonitor.createEvent(l);
	}

}
