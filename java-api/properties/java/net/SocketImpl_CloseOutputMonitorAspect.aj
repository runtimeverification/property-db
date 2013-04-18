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

public aspect SocketImpl_CloseOutputMonitorAspect implements rvmonitorrt.RVMObject {
	public SocketImpl_CloseOutputMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock SocketImpl_CloseOutput_MOPLock = new ReentrantLock();
	static Condition SocketImpl_CloseOutput_MOPLock_cond = SocketImpl_CloseOutput_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut SocketImpl_CloseOutput_use(OutputStream output) : (call(* OutputStream+.*(..)) && target(output)) && MOP_CommonPointCut();
	before (OutputStream output) : SocketImpl_CloseOutput_use(output) {
		SocketImpl_CloseOutputRuntimeMonitor.useEvent(output);
	}

	pointcut SocketImpl_CloseOutput_close_4(SocketImpl sock) : (call(* SocketImpl+.shutdownOutput(..)) && target(sock)) && MOP_CommonPointCut();
	before (SocketImpl sock) : SocketImpl_CloseOutput_close_4(sock) {
		SocketImpl_CloseOutputRuntimeMonitor.closeEvent(sock);
	}

	pointcut SocketImpl_CloseOutput_close_3(SocketImpl sock) : (call(* SocketImpl+.close(..)) && target(sock)) && MOP_CommonPointCut();
	before (SocketImpl sock) : SocketImpl_CloseOutput_close_3(sock) {
		SocketImpl_CloseOutputRuntimeMonitor.closeEvent(sock);
	}

	pointcut SocketImpl_CloseOutput_getoutput(SocketImpl sock) : (call(OutputStream SocketImpl+.getOutputStream()) && target(sock)) && MOP_CommonPointCut();
	after (SocketImpl sock) returning (OutputStream output) : SocketImpl_CloseOutput_getoutput(sock) {
		SocketImpl_CloseOutputRuntimeMonitor.getoutputEvent(sock, output);
	}

}
