package mop;
import java.net.*;
import java.io.InputStream;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Socket_SetTimeoutBeforeBlockingInputMonitorAspect implements rvmonitorrt.RVMObject {
	public Socket_SetTimeoutBeforeBlockingInputMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Socket_SetTimeoutBeforeBlockingInput_MOPLock = new ReentrantLock();
	static Condition Socket_SetTimeoutBeforeBlockingInput_MOPLock_cond = Socket_SetTimeoutBeforeBlockingInput_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Socket_SetTimeoutBeforeBlockingInput_set(Socket sock, int timeout) : (call(* Socket+.setSoTimeout(int)) && target(sock) && args(timeout)) && MOP_CommonPointCut();
	before (Socket sock, int timeout) : Socket_SetTimeoutBeforeBlockingInput_set(sock, timeout) {
		Socket_SetTimeoutBeforeBlockingInputRuntimeMonitor.setEvent(sock, timeout);
	}

	pointcut Socket_SetTimeoutBeforeBlockingInput_enter(InputStream input) : (call(* InputStream+.read(..)) && target(input)) && MOP_CommonPointCut();
	before (InputStream input) : Socket_SetTimeoutBeforeBlockingInput_enter(input) {
		Socket_SetTimeoutBeforeBlockingInputRuntimeMonitor.enterEvent(input);
	}

	pointcut Socket_SetTimeoutBeforeBlockingInput_getinput(Socket sock) : (call(InputStream Socket+.getInputStream()) && target(sock)) && MOP_CommonPointCut();
	after (Socket sock) returning (InputStream input) : Socket_SetTimeoutBeforeBlockingInput_getinput(sock) {
		Socket_SetTimeoutBeforeBlockingInputRuntimeMonitor.getinputEvent(sock, input);
	}

	pointcut Socket_SetTimeoutBeforeBlockingInput_leave(InputStream input) : (call(* InputStream+.read(..)) && target(input)) && MOP_CommonPointCut();
	after (InputStream input) : Socket_SetTimeoutBeforeBlockingInput_leave(input) {
		Socket_SetTimeoutBeforeBlockingInputRuntimeMonitor.leaveEvent(input);
	}

}
