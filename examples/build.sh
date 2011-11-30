#!/bin/bash

JAVAMOP=javamop.bat
AJC=ajc.bat
JAVA=java

PACKAGE=java/io

EXAMPLEDIR=`pwd`
PROPERTYDIR=`pwd`/../properties
BUILDIR=`pwd`/build

properties=(
#	ByteArrayInputStream_Close
#	ByteArrayOutputStream_Close
#	ByteArrayOutputStream_FlushBeforeRetrieve
#	CharArrayWriter_Close
#	Closeable_MultipleClose
#	Console_CloseReader
#	Console_CloseWriter
#	Console_FillZeroPassword
#	File_DeleteTempFile
#	File_LengthOnDirectory
#	InputStream_ManipulateAfterClose
#	InputStream_MarkAfterClose
#	InputStream_MarkReset
#	InputStream_ReadAheadLimit
#	InputStream_UnmarkedReset
#	ObjectInput_Close
#	ObjectOutput_Close
#	OutputStream_ManipulateAfterClose
#	PipedInputStream_UnconnectedRead
#	PipedOutputStream_UnconnectedWrite
	PipedStream_SingleThread
# XXX
#	PushbackInputStream_UnreadAheadLimit
#	RandomAccessFile_ManipulateAfterClose
#	Reader_ManipulateAfterClose
#	Reader_MarkReset
#	Reader_ReadAheadLimit
#	Reader_UnmarkedReset
#	Serializable_UID
#	Serializable_NoArgConstructor
#	StreamTokenizer_AccessInvalidField
#	StringWriter_Close
#	Writer_ManipulateAfterClose
)

function handle_property {
	packname=$1
	propname=$2

	moppath=`cygpath -w $PROPERTYDIR/$packname/$propname.mop`
	classdir=`cygpath -w $BUILDIR/$packname/$propname`
	ajdir=$classdir/mop

	# clean up
	rm -rf $classdir

	# Build .aj from .mop
	mkdir -p $ajdir
	$JAVAMOP -d $ajdir $moppath || exit 1

	# Compile .java and .aj together
	ajpath=$ajdir/${propname}MonitorAspect.aj
	exampledir=$EXAMPLEDIR/$packname/$propname
	examplepaths=`cygpath -w $exampledir/*.java`
	$AJC -1.6 -d $classdir $ajpath $examplepaths || exit 2

	for example in `ls $exampledir/*.java`
	do
		fname=`basename $example`
		entry=${fname%.java}
		echo "   running $entry {{{"
		java -cp "$classdir;$CLASSPATH" $entry
		echo "   }}}"
	done
}

for prop in "${properties[@]}"
do
	target=$EXAMPLEDIR/$PACKAGE/$prop
	echo "handling $PACKAGE.$prop ... {{{"
	cd $target
	handle_property $PACKAGE $prop
	cd ..
	echo "}}}"
done

