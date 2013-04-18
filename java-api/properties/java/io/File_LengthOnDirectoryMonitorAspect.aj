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

public aspect File_LengthOnDirectoryMonitorAspect implements rvmonitorrt.RVMObject {
	public File_LengthOnDirectoryMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock File_LengthOnDirectory_MOPLock = new ReentrantLock();
	static Condition File_LengthOnDirectory_MOPLock_cond = File_LengthOnDirectory_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut File_LengthOnDirectory_bad_length(File f) : (call(* File+.length()) && target(f)) && MOP_CommonPointCut();
	before (File f) : File_LengthOnDirectory_bad_length(f) {
		File_LengthOnDirectoryRuntimeMonitor.bad_lengthEvent(f);
	}

}
