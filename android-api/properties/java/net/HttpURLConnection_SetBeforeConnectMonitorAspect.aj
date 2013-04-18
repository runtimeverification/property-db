package mop;
import java.net.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect HttpURLConnection_SetBeforeConnectMonitorAspect implements rvmonitorrt.RVMObject {
	public HttpURLConnection_SetBeforeConnectMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock HttpURLConnection_SetBeforeConnect_MOPLock = new ReentrantLock();
	static Condition HttpURLConnection_SetBeforeConnect_MOPLock_cond = HttpURLConnection_SetBeforeConnect_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut HttpURLConnection_SetBeforeConnect_connect(HttpURLConnection c) : ((call(* URLConnection+.connect(..)) || call(* URLConnection+.getContent(..)) || call(* URLConnection+.getContentEncoding(..)) || call(* URLConnection+.getContentLength(..)) || call(* URLConnection+.getContentType(..)) || call(* URLConnection+.getDate(..)) || call(* URLConnection+.getExpiration(..)) || call(* URLConnection+.getHeaderField(..)) || call(* URLConnection+.getHeaderFieldInt(..)) || call(* URLConnection+.getHeaderFields(..)) || call(* URLConnection+.getInputStream(..)) || call(* URLConnection+.getLastModified(..)) || call(* URLConnection+.getOutputStream(..)) || call(* HttpURLConnection+.getErrorStream(..)) || call(* HttpURLConnection+.getHeaderFieldDate(..)) || call(* HttpURLConnection+.getHeaderFieldKey(..)) || call(* HttpURLConnection+.getResponseCode(..)) || call(* HttpURLConnection+.getResponseMessage(..))) && target(c)) && MOP_CommonPointCut();
	before (HttpURLConnection c) : HttpURLConnection_SetBeforeConnect_connect(c) {
		HttpURLConnection_SetBeforeConnectRuntimeMonitor.connectEvent(c);
	}

	pointcut HttpURLConnection_SetBeforeConnect_set(HttpURLConnection c) : ((call(* HttpURLConnection+.setFixedLengthStreamingMode(..)) || call(* HttpURLConnection+.setChunkedStreamingMode(..)) || call(* HttpURLConnection+.setRequestMethod(..))) && target(c)) && MOP_CommonPointCut();
	before (HttpURLConnection c) : HttpURLConnection_SetBeforeConnect_set(c) {
		HttpURLConnection_SetBeforeConnectRuntimeMonitor.setEvent(c);
	}

}
