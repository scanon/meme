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
#JAR_LIB_DIR=$JAR_DIR/lib 
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
#mkdir $JAR_LIB_DIR
#mkdir $CLASSES_DIR

JAVA_FILE=${MAIN_CLASS//\./\/}
javac -sourcepath $SRC_DIR $CLASSPATH -d $JAR_DIR -g $SRC_DIR/$JAVA_FILE.java

#if [ -d $LIB_DIR ]; then
#    cp -R $LIB_DIR $JAR_DIR
#fi
if [ -f $JAR_FILE ]
then
    rm $JAR_FILE
fi

cd $JAR_DIR
jar cfe $JAR_FILE $MAIN_CLASS *

cd ..
rm -rf $JAR_DIR


