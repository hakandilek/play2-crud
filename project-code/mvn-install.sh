#!/bin/bash

./publish-local.sh

ARTIFACT=play2-crud;
VERSION=0.7.5-SNAPSHOT;
LOCAL_REPO=/Users/ludochane/workspace/maven-repo;

mvn install:install-file -DgroupId=${ARTIFACT} -DartifactId=${ARTIFACT}_2.11 -Dversion=$VERSION -Dpackaging=jar -Dfile=/Users/ludochane/.ivy2/local/${ARTIFACT}/${ARTIFACT}_2.11/$VERSION/jars/${ARTIFACT}_2.11.jar -DlocalRepositoryPath=$LOCAL_REPO;

mvn install:install-file -DgroupId=${ARTIFACT} -DartifactId=${ARTIFACT}_2.11 -Dversion=$VERSION -Dpackaging=jar -Dclassifier=assets -Dfile=/Users/ludochane/.ivy2/local/${ARTIFACT}/${ARTIFACT}_2.11/$VERSION/jars/${ARTIFACT}_2.11-assets.jar -DlocalRepositoryPath=$LOCAL_REPO;

mvn install:install-file -DgroupId=${ARTIFACT} -DartifactId=${ARTIFACT}_2.11 -Dversion=$VERSION -Dpackaging=jar -Dclassifier=sources -Dfile=/Users/ludochane/.ivy2/local/${ARTIFACT}/${ARTIFACT}_2.11/$VERSION/srcs/${ARTIFACT}_2.11-sources.jar -DlocalRepositoryPath=$LOCAL_REPO;