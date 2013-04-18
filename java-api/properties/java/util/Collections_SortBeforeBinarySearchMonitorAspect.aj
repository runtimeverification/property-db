package mop;
import java.util.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Collections_SortBeforeBinarySearchMonitorAspect implements rvmonitorrt.RVMObject {
	public Collections_SortBeforeBinarySearchMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Collections_SortBeforeBinarySearch_MOPLock = new ReentrantLock();
	static Condition Collections_SortBeforeBinarySearch_MOPLock_cond = Collections_SortBeforeBinarySearch_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Collections_SortBeforeBinarySearch_bsearch2(List list, Comparator comp2) : (call(int Collections.binarySearch(List, Object, Comparator)) && args(list, .., comp2)) && MOP_CommonPointCut();
	before (List list, Comparator comp2) : Collections_SortBeforeBinarySearch_bsearch2(list, comp2) {
		//Collections_SortBeforeBinarySearch_bad_bsearch2
		Collections_SortBeforeBinarySearchRuntimeMonitor.bad_bsearch2Event(list, comp2);
		//Collections_SortBeforeBinarySearch_bsearch2
		Collections_SortBeforeBinarySearchRuntimeMonitor.bsearch2Event(list, comp2);
	}

	pointcut Collections_SortBeforeBinarySearch_bsearch1(List list) : (call(int Collections.binarySearch(List, Object)) && args(list, ..)) && MOP_CommonPointCut();
	before (List list) : Collections_SortBeforeBinarySearch_bsearch1(list) {
		Collections_SortBeforeBinarySearchRuntimeMonitor.bsearch1Event(list);
	}

	pointcut Collections_SortBeforeBinarySearch_modify(List list) : ((call(* Collection+.add*(..)) || call(* Collection+.remove*(..)) || call(* Collection+.clear(..)) || call(* Collection+.retain*(..)) || call(* List+.set(..))) && target(list)) && MOP_CommonPointCut();
	before (List list) : Collections_SortBeforeBinarySearch_modify(list) {
		Collections_SortBeforeBinarySearchRuntimeMonitor.modifyEvent(list);
	}

	pointcut Collections_SortBeforeBinarySearch_sort2(List list, Comparator comp2) : (call(void Collections.sort(List, Comparator)) && args(list, comp2)) && MOP_CommonPointCut();
	before (List list, Comparator comp2) : Collections_SortBeforeBinarySearch_sort2(list, comp2) {
		Collections_SortBeforeBinarySearchRuntimeMonitor.sort2Event(list, comp2);
	}

	pointcut Collections_SortBeforeBinarySearch_sort1(List list) : (call(void Collections.sort(List)) && args(list)) && MOP_CommonPointCut();
	before (List list) : Collections_SortBeforeBinarySearch_sort1(list) {
		Collections_SortBeforeBinarySearchRuntimeMonitor.sort1Event(list);
	}

}
