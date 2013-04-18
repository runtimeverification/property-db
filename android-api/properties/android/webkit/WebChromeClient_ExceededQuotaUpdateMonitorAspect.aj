package mop;
import android.webkit.*;
import android.webkit.WebStorage.QuotaUpdater;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect WebChromeClient_ExceededQuotaUpdateMonitorAspect implements rvmonitorrt.RVMObject {
	public WebChromeClient_ExceededQuotaUpdateMonitorAspect(){
		Runtime.getRuntime().addShutdownHook(new WebChromeClient_ExceededQuotaUpdate_DummyHookThread());
	}

	// Declarations for the Lock
	static ReentrantLock WebChromeClient_ExceededQuotaUpdate_MOPLock = new ReentrantLock();
	static Condition WebChromeClient_ExceededQuotaUpdate_MOPLock_cond = WebChromeClient_ExceededQuotaUpdate_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut WebChromeClient_ExceededQuotaUpdate_goodUpdateQuota(QuotaUpdater u, long quota) : (call(* QuotaUpdater+.updateQuota(long)) && target(u)) && MOP_CommonPointCut();
	before (QuotaUpdater u, long quota) : WebChromeClient_ExceededQuotaUpdate_goodUpdateQuota(u, quota) {
		//WebChromeClient_ExceededQuotaUpdate_badUpdateQuota
		WebChromeClient_ExceededQuotaUpdateRuntimeMonitor.badUpdateQuotaEvent(u, quota);
		//WebChromeClient_ExceededQuotaUpdate_goodUpdateQuota
		WebChromeClient_ExceededQuotaUpdateRuntimeMonitor.goodUpdateQuotaEvent(u, quota);
	}

	pointcut WebChromeClient_ExceededQuotaUpdate_onExceededDatabaseQuota(WebChromeClient c, Webstorage.QuotaUpdater u, int quota) : (execution(* WebChromeClient+.onExceededDatabaseQuota(String, String, long, long, long, QuotaUpdater)) && this(c) && args(String, String, quota, long, long, u)) && MOP_CommonPointCut();
	before (WebChromeClient c, Webstorage.QuotaUpdater u, int quota) : WebChromeClient_ExceededQuotaUpdate_onExceededDatabaseQuota(c, u, quota) {
		WebChromeClient_ExceededQuotaUpdateRuntimeMonitor.onExceededDatabaseQuotaEvent(c, u, quota);
	}

	class WebChromeClient_ExceededQuotaUpdate_DummyHookThread extends Thread {
		public void run(){
			WebChromeClient_ExceededQuotaUpdateRuntimeMonitor.endProgEvent();
		}
	}
}
