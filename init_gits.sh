#!/usr/bin/env bash
git init
git submodule add git@git.next-wireless.co:/git/pap_common_gradle_scripts pap_common_gradle_scripts

#Scala specific
git submodule add git@git.next-wireless.co:/git/common/getquill common/getquill
git submodule add git@git.next-wireless.co:/git/common/ws/awslambdahttp4s common/ws/awslambdahttp4s
git submodule add git@git.next-wireless.co:/git/common/ws/http4s common/ws/http4s
git submodule add git@git.next-wireless.co:/git/common/scala common/scala

#Not for Scala. Java projects
#git submodule add git@git.next-wireless.co:/git/pap_common_test_module pap_common_test_module
#git submodule add git@git.next-wireless.co:/git/common_ws_metrics_module common_ws_metrics_module
#git submodule add git@git.next-wireless.co:/git/common_ws_module common_ws_module
#git submodule add git@git.next-wireless.co:/git/common_scala_module common_scala_module
#git submodule add git@git.next-wireless.co:/git/common_scala_test_module common_scala_test_module

#SWAGGER templates
#git submodule add git@git.next-wireless.co:/git/swagger/templates/scalatra swagger/templates/scalatra
#git submodule add git@git.next-wireless.co:/git/swagger/templates/java_server_templates swagger/templates/java_server_templates
#git submodule add git@git.next-wireless.co:/git/swagger/templates/java_client swagger/templates/java_client
#git submodule add git@git.next-wireless.co:/git/swagger/templates/swift3 swagger/templates/swift3
#git submodule add git@git.next-wireless.co:/git/swagger/templates/aws_lambda_scala_api swagger/templates/aws_lambda_scala_api
#git submodule add git@git.next-wireless.co:/git/swagger/templates/http4s_client swagger/templates/http4s_client
git submodule add git@git.next-wireless.co:/git/swagger/templates/http4s_server swagger/templates/http4s_server

#APIs
#git submodule add git@git.next-wireless.co:/git/swagger/apis/mde swagger/apis/mde

git submodule add git@git.next-wireless.co:/git/liquibase liquibase

