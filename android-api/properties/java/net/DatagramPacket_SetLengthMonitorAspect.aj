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

public aspect DatagramPacket_SetLengthMonitorAspect implements rvmonitorrt.RVMObject {
	public DatagramPacket_SetLengthMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock DatagramPacket_SetLength_MOPLock = new ReentrantLock();
	static Condition DatagramPacket_SetLength_MOPLock_cond = DatagramPacket_SetLength_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut DatagramPacket_SetLength_setlength(DatagramPacket packet, int length) : (call(void DatagramPacket.setLength(int)) && target(packet) && args(length)) && MOP_CommonPointCut();
	before (DatagramPacket packet, int length) : DatagramPacket_SetLength_setlength(packet, length) {
		DatagramPacket_SetLengthRuntimeMonitor.setlengthEvent(packet, length);
	}

}
