#!/bin/bash
filename=${WORDPACE}/batterystats.txt
n=1
while read line; do

    if [[ $line == *"Estimated power use"* ]]; then
        echo "Line No. $n : $line"
    fi

    if [[ $line == *"Total cpu time"* ]]; then
        echo "Line No. $n : $line"
    fi

    if [[ $line == *"Proc"* ]]; then
        echo "Line No. $n : $line"
    fi

    n=$((n+1))

done < "$filename"
