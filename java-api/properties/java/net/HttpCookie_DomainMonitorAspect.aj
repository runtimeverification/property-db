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

public aspect HttpCookie_DomainMonitorAspect implements rvmonitorrt.RVMObject {
	public HttpCookie_DomainMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock HttpCookie_Domain_MOPLock = new ReentrantLock();
	static Condition HttpCookie_Domain_MOPLock_cond = HttpCookie_Domain_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut HttpCookie_Domain_setdomain(String domain) : (call(void HttpCookie.setDomain(String)) && args(domain)) && MOP_CommonPointCut();
	before (String domain) : HttpCookie_Domain_setdomain(domain) {
		HttpCookie_DomainRuntimeMonitor.setdomainEvent(domain);
	}

}
