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

public aspect CookieSyncManager_GetInstanceMonitorAspect implements rvmonitorrt.RVMObject {
	public CookieSyncManager_GetInstanceMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock CookieSyncManager_GetInstance_MOPLock = new ReentrantLock();
	static Condition CookieSyncManager_GetInstance_MOPLock_cond = CookieSyncManager_GetInstance_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut CookieSyncManager_GetInstance_getCookieSyncManagerInstance() : (execution(* CookieSyncManager.getInstance(Context))) && MOP_CommonPointCut();
	before () : CookieSyncManager_GetInstance_getCookieSyncManagerInstance() {
		CookieSyncManager_GetInstanceRuntimeMonitor.getCookieSyncManagerInstanceEvent();
	}

	pointcut CookieSyncManager_GetInstance_createCookieSyncManagerInstance() : (execution(* CookieSyncManager+.createInstance(..))) && MOP_CommonPointCut();
	after () : CookieSyncManager_GetInstance_createCookieSyncManagerInstance() {
		CookieSyncManager_GetInstanceRuntimeMonitor.createCookieSyncManagerInstanceEvent();
	}

}
