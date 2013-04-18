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

public aspect File_DeleteTempFileMonitorAspect implements rvmonitorrt.RVMObject {
	public File_DeleteTempFileMonitorAspect(){
		Runtime.getRuntime().addShutdownHook(new File_DeleteTempFile_DummyHookThread());
	}

	// Declarations for the Lock
	static ReentrantLock File_DeleteTempFile_MOPLock = new ReentrantLock();
	static Condition File_DeleteTempFile_MOPLock_cond = File_DeleteTempFile_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut File_DeleteTempFile_implicit(File f) : (call(* File+.deleteOnExit(..)) && target(f)) && MOP_CommonPointCut();
	before (File f) : File_DeleteTempFile_implicit(f) {
		File_DeleteTempFileRuntimeMonitor.implicitEvent(f);
	}

	pointcut File_DeleteTempFile_explicit(File f) : (call(* File+.delete(..)) && target(f)) && MOP_CommonPointCut();
	before (File f) : File_DeleteTempFile_explicit(f) {
		File_DeleteTempFileRuntimeMonitor.explicitEvent(f);
	}

	pointcut File_DeleteTempFile_create() : (call(File+ File.createTempFile(..))) && MOP_CommonPointCut();
	after () returning (File f) : File_DeleteTempFile_create() {
		File_DeleteTempFileRuntimeMonitor.createEvent(f);
	}

	class File_DeleteTempFile_DummyHookThread extends Thread {
		public void run(){
			File_DeleteTempFileRuntimeMonitor.endProgEvent();
		}
	}
}
