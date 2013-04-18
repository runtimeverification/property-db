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

public aspect MulticastSocket_TTLMonitorAspect implements rvmonitorrt.RVMObject {
	public MulticastSocket_TTLMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock MulticastSocket_TTL_MOPLock = new ReentrantLock();
	static Condition MulticastSocket_TTL_MOPLock_cond = MulticastSocket_TTL_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut MulticastSocket_TTL_set_4(int ttl) : (call(* MulticastSocket+.setTimeToLive(int)) && args(ttl)) && MOP_CommonPointCut();
	before (int ttl) : MulticastSocket_TTL_set_4(ttl) {
		MulticastSocket_TTLRuntimeMonitor.setEvent(ttl);
	}

	pointcut MulticastSocket_TTL_set_3(byte ttl) : (call(* MulticastSocket+.setTTL(byte)) && args(ttl)) && MOP_CommonPointCut();
	before (byte ttl) : MulticastSocket_TTL_set_3(ttl) {
		MulticastSocket_TTLRuntimeMonitor.setEvent(ttl);
	}

}
