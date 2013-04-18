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

public aspect ServerSocket_BacklogMonitorAspect implements rvmonitorrt.RVMObject {
	public ServerSocket_BacklogMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock ServerSocket_Backlog_MOPLock = new ReentrantLock();
	static Condition ServerSocket_Backlog_MOPLock_cond = ServerSocket_Backlog_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut ServerSocket_Backlog_set(int backlog) : (call(* ServerSocket+.bind(SocketAddress, int)) && args(*, backlog)) && MOP_CommonPointCut();
	before (int backlog) : ServerSocket_Backlog_set(backlog) {
		ServerSocket_BacklogRuntimeMonitor.setEvent(backlog);
	}

	pointcut ServerSocket_Backlog_construct(int backlog) : ((call(ServerSocket.new(int, int)) || call(ServerSocket.new(int, int, InetAddress))) && args(*, backlog, ..)) && MOP_CommonPointCut();
	before (int backlog) : ServerSocket_Backlog_construct(backlog) {
		ServerSocket_BacklogRuntimeMonitor.constructEvent(backlog);
	}

}
