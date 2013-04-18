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

public aspect CookieManager_GetInstanceMonitorAspect implements rvmonitorrt.RVMObject {
	public CookieManager_GetInstanceMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock CookieManager_GetInstance_MOPLock = new ReentrantLock();
	static Condition CookieManager_GetInstance_MOPLock_cond = CookieManager_GetInstance_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut CookieManager_GetInstance_getCookieManagerInstance() : (execution(* CookieManager.getInstance(Context))) && MOP_CommonPointCut();
	before () : CookieManager_GetInstance_getCookieManagerInstance() {
		CookieManager_GetInstanceRuntimeMonitor.getCookieManagerInstanceEvent();
	}

	pointcut CookieManager_GetInstance_webViewInstance() : (execution(WebView+.new(..))) && MOP_CommonPointCut();
	after () : CookieManager_GetInstance_webViewInstance() {
		CookieManager_GetInstanceRuntimeMonitor.webViewInstanceEvent();
	}

	pointcut CookieManager_GetInstance_createCookieSyncManagerInstance() : (execution(* CookieSyncManager+.createInstance(..))) && MOP_CommonPointCut();
	after () : CookieManager_GetInstance_createCookieSyncManagerInstance() {
		CookieManager_GetInstanceRuntimeMonitor.createCookieSyncManagerInstanceEvent();
	}

}
