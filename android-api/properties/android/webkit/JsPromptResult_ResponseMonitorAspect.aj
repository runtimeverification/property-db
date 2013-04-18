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

public aspect JsPromptResult_ResponseMonitorAspect implements rvmonitorrt.RVMObject {
	public JsPromptResult_ResponseMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock JsPromptResult_Response_MOPLock = new ReentrantLock();
	static Condition JsPromptResult_Response_MOPLock_cond = JsPromptResult_Response_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut JsPromptResult_Response_cancel() : (execution(* JsPromptResult+.cancel()) && this(jpr) && cflow(call(boolean WebChromeClient+.onJsPrompt(.., jpr)))) && MOP_CommonPointCut();
	before () : JsPromptResult_Response_cancel() {
		JsPromptResult_ResponseRuntimeMonitor.cancelEvent();
	}

	pointcut JsPromptResult_Response_confirm() : (execution(* JsPromptResult+.confirm()) && this(jpr) && cflow(call(boolean WebChromeClient+.onJsPrompt(.., jpr)))) && MOP_CommonPointCut();
	before () : JsPromptResult_Response_confirm() {
		JsPromptResult_ResponseRuntimeMonitor.confirmEvent();
	}

	pointcut JsPromptResult_Response_jsPromptStart() : (execution(boolean WebChromeClient+.onJsPrompt(.., jpr))) && MOP_CommonPointCut();
	before () : JsPromptResult_Response_jsPromptStart() {
		JsPromptResult_ResponseRuntimeMonitor.jsPromptStartEvent();
	}

	pointcut JsPromptResult_Response_jsPromptEnd() : (execution(boolean WebChromeClient+.onJsPrompt(.., jpr))) && MOP_CommonPointCut();
	after () : JsPromptResult_Response_jsPromptEnd() {
		JsPromptResult_ResponseRuntimeMonitor.jsPromptEndEvent();
	}

}
