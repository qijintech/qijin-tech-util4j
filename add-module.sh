#!/bin/bash

LIGHT_GRAY='\033[0;37m'
PLAIN='\033[0m'
BOLD='\033[1m'
RED='\033[0;31m'
DARK_GRAY='\033[1;30m'
YELLOW='\033[1;33m'


DEFAULT_MODULE="demo"
DEFAULT_VERSION=`grep "<version>" pom.xml|head -n1| awk -F'>|<' '{print $3}'`

printf "请输入module名${DARK_GRAY}(${DEFAULT_MODULE})${PLAIN}:"

read module_name
if [ "${module_name}" == "" ];then
    module_name="demo"
fi

printf "请输入version名字${DARK_GRAY}(${DEFAULT_VERSION})${PLAIN}:"
read version
if [ "${version}" == "" ];then
    version="${DEFAULT_VERSION}"
fi

packe_name=`echo ${module_name} | sed "s/-/./g"`

mvn archetype:generate -DarchetypeGroupId=tech.qijin.archetype -DarchetypeArtifactId=util4j-module -DarchetypeVersion=1.0.0-SNAPSHOT -DgroupId=tech.qijin.util4j.${module_name}  -DartifactId=${module_name}  -Dversion=${version}  -DpackageName=qijin.tech.util4j.${packe_name} -DinteractiveMode=false -DarchetypeCatalog=internal

real_module_name="util4j-${module_name}"

mv ${module_name} ${real_module_name}

sed -i "" "s/<module>${module_name}<\/module>/<module>${real_module_name}<\/module>/g" pom.xml

COMMONS_PATH="${real_module_name}/src/main/java/tech/qijin/util4j"
MODULE_PATH="${COMMONS_PATH}/${module_name}"
MODULE_CONFIG_PATH="$MODULE_PATH/config"

module_name_camel=`echo "${module_name}" | perl -pe 's/(^|-)./uc($&)/ge;s/-//g'`
echo ${module_name_camel}
autoconfig="${module_name_camel}AutoConfiguration"
properties="${module_name_camel}Properties"
propertiesconfig="${module_name_camel}PropertiesConfiguration"
sed -i "" "s/ModuleAutoConfiguration/${autoconfig}/g" "${MODULE_CONFIG_PATH}/ModuleAutoConfiguration.java"
sed -i "" "s/ModuleProperties/${properties}/g" "${MODULE_CONFIG_PATH}/ModuleAutoConfiguration.java"
sed -i "" "s/ModulePropertiesConfiguration/${propertiesconfig}/g" "${MODULE_CONFIG_PATH}/ModuleAutoConfiguration.java"
sed -i "" "s/ModuleProperties/${properties}/g" "${MODULE_CONFIG_PATH}/ModuleProperties.java"
sed -i "" "s/ModulePropertiesConfiguration/${propertiesconfig}/g" "${MODULE_CONFIG_PATH}/ModulePropertiesConfiguration.java"
sed -i "" "s/ModuleAutoConfiguration/${autoconfig}/g" "${real_module_name}/src/main/resources/META-INF/spring.factories"
sed -i "" "s/${module_name}/${packe_name}/g" "${MODULE_CONFIG_PATH}/ModuleProperties.java"
sed -i "" "s/tech.qijin.util4j.${module_name}/tech.qijin.util4j/g" ${real_module_name}/pom.xml
mv "${MODULE_CONFIG_PATH}/ModuleAutoConfiguration.java" "${MODULE_CONFIG_PATH}/${autoconfig}.java"
mv "${MODULE_CONFIG_PATH}/ModuleProperties.java" "${MODULE_CONFIG_PATH}/${properties}.java"
mv "${MODULE_CONFIG_PATH}/ModulePropertiesConfiguration.java" "${MODULE_CONFIG_PATH}/${propertiesconfig}.java"

python add-dependency.py ${module_name}

mv util4j-dependencies/pom_bak.xml util4j-dependencies/pom.xml

#if [ ! -d "${COMMONS_PATH}/${packe_name}" ];then
#    echo "N"
#    echo "${COMMONS_PATH}/${packe_name}" 
#fi

