#!/bin/sh
echo "$1 is the first argument"
echo "$2 is the second argument"
echo "$3 is the third argument"
echo "$4 is the fourth argument"
echo "$@ is all arguments"
java -cp ../lib/clojure-1.2.0.jar:../lib/clargon-1.0.0.jar clojure.main starting_line/mymain.clj $1 $2 $3 $4