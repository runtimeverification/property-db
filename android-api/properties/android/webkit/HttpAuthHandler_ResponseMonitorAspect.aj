package mop;
import android.webkit.*;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect HttpAuthHandler_ResponseMonitorAspect implements rvmonitorrt.RVMObject {
	public HttpAuthHandler_ResponseMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock HttpAuthHandler_Response_MOPLock = new ReentrantLock();
	static Condition HttpAuthHandler_Response_MOPLock_cond = HttpAuthHandler_Response_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut HttpAuthHandler_Response_cancel() : (execution(* HttpAuthHandler+.cancel()) && this(hah) && cflow(call(* WebViewClient+.onReceivedHttpAuthRequest(*, hah, ..)))) && MOP_CommonPointCut();
	before () : HttpAuthHandler_Response_cancel() {
		HttpAuthHandler_ResponseRuntimeMonitor.cancelEvent();
	}

	pointcut HttpAuthHandler_Response_proceed() : (execution(* HttpAuthHandler+.proceed()) && this(hah) && cflow(call(* WebViewClient+.onReceivedHttpAuthRequest(*, hah, ..)))) && MOP_CommonPointCut();
	before () : HttpAuthHandler_Response_proceed() {
		HttpAuthHandler_ResponseRuntimeMonitor.proceedEvent();
	}

	pointcut HttpAuthHandler_Response_receivedRequestStart() : (execution(* WebViewClient+.onReceivedHttpAuthRequest(*, hah, ..))) && MOP_CommonPointCut();
	before () : HttpAuthHandler_Response_receivedRequestStart() {
		HttpAuthHandler_ResponseRuntimeMonitor.receivedRequestStartEvent();
	}

	pointcut HttpAuthHandler_Response_receivedRequestEnd() : (execution(* WebViewClient+.onReceivedHttpAuthRequest(*, hah, ..))) && MOP_CommonPointCut();
	after () : HttpAuthHandler_Response_receivedRequestEnd() {
		HttpAuthHandler_ResponseRuntimeMonitor.receivedRequestEndEvent();
	}

}
