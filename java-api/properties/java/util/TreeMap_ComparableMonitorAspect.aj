package mop;
import java.util.*;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect TreeMap_ComparableMonitorAspect implements rvmonitorrt.RVMObject {
	public TreeMap_ComparableMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock TreeMap_Comparable_MOPLock = new ReentrantLock();
	static Condition TreeMap_Comparable_MOPLock_cond = TreeMap_Comparable_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut TreeMap_Comparable_putall(Map src) : (call(* Map+.putAll(Map)) && args(src) && target(TreeMap)) && MOP_CommonPointCut();
	before (Map src) : TreeMap_Comparable_putall(src) {
		TreeMap_ComparableRuntimeMonitor.putallEvent(src);
	}

	pointcut TreeMap_Comparable_put(Object key) : (call(* Map+.put(Object, Object)) && args(key, ..) && target(TreeMap)) && MOP_CommonPointCut();
	before (Object key) : TreeMap_Comparable_put(key) {
		TreeMap_ComparableRuntimeMonitor.putEvent(key);
	}

	pointcut TreeMap_Comparable_create(Map src) : (call(TreeMap.new(Map)) && args(src)) && MOP_CommonPointCut();
	before (Map src) : TreeMap_Comparable_create(src) {
		TreeMap_ComparableRuntimeMonitor.createEvent(src);
	}

}
