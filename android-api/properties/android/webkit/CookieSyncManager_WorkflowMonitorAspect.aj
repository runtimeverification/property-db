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

public aspect CookieSyncManager_WorkflowMonitorAspect implements rvmonitorrt.RVMObject {
	public CookieSyncManager_WorkflowMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock CookieSyncManager_Workflow_MOPLock = new ReentrantLock();
	static Condition CookieSyncManager_Workflow_MOPLock_cond = CookieSyncManager_Workflow_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut CookieSyncManager_GetInstance_stopSync() : (execution(* CookieSyncManager.stopSync()) && this(csm) && !cflow(call(* Activity.onPause()))) && MOP_CommonPointCut();
	before () : CookieSyncManager_GetInstance_stopSync() {
		//CookieSyncManager_GetInstance_badstopSync
		CookieSyncManager_GetInstanceRuntimeMonitor.badstopSyncEvent();
		//CookieSyncManager_GetInstance_stopSync
		CookieSyncManager_GetInstanceRuntimeMonitor.stopSyncEvent();
	}

	pointcut CookieSyncManager_GetInstance_badstartSync() : (execution(* CookieSyncManager.startSync()) && this(csm) && !cflow(call(* Activity.onResume()))) && MOP_CommonPointCut();
	before () : CookieSyncManager_GetInstance_badstartSync() {
		CookieSyncManager_GetInstanceRuntimeMonitor.badstartSyncEvent();
	}

	pointcut CookieSyncManager_GetInstance_startSync() : (execution(* CookieSyncManager.startSync()) && this(csm) && cflow(call(* Activity.onResume()))) && MOP_CommonPointCut();
	before () : CookieSyncManager_GetInstance_startSync() {
		CookieSyncManager_GetInstanceRuntimeMonitor.startSyncEvent();
	}

	pointcut CookieSyncManager_GetInstance_createCookieSyncManagerInstance() : (execution(* CookieSyncManager+.createInstance(..))) && MOP_CommonPointCut();
	after () returning (CookieSyncManager csm) : CookieSyncManager_GetInstance_createCookieSyncManagerInstance() {
		CookieSyncManager_GetInstanceRuntimeMonitor.createCookieSyncManagerInstanceEvent(csm);
	}

}
