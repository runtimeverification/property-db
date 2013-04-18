package mop;
import java.io.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect PipedOutputStream_UnconnectedWriteMonitorAspect implements rvmonitorrt.RVMObject {
	public PipedOutputStream_UnconnectedWriteMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock PipedOutputStream_UnconnectedWrite_MOPLock = new ReentrantLock();
	static Condition PipedOutputStream_UnconnectedWrite_MOPLock_cond = PipedOutputStream_UnconnectedWrite_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut PipedOutputStream_UnconnectedWrite_write(PipedOutputStream o) : (call(* PipedOutputStream+.write(..)) && target(o)) && MOP_CommonPointCut();
	before (PipedOutputStream o) : PipedOutputStream_UnconnectedWrite_write(o) {
		PipedOutputStream_UnconnectedWriteRuntimeMonitor.writeEvent(o);
	}

	pointcut PipedOutputStream_UnconnectedWrite_connect_4(PipedOutputStream o) : (call(* PipedOutputStream+.connect(PipedInputStream+)) && target(o)) && MOP_CommonPointCut();
	before (PipedOutputStream o) : PipedOutputStream_UnconnectedWrite_connect_4(o) {
		PipedOutputStream_UnconnectedWriteRuntimeMonitor.connectEvent(o);
	}

	pointcut PipedOutputStream_UnconnectedWrite_connect_3(PipedOutputStream o) : (call(* PipedInputStream+.connect(PipedOutputStream+)) && args(o)) && MOP_CommonPointCut();
	before (PipedOutputStream o) : PipedOutputStream_UnconnectedWrite_connect_3(o) {
		PipedOutputStream_UnconnectedWriteRuntimeMonitor.connectEvent(o);
	}

	pointcut PipedOutputStream_UnconnectedWrite_create_oi(PipedOutputStream o) : (call(PipedInputStream+.new(PipedOutputStream+)) && args(o)) && MOP_CommonPointCut();
	before (PipedOutputStream o) : PipedOutputStream_UnconnectedWrite_create_oi(o) {
		PipedOutputStream_UnconnectedWriteRuntimeMonitor.create_oiEvent(o);
	}

	pointcut PipedOutputStream_UnconnectedWrite_create() : (call(PipedOutputStream+.new())) && MOP_CommonPointCut();
	after () returning (PipedOutputStream o) : PipedOutputStream_UnconnectedWrite_create() {
		PipedOutputStream_UnconnectedWriteRuntimeMonitor.createEvent(o);
	}

	pointcut PipedOutputStream_UnconnectedWrite_create_io() : (call(PipedOutputStream+.new(PipedInputStream+))) && MOP_CommonPointCut();
	after () returning (PipedOutputStream o) : PipedOutputStream_UnconnectedWrite_create_io() {
		PipedOutputStream_UnconnectedWriteRuntimeMonitor.create_ioEvent(o);
	}

}
