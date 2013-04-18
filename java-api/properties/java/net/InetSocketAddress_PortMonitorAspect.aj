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

public aspect InetSocketAddress_PortMonitorAspect implements rvmonitorrt.RVMObject {
	public InetSocketAddress_PortMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock InetSocketAddress_Port_MOPLock = new ReentrantLock();
	static Condition InetSocketAddress_Port_MOPLock_cond = InetSocketAddress_Port_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut InetSocketAddress_Port_construct(int port) : ((call(InetSocketAddress.new(int)) || call(InetSocketAddress.new(InetAddress, int)) || call(InetSocketAddress.new(String, int)) || call(* InetSocketAddress.createUnresolved(String, int))) && args(.., port)) && MOP_CommonPointCut();
	before (int port) : InetSocketAddress_Port_construct(port) {
		InetSocketAddress_PortRuntimeMonitor.constructEvent(port);
	}

}
