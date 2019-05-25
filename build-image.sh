#!/usr/bin/env bash
mvn clean package -DskipTests -Pprod jib:dockerBuild
HOST=$(kubectl config view --minify -o go-template='{{with index .clusters 0 }}{{.cluster.server}}{{end}}'|awk -F[/:] '{print $4}')
USER=$(kubectl config view --minify -o go-template='{{with index .users 0 }}{{.user.username}}{{end}}')
PWD=$(kubectl config view --minify -o go-template='{{with index .users 0 }}{{.user.password}}{{end}}')
docker login -u $USER  -p $PWD $HOST:32000
docker tag entando/entando-avatar-plugin:5.2.0-SNAPSHOT $HOST:32000/entando/entando-avatar-plugin:5.2.0-SNAPSHOT
docker push $HOST:32000/entando/entando-avatar-plugin:5.2.0-SNAPSHOT
