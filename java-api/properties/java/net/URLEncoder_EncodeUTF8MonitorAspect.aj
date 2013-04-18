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

public aspect URLEncoder_EncodeUTF8MonitorAspect implements rvmonitorrt.RVMObject {
	public URLEncoder_EncodeUTF8MonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock URLEncoder_EncodeUTF8_MOPLock = new ReentrantLock();
	static Condition URLEncoder_EncodeUTF8_MOPLock_cond = URLEncoder_EncodeUTF8_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut URLEncoder_EncodeUTF8_encode(String enc) : (call(* URLEncoder.encode(String, String)) && args(*, enc)) && MOP_CommonPointCut();
	before (String enc) : URLEncoder_EncodeUTF8_encode(enc) {
		URLEncoder_EncodeUTF8RuntimeMonitor.encodeEvent(enc);
	}

}
