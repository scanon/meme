#!/bin/bash

if (( $# != 1 ))
then
    echo "Usage: make_jar <main_class>"
    exit
fi

MAIN_CLASS=$1
DATE=$(date +"%Y%m%d%H%M")
JAR_DIR=./jar_$DATE
CURRENT_DIR="$(pwd)"
DIST_DIR=$CURRENT_DIR/dist
JAR_FILE=$DIST_DIR/meme_cluster.jar
SRC_DIR=./src
LIB_DIR=./lib
CLASSES_DIR=$JAR_DIR
CLASSPATH=

test -d "$DIST_DIR" || mkdir "$DIST_DIR"

if [ -d $LIB_DIR ]; then
    for a in $LIB_DIR/*.jar; do
	if [ $CLASSPATH ]  
	then  
	    CLASSPATH=$CLASSPATH:$a
	else  
            CLASSPATH=$a
        fi
    done
    CLASSPATH="-classpath $CLASSPATH"
fi

mkdir $JAR_DIR

JAVA_FILE=${MAIN_CLASS//\./\/}
javac -sourcepath $SRC_DIR $CLASSPATH -d $JAR_DIR -g $SRC_DIR/$JAVA_FILE.java


cat > $JAR_DIR/Manifest.txt <<EOF
Main-Class: $MAIN_CLASS
Class-Path: lib/bcpkix-jdk15on-147.jar lib/bcprov-ext-jdk15on-147.jar lib/commons-cli-1.2.jar lib/ini4j-0.5.2.jar lib/jackson-all-1.9.11.jar lib/jetty-all-7.0.0.jar lib/kbase-auth.jar lib/servlet-api-2.5.jar
EOF


if [ -f $JAR_FILE ]
then
    rm $JAR_FILE
fi

cd $JAR_DIR
jar cfm $JAR_FILE Manifest.txt *

cd ..
rm -rf $JAR_DIR


