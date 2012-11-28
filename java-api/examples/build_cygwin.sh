#!/bin/bash

JAVAMOP=javamop.bat
AJC=ajc.bat
JAVA=java

PACKAGE=java/net

EXAMPLEDIR=`pwd`
PROPERTYDIR=`pwd`/../properties
BUILDIR=`pwd`/build

properties=(
#	Authenticator_OverrideGetPasswordAuthentication
#	ContentHandler_GetContent
#	DatagramPacket_Length
#	DatagramPacket_SetLength
#	DatagramSocket_Port
#	DatagramSocket_SoTimeout
#	DatagramSocket_TrafficClass
#	HttpCookie_Name
#	HttpURLConnection_SetBeforeConnect
#	IDN_ToAscii
#	InetAddress_IsReachable
#	InetSocketAddress_Port
#	MulticastSocket_TTL
#	NetPermission_Name
#	NetPermission_Actions
#	PasswordAuthentication_FillZeroPassword
#	ServerSocket_Backlog
#	ServerSocket_Port
#	ServerSocket_SetTimeoutBeforeBlocking
#	ServerSocket_Timeout
#	ServerSocket_ReuseAddress
#	ServerSocket_LargeReceiveBuffer
#	ServerSocket_PerformancePreferences
#	Socket_InputStreamUnavailable
#	Socket_OutputStreamUnavailable
#	Socket_Timeout
#	Socket_SetTimeoutBeforeBlockingInput
#	Socket_LargeReceiveBuffer
#	Socket_TrafficClass
#	Socket_ReuseAddress
#	Socket_ReuseSocket
#	Socket_CloseInput
#	Socket_CloseOutput
#	Socket_PerformancePreferences
#	SocketImpl_CloseOutput
#	SocketPermission_Actions
#	URL_SetURLStreamHandlerFactory
#	URLConnection_Connect
#	URLConnection_SetBeforeConnect
#	URLConnection_OverrideGetPermission
#	URLDecoder_DecodeUTF8
#	URLEncoder_EncodeUTF8
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
	echo $AJC -1.6 -d $classdir $ajpath $examplepaths

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

