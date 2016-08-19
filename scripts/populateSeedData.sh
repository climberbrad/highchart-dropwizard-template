#!/usr/bin/env bash

sec_per_day=86164
timestamp=$(date +%s)
start=$((timestamp - (sec_per_day * 24)))
for i in {1..25}; do
    start=$((start + sec_per_day))
    randomFloat=`awk -v "seed=$[(RANDOM & 32767) + 32768 * (RANDOM & 32767)]" 'BEGIN { srand(seed); printf("%.2f\n", rand() * 100.0) }'`
    mysql -uroot app_graphs -e "insert into line_chart values('line-chart-1', $start, $randomFloat)"
done
