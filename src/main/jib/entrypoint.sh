#!/bin/bash
if [ "$DB_VENDOR" = "oracle" ] ; then
    pushd /app/entando-common/oracle-driver-installer
    ./install.sh || exit 1
    popd
fi
echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}
exec java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "org.entando.plugin.avatar.AvatarPluginApp"  "$@"
