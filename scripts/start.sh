#!/usr/bin/env bash

mysql -uroot -e "create database IF NOT EXISTS app_graphs"
java -jar target/highchart-template-1.0-SNAPSHOT.jar server app.yml

