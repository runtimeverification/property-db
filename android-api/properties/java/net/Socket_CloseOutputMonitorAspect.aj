package mop;
import java.net.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.io.OutputStream;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Socket_CloseOutputMonitorAspect implements rvmonitorrt.RVMObject {
	public Socket_CloseOutputMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Socket_CloseOutput_MOPLock = new ReentrantLock();
	static Condition Socket_CloseOutput_MOPLock_cond = Socket_CloseOutput_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Socket_CloseOutput_use(OutputStream output) : (call(* OutputStream+.*(..)) && target(output)) && MOP_CommonPointCut();
	before (OutputStream output) : Socket_CloseOutput_use(output) {
		Socket_CloseOutputRuntimeMonitor.useEvent(output);
	}

	pointcut Socket_CloseOutput_close_4(Socket sock) : (call(* Socket+.shutdownOutput(..)) && target(sock)) && MOP_CommonPointCut();
	before (Socket sock) : Socket_CloseOutput_close_4(sock) {
		Socket_CloseOutputRuntimeMonitor.closeEvent(sock);
	}

	pointcut Socket_CloseOutput_close_3(Socket sock) : (call(* Socket+.close(..)) && target(sock)) && MOP_CommonPointCut();
	before (Socket sock) : Socket_CloseOutput_close_3(sock) {
		Socket_CloseOutputRuntimeMonitor.closeEvent(sock);
	}

	pointcut Socket_CloseOutput_getoutput(Socket sock) : (call(OutputStream Socket+.getOutputStream()) && target(sock)) && MOP_CommonPointCut();
	after (Socket sock) returning (OutputStream output) : Socket_CloseOutput_getoutput(sock) {
		Socket_CloseOutputRuntimeMonitor.getoutputEvent(sock, output);
	}

}
