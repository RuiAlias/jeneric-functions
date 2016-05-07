#!/bin/bash
for f in examples/*.java
do
    filename=${f##*/}
    class=${filename%.*}
    echo "Testing class ${class}..."
    java -cp build/main:build/examples ${class} 2>&1 | diff examples/out${class}.txt -
done
