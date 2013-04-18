package mop;
import java.net.*;
import java.io.OutputStream;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Socket_SetTimeoutBeforeBlockingOutputMonitorAspect implements rvmonitorrt.RVMObject {
	public Socket_SetTimeoutBeforeBlockingOutputMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Socket_SetTimeoutBeforeBlockingOutput_MOPLock = new ReentrantLock();
	static Condition Socket_SetTimeoutBeforeBlockingOutput_MOPLock_cond = Socket_SetTimeoutBeforeBlockingOutput_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Socket_SetTimeoutBeforeBlockingOutput_set(Socket sock, int timeout) : (call(* Socket+.setSoTimeout(int)) && target(sock) && args(timeout)) && MOP_CommonPointCut();
	before (Socket sock, int timeout) : Socket_SetTimeoutBeforeBlockingOutput_set(sock, timeout) {
		Socket_SetTimeoutBeforeBlockingOutputRuntimeMonitor.setEvent(sock, timeout);
	}

	pointcut Socket_SetTimeoutBeforeBlockingOutput_enter(OutputStream output) : (call(* OutputStream+.write(..)) && target(output)) && MOP_CommonPointCut();
	before (OutputStream output) : Socket_SetTimeoutBeforeBlockingOutput_enter(output) {
		Socket_SetTimeoutBeforeBlockingOutputRuntimeMonitor.enterEvent(output);
	}

	pointcut Socket_SetTimeoutBeforeBlockingOutput_getoutput(Socket sock) : (call(OutputStream Socket+.getOutputStream()) && target(sock)) && MOP_CommonPointCut();
	after (Socket sock) returning (OutputStream output) : Socket_SetTimeoutBeforeBlockingOutput_getoutput(sock) {
		Socket_SetTimeoutBeforeBlockingOutputRuntimeMonitor.getoutputEvent(sock, output);
	}

	pointcut Socket_SetTimeoutBeforeBlockingOutput_leave(OutputStream output) : (call(* OutputStream+.write(..)) && target(output)) && MOP_CommonPointCut();
	after (OutputStream output) : Socket_SetTimeoutBeforeBlockingOutput_leave(output) {
		Socket_SetTimeoutBeforeBlockingOutputRuntimeMonitor.leaveEvent(output);
	}

}
