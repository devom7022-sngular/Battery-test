#!/bin/bash
cd ${WORKSPACE}
filename='batterystats.txt'
n=1
while read line; do

    if [[ $line == *"Estimated power use"* ]]; then
        echo -e "\e[34mLine No. $n : $line\e[0m"
    fi

    if [[ $line == *"Total cpu time"* ]]; then
        echo -e "\e[34mLine No. $n : $line\e[0m"
    fi

    if [[ $line == *"Proc"* ]]; then
        echo -e "\e[34mLine No. $n : $line\e[0m"
    fi

    n=$((n+1))

done < "$filename"