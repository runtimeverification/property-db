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

public aspect ServerSocket_ReuseAddressMonitorAspect implements rvmonitorrt.RVMObject {
	public ServerSocket_ReuseAddressMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ServerSocket_ReuseAddress_MOPLock = new ReentrantLock();
	static Condition ServerSocket_ReuseAddress_MOPLock_cond = ServerSocket_ReuseAddress_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ServerSocket_ReuseAddress_set(ServerSocket sock) : (call(* ServerSocket+.setReuseAddress(..)) && target(sock)) && MOP_CommonPointCut();
	before (ServerSocket sock) : ServerSocket_ReuseAddress_set(sock) {
		ServerSocket_ReuseAddressRuntimeMonitor.setEvent(sock);
	}

	pointcut ServerSocket_ReuseAddress_bind(ServerSocket sock) : (call(* ServerSocket+.bind(..)) && target(sock)) && MOP_CommonPointCut();
	before (ServerSocket sock) : ServerSocket_ReuseAddress_bind(sock) {
		ServerSocket_ReuseAddressRuntimeMonitor.bindEvent(sock);
	}

	pointcut ServerSocket_ReuseAddress_create_bound() : (call(ServerSocket.new(int, ..))) && MOP_CommonPointCut();
	after () returning (ServerSocket sock) : ServerSocket_ReuseAddress_create_bound() {
		ServerSocket_ReuseAddressRuntimeMonitor.create_boundEvent(sock);
	}

	pointcut ServerSocket_ReuseAddress_create_unbound() : (call(ServerSocket.new())) && MOP_CommonPointCut();
	after () returning (ServerSocket sock) : ServerSocket_ReuseAddress_create_unbound() {
		ServerSocket_ReuseAddressRuntimeMonitor.create_unboundEvent(sock);
	}

}
