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

public aspect DatagramSocket_TrafficClassMonitorAspect implements rvmonitorrt.RVMObject {
	public DatagramSocket_TrafficClassMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock DatagramSocket_TrafficClass_MOPLock = new ReentrantLock();
	static Condition DatagramSocket_TrafficClass_MOPLock_cond = DatagramSocket_TrafficClass_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut DatagramSocket_TrafficClass_settc(DatagramSocket socket, int tc) : (call(void DatagramSocket.setTrafficClass(int)) && target(socket) && args(tc)) && MOP_CommonPointCut();
	before (DatagramSocket socket, int tc) : DatagramSocket_TrafficClass_settc(socket, tc) {
		DatagramSocket_TrafficClassRuntimeMonitor.settcEvent(socket, tc);
	}

}
