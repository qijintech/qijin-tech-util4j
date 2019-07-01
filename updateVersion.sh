
function find_version()
{
    line=`grep "version" pom.xml | head -n2 |tail -n1 | sed 's/ //g'`
    length=${#line}
    step=$((length-19))
    echo ${line:9:$step}
}

function usage(){
    echo "Usage: sh updateVersion.sh 1.0.1"
    echo "\tWarning! suffix 'SNAPSHOT' is not needed"
    exit 1
}

function check_param(){
    if [ $# -lt 1 ];then
        usage
    fi
    version=$1
    res=`echo $version | grep "^\d\{1,2\}\.\d\{1,2\}\.\d\{1,2\}$"`
    if [ ! -n "$res" ];then
        usage
    fi
}

check_param $@

old_version=`find_version`

version=$1

snapshot_version=$version-SNAPSHOT

echo "old version is: $old_version"
echo "new version is: $snapshot_version"

mvn versions:set -DnewVersion=$snapshot_version

sed -i '' "s/<util4j\.version>$old_version<\/util4j\.version>/<util4j\.version>$snapshot_version<\/util4j\.version>/g" util4j-dependencies/pom.xml

