#!/bin/bash

JAVAMOP=javamop
AJC=ajc
JAVA=java

PACKAGE=java/io

EXAMPLEDIR=`pwd`
PROPERTYDIR=`pwd`/../properties
BUILDIR=`pwd`/build

properties=(
#	ByteArrayInputStream_Close
#	ByteArrayOutputStream_Close
	ByteArrayOutputStream_FlushBeforeRetrieve
)

function handle_property {
	packname=$1
	propname=$2

	moppath=$PROPERTYDIR/$packname/$propname.mop
	classdir=$BUILDIR/$packname/$propname
	ajdir=$classdir/mop

	# Build .aj from .mop
	mkdir -p $ajdir
	$JAVAMOP -d $ajdir $moppath

	# Compile .java and .aj together
	exampledir=$EXAMPLEDIR/$packname/$propname
	$AJC -1.6 -d $classdir $ajdir/${propname}MonitorAspect.aj $exampledir/*.java

	for example in `ls $exampledir/*.java`
	do
		fname=`basename $example`
		entry=${fname%.java}
		echo "   running $entry {{{"
		java -cp $classdir:$CLASSPATH $entry
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

