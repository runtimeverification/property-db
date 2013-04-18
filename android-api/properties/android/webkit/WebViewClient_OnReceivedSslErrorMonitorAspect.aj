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

public aspect WebViewClient_OnReceivedSslErrorMonitorAspect implements rvmonitorrt.RVMObject {
	public WebViewClient_OnReceivedSslErrorMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock WebViewClient_OnReceivedSslError_MOPLock = new ReentrantLock();
	static Condition WebViewClient_OnReceivedSslError_MOPLock_cond = WebViewClient_OnReceivedSslError_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut WebViewClient_OnReceivedSslError_cancel() : (execution(* SslErrorHandler+.cancel()) && this(hand) && cflow(call(* WebViewClient+.onReceivedSslError(*, hand, *)))) && MOP_CommonPointCut();
	before () : WebViewClient_OnReceivedSslError_cancel() {
		WebViewClient_OnReceivedSslErrorRuntimeMonitor.cancelEvent();
	}

	pointcut WebViewClient_OnReceivedSslError_proceed() : (execution(* SslErrorHandler+.proceed()) && this(hand) && cflow(call(* WebViewClient+.onReceivedSslError(*, hand, *)))) && MOP_CommonPointCut();
	before () : WebViewClient_OnReceivedSslError_proceed() {
		WebViewClient_OnReceivedSslErrorRuntimeMonitor.proceedEvent();
	}

	pointcut WebViewClient_OnReceivedSslError_receivedSslError() : (execution(* WebViewClient+.onReceivedSslError(*, hand, *))) && MOP_CommonPointCut();
	before () : WebViewClient_OnReceivedSslError_receivedSslError() {
		WebViewClient_OnReceivedSslErrorRuntimeMonitor.receivedSslErrorEvent();
	}

	pointcut WebViewClient_OnReceivedSslError_receivedSslErrorEnd() : (execution(boolean WebViewClient+.onReceivedSslError(*, hand, *))) && MOP_CommonPointCut();
	after () : WebViewClient_OnReceivedSslError_receivedSslErrorEnd() {
		WebViewClient_OnReceivedSslErrorRuntimeMonitor.receivedSslErrorEndEvent();
	}

}
