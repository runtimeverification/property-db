package mop;
import java.io.*;
import java.lang.*;
import rvmonitorrt.MOPLogging;
import rvmonitorrt.MOPLogging.Level;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import rvmonitorrt.*;
import java.lang.ref.*;
import org.aspectj.lang.*;

public aspect Character_ValidateCharMonitorAspect implements rvmonitorrt.RVMObject {
	public Character_ValidateCharMonitorAspect(){
	}

	// Declarations for the Lock
	static ReentrantLock Character_ValidateChar_MOPLock = new ReentrantLock();
	static Condition Character_ValidateChar_MOPLock_cond = Character_ValidateChar_MOPLock.newCondition();

	pointcut MOP_CommonPointCut() : !within(rvmonitorrt.RVMObject+) && !adviceexecution();
	pointcut Character_ValidateChar_toCodePoint(char high, char low) : (call(* Character.toCodePoint(char, char)) && args(high, low)) && MOP_CommonPointCut();
	before (char high, char low) : Character_ValidateChar_toCodePoint(high, low) {
		Character_ValidateCharRuntimeMonitor.toCodePointEvent(high, low);
	}

	pointcut Character_ValidateChar_charCount(int codePoint) : (call(* Character.charCount(int)) && args(codePoint)) && MOP_CommonPointCut();
	before (int codePoint) : Character_ValidateChar_charCount(codePoint) {
		Character_ValidateCharRuntimeMonitor.charCountEvent(codePoint);
	}

}
