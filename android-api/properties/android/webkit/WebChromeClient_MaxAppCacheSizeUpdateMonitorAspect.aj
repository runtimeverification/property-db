package mop;
import android.webkit.*;
import android.webkit.Webstorage.QuotaUpdater;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect WebChromeClient_MaxAppCacheSizeUpdateMonitorAspect implements rvmonitorrt.RVMObject {
	public WebChromeClient_MaxAppCacheSizeUpdateMonitorAspect(){
		Runtime.getRuntime().addShutdownHook(new WebChromeClient_MaxAppCacheSizeUpdate_DummyHookThread());
	}

	// Declarations for the Lock
	static ReentrantLock WebChromeClient_MaxAppCacheSizeUpdate_MOPLock = new ReentrantLock();
	static Condition WebChromeClient_MaxAppCacheSizeUpdate_MOPLock_cond = WebChromeClient_MaxAppCacheSizeUpdate_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut WebChromeClient_MaxAppCacheSizeUpdate_goodUpdateQuota(QuotaUpdater u, long quota) : (call(* QuotaUpdater+.updateQuota(long)) && target(u)) && MOP_CommonPointCut();
	before (QuotaUpdater u, long quota) : WebChromeClient_MaxAppCacheSizeUpdate_goodUpdateQuota(u, quota) {
		//WebChromeClient_MaxAppCacheSizeUpdate_badUpdateQuota
		WebChromeClient_MaxAppCacheSizeUpdateRuntimeMonitor.badUpdateQuotaEvent(u, quota);
		//WebChromeClient_MaxAppCacheSizeUpdate_goodUpdateQuota
		WebChromeClient_MaxAppCacheSizeUpdateRuntimeMonitor.goodUpdateQuotaEvent(u, quota);
	}

	pointcut WebChromeClient_MaxAppCacheSizeUpdate_onReachedMaxAppCacheSizeUpdate(WebChromeClient c, QuotaUpdater u, int quota) : (execution(* WebChromeClient+.onReachedMaxAppCacheSizeUpdate(long, long, QuotaUpdater)) && this(c) && args(long, quota, u)) && MOP_CommonPointCut();
	before (WebChromeClient c, QuotaUpdater u, int quota) : WebChromeClient_MaxAppCacheSizeUpdate_onReachedMaxAppCacheSizeUpdate(c, u, quota) {
		WebChromeClient_MaxAppCacheSizeUpdateRuntimeMonitor.onReachedMaxAppCacheSizeUpdateEvent(c, u, quota);
	}

	class WebChromeClient_MaxAppCacheSizeUpdate_DummyHookThread extends Thread {
		public void run(){
			WebChromeClient_MaxAppCacheSizeUpdateRuntimeMonitor.endProgEvent();
		}
	}
}
