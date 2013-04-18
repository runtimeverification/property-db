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

public aspect DatagramPacket_LengthMonitorAspect implements rvmonitorrt.RVMObject {
	public DatagramPacket_LengthMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock DatagramPacket_Length_MOPLock = new ReentrantLock();
	static Condition DatagramPacket_Length_MOPLock_cond = DatagramPacket_Length_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut DatagramPacket_Length_construct_offlen(byte[] buffer, int offset, int length) : ((call(DatagramPacket.new(byte[], int, int)) || call(DatagramPacket.new(byte[], int, int, InetAddress, int)) || call(DatagramPacket.new(byte[], int, int, SocketAddress))) && args(buffer, offset, length, ..)) && MOP_CommonPointCut();
	before (byte[] buffer, int offset, int length) : DatagramPacket_Length_construct_offlen(buffer, offset, length) {
		DatagramPacket_LengthRuntimeMonitor.construct_offlenEvent(buffer, offset, length);
	}

	pointcut DatagramPacket_Length_construct_len(byte[] buffer, int length) : ((call(DatagramPacket.new(byte[], int)) || call(DatagramPacket.new(byte[], int, InetAddress, int)) || call(DatagramPacket.new(byte[], int, SocketAddress))) && args(buffer, length, ..)) && MOP_CommonPointCut();
	before (byte[] buffer, int length) : DatagramPacket_Length_construct_len(buffer, length) {
		DatagramPacket_LengthRuntimeMonitor.construct_lenEvent(buffer, length);
	}

}
