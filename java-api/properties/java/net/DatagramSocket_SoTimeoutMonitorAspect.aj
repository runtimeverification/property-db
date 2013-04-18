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

public aspect DatagramSocket_SoTimeoutMonitorAspect implements rvmonitorrt.RVMObject {
	public DatagramSocket_SoTimeoutMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock DatagramSocket_SoTimeout_MOPLock = new ReentrantLock();
	static Condition DatagramSocket_SoTimeout_MOPLock_cond = DatagramSocket_SoTimeout_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut DatagramSocket_SoTimeout_settimeout(int timeout) : (call(void DatagramSocket.setSoTimeout(int)) && args(timeout)) && MOP_CommonPointCut();
	before (int timeout) : DatagramSocket_SoTimeout_settimeout(timeout) {
		DatagramSocket_SoTimeoutRuntimeMonitor.settimeoutEvent(timeout);
	}

}
