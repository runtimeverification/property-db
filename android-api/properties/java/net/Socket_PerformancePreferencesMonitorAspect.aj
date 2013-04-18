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

public aspect Socket_PerformancePreferencesMonitorAspect implements rvmonitorrt.RVMObject {
	public Socket_PerformancePreferencesMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Socket_PerformancePreferences_MOPLock = new ReentrantLock();
	static Condition Socket_PerformancePreferences_MOPLock_cond = Socket_PerformancePreferences_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Socket_PerformancePreferences_set(Socket sock) : (call(* Socket+.setPerformancePreferences(..)) && target(sock)) && MOP_CommonPointCut();
	before (Socket sock) : Socket_PerformancePreferences_set(sock) {
		Socket_PerformancePreferencesRuntimeMonitor.setEvent(sock);
	}

	pointcut Socket_PerformancePreferences_connect(Socket sock) : (call(* Socket+.connect(..)) && target(sock)) && MOP_CommonPointCut();
	before (Socket sock) : Socket_PerformancePreferences_connect(sock) {
		Socket_PerformancePreferencesRuntimeMonitor.connectEvent(sock);
	}

	pointcut Socket_PerformancePreferences_create_connected() : (call(Socket.new(InetAddress, int)) || call(Socket.new(InetAddress, int, boolean)) || call(Socket.new(InetAddress, int, InetAddress, int)) || call(Socket.new(String, int)) || call(Socket.new(String, int, boolean)) || call(Socket.new(String, int, InetAddress, int))) && MOP_CommonPointCut();
	after () returning (Socket sock) : Socket_PerformancePreferences_create_connected() {
		Socket_PerformancePreferencesRuntimeMonitor.create_connectedEvent(sock);
	}

	pointcut Socket_PerformancePreferences_create_unconnected() : (call(Socket.new()) || call(Socket.new(Proxy)) || call(Socket.new(SocketImpl))) && MOP_CommonPointCut();
	after () returning (Socket sock) : Socket_PerformancePreferences_create_unconnected() {
		Socket_PerformancePreferencesRuntimeMonitor.create_unconnectedEvent(sock);
	}

}
