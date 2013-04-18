package mop;
import java.net.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.io.InputStream;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Socket_CloseInputMonitorAspect implements rvmonitorrt.RVMObject {
	public Socket_CloseInputMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Socket_CloseInput_MOPLock = new ReentrantLock();
	static Condition Socket_CloseInput_MOPLock_cond = Socket_CloseInput_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Socket_CloseInput_use(InputStream input) : (call(* InputStream+.*(..)) && target(input)) && MOP_CommonPointCut();
	before (InputStream input) : Socket_CloseInput_use(input) {
		Socket_CloseInputRuntimeMonitor.useEvent(input);
	}

	pointcut Socket_CloseInput_close(Socket sock) : (call(* Socket+.close(..)) && target(sock)) && MOP_CommonPointCut();
	before (Socket sock) : Socket_CloseInput_close(sock) {
		Socket_CloseInputRuntimeMonitor.closeEvent(sock);
	}

	pointcut Socket_CloseInput_getinput(Socket sock) : (call(InputStream Socket+.getInputStream()) && target(sock)) && MOP_CommonPointCut();
	after (Socket sock) returning (InputStream input) : Socket_CloseInput_getinput(sock) {
		Socket_CloseInputRuntimeMonitor.getinputEvent(sock, input);
	}

}
