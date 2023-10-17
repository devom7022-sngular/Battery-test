#!/usr/bin/env bash

let STRICT_MODE_PARAM="$1"
let PIVOT_PARAM=$2
let STAGE_SUCCESS="true"

cd ${WORKSPACE}
filename='batterystats.txt'
n=1

while read line; do

    if [[ $line == *"Estimated power use"* ]]; then
        echo -e "\033[34mLine No. $n : $line\033[0m"
    fi

    if [[ $line == *"Total cpu time"* ]]; then
        echo -e "\033[34mLine No. $n : $line\033[0m"
    fi

    if [[ $line == *"Proc"* ]]; then
        echo -e "\033[34mLine No. $n : $line\033[0m"
    fi

    n=$((n+1))

done < "$filename"

if ["$STRICT_MODE_PARAM" = "true" && "$STAGE_SUCCESS" = "true" ]; then
  error("NOT OPTIMAL ENERGY EFFICIENCY")
else
  echo "OPTIMAL ENERGY EFFICIENCY"
fi