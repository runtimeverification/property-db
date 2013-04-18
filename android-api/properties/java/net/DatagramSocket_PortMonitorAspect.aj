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

public aspect DatagramSocket_PortMonitorAspect implements rvmonitorrt.RVMObject {
	public DatagramSocket_PortMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock DatagramSocket_Port_MOPLock = new ReentrantLock();
	static Condition DatagramSocket_Port_MOPLock_cond = DatagramSocket_Port_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut DatagramSocket_Port_construct_port(int port) : ((call(DatagramSocket.new(int)) || call(DatagramSocket.new(int, InetAddress))) && args(port, ..)) && MOP_CommonPointCut();
	before (int port) : DatagramSocket_Port_construct_port(port) {
		DatagramSocket_PortRuntimeMonitor.construct_portEvent(port);
	}

}
