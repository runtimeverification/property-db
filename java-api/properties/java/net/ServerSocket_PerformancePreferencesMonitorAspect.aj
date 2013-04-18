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

public aspect ServerSocket_PerformancePreferencesMonitorAspect implements rvmonitorrt.RVMObject {
	public ServerSocket_PerformancePreferencesMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ServerSocket_PerformancePreferences_MOPLock = new ReentrantLock();
	static Condition ServerSocket_PerformancePreferences_MOPLock_cond = ServerSocket_PerformancePreferences_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ServerSocket_PerformancePreferences_set(ServerSocket sock) : (call(* ServerSocket+.setPerformancePreferences(..)) && target(sock)) && MOP_CommonPointCut();
	before (ServerSocket sock) : ServerSocket_PerformancePreferences_set(sock) {
		ServerSocket_PerformancePreferencesRuntimeMonitor.setEvent(sock);
	}

	pointcut ServerSocket_PerformancePreferences_bind(ServerSocket sock) : (call(* ServerSocket+.bind(..)) && target(sock)) && MOP_CommonPointCut();
	before (ServerSocket sock) : ServerSocket_PerformancePreferences_bind(sock) {
		ServerSocket_PerformancePreferencesRuntimeMonitor.bindEvent(sock);
	}

	pointcut ServerSocket_PerformancePreferences_create_bound() : (call(ServerSocket.new(int, ..))) && MOP_CommonPointCut();
	after () returning (ServerSocket sock) : ServerSocket_PerformancePreferences_create_bound() {
		ServerSocket_PerformancePreferencesRuntimeMonitor.create_boundEvent(sock);
	}

	pointcut ServerSocket_PerformancePreferences_create_unbound() : (call(ServerSocket.new())) && MOP_CommonPointCut();
	after () returning (ServerSocket sock) : ServerSocket_PerformancePreferences_create_unbound() {
		ServerSocket_PerformancePreferencesRuntimeMonitor.create_unboundEvent(sock);
	}

}
