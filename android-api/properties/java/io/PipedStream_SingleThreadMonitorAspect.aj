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

public aspect PipedStream_SingleThreadMonitorAspect implements rvmonitorrt.RVMObject {
	public PipedStream_SingleThreadMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock PipedStream_SingleThread_MOPLock = new ReentrantLock();
	static Condition PipedStream_SingleThread_MOPLock_cond = PipedStream_SingleThread_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut PipedStream_SingleThread_read(PipedInputStream i) : (call(* PipedInputStream+.read(..)) && target(i)) && MOP_CommonPointCut();
	before (PipedInputStream i) : PipedStream_SingleThread_read(i) {
		Thread t = Thread.currentThread();
		PipedStream_SingleThreadRuntimeMonitor.readEvent(i, t);
	}

	pointcut PipedStream_SingleThread_write(PipedOutputStream o) : (call(* PipedOutputStream+.write(..)) && target(o)) && MOP_CommonPointCut();
	before (PipedOutputStream o) : PipedStream_SingleThread_write(o) {
		Thread t = Thread.currentThread();
		PipedStream_SingleThreadRuntimeMonitor.writeEvent(o, t);
	}

	pointcut PipedStream_SingleThread_create_8(PipedOutputStream o, PipedInputStream i) : (call(* PipedOutputStream+.connect(PipedInputStream+)) && target(o) && args(i)) && MOP_CommonPointCut();
	before (PipedOutputStream o, PipedInputStream i) : PipedStream_SingleThread_create_8(o, i) {
		PipedStream_SingleThreadRuntimeMonitor.createEvent(o, i);
	}

	pointcut PipedStream_SingleThread_create_6(PipedInputStream i, PipedOutputStream o) : (call(* PipedInputStream+.connect(PipedOutputStream+)) && target(i) && args(o)) && MOP_CommonPointCut();
	before (PipedInputStream i, PipedOutputStream o) : PipedStream_SingleThread_create_6(i, o) {
		PipedStream_SingleThreadRuntimeMonitor.createEvent(i, o);
	}

	pointcut PipedStream_SingleThread_create_5(PipedOutputStream o) : (call(PipedInputStream+.new(PipedOutputStream+)) && args(o)) && MOP_CommonPointCut();
	after (PipedOutputStream o) returning (PipedInputStream i) : PipedStream_SingleThread_create_5(o) {
		PipedStream_SingleThreadRuntimeMonitor.createEvent(o, i);
	}

	pointcut PipedStream_SingleThread_create_7(PipedInputStream i) : (call(PipedOutputStream+.new(PipedInputStream+)) && args(i)) && MOP_CommonPointCut();
	after (PipedInputStream i) returning (PipedOutputStream o) : PipedStream_SingleThread_create_7(i) {
		PipedStream_SingleThreadRuntimeMonitor.createEvent(i, o);
	}

}
