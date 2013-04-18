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

public aspect ServerSocket_LargeReceiveBufferMonitorAspect implements rvmonitorrt.RVMObject {
	public ServerSocket_LargeReceiveBufferMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ServerSocket_LargeReceiveBuffer_MOPLock = new ReentrantLock();
	static Condition ServerSocket_LargeReceiveBuffer_MOPLock_cond = ServerSocket_LargeReceiveBuffer_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ServerSocket_LargeReceiveBuffer_set(ServerSocket sock, int size) : (call(* ServerSocket+.setReceiveBufferSize(int)) && target(sock) && args(size)) && MOP_CommonPointCut();
	before (ServerSocket sock, int size) : ServerSocket_LargeReceiveBuffer_set(sock, size) {
		ServerSocket_LargeReceiveBufferRuntimeMonitor.setEvent(sock, size);
	}

	pointcut ServerSocket_LargeReceiveBuffer_bind(ServerSocket sock) : (call(* ServerSocket+.bind(..)) && target(sock)) && MOP_CommonPointCut();
	before (ServerSocket sock) : ServerSocket_LargeReceiveBuffer_bind(sock) {
		ServerSocket_LargeReceiveBufferRuntimeMonitor.bindEvent(sock);
	}

	pointcut ServerSocket_LargeReceiveBuffer_create_bound() : (call(ServerSocket.new(int, ..))) && MOP_CommonPointCut();
	after () returning (ServerSocket sock) : ServerSocket_LargeReceiveBuffer_create_bound() {
		ServerSocket_LargeReceiveBufferRuntimeMonitor.create_boundEvent(sock);
	}

	pointcut ServerSocket_LargeReceiveBuffer_create_unbound() : (call(ServerSocket.new())) && MOP_CommonPointCut();
	after () returning (ServerSocket sock) : ServerSocket_LargeReceiveBuffer_create_unbound() {
		ServerSocket_LargeReceiveBufferRuntimeMonitor.create_unboundEvent(sock);
	}

}
