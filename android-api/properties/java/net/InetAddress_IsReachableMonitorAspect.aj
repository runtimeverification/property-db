package mop;
import java.net.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect InetAddress_IsReachableMonitorAspect implements rvmonitorrt.RVMObject {
	public InetAddress_IsReachableMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock InetAddress_IsReachable_MOPLock = new ReentrantLock();
	static Condition InetAddress_IsReachable_MOPLock_cond = InetAddress_IsReachable_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut InetAddress_IsReachable_isreachable_4(int ttl, int timeout) : (call(* InetAddress+.isReachable(NetworkInterface, int, int)) && args(*, ttl, timeout)) && MOP_CommonPointCut();
	before (int ttl, int timeout) : InetAddress_IsReachable_isreachable_4(ttl, timeout) {
		InetAddress_IsReachableRuntimeMonitor.isreachableEvent(ttl, timeout);
	}

	pointcut InetAddress_IsReachable_isreachable_3(int timeout) : (call(* InetAddress+.isReachable(int)) && args(timeout)) && MOP_CommonPointCut();
	before (int timeout) : InetAddress_IsReachable_isreachable_3(timeout) {
		InetAddress_IsReachableRuntimeMonitor.isreachableEvent(timeout);
	}

}
