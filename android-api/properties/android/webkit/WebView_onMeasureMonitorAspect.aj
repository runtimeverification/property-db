package mop;
import android.webkit.*;
import android.view.View;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect WebView_onMeasureMonitorAspect implements rvmonitorrt.RVMObject {
	public WebView_onMeasureMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock WebView_onMeasure_MOPLock = new ReentrantLock();
	static Condition WebView_onMeasure_MOPLock_cond = WebView_onMeasure_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut WebView_OnMeasure_setDimension() : (execution(* View+.setMeasuredDimension(..)) && this(view) && cflow(call(* WebView+.onMeasure(..)))) && MOP_CommonPointCut();
	before () : WebView_OnMeasure_setDimension() {
		WebView_OnMeasureRuntimeMonitor.setDimensionEvent();
	}

	pointcut WebView_OnMeasure_onMeasure() : (execution(* WebView+.onMeasure(..)) && this(view)) && MOP_CommonPointCut();
	before () : WebView_OnMeasure_onMeasure() {
		WebView_OnMeasureRuntimeMonitor.onMeasureEvent();
	}

	pointcut WebView_OnMeasure_onMeasureEnd() : (execution(* WebView+.onMeasure(..)) && this(view)) && MOP_CommonPointCut();
	after () : WebView_OnMeasure_onMeasureEnd() {
		WebView_OnMeasureRuntimeMonitor.onMeasureEndEvent();
	}

}
