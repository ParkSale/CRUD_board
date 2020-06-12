ABSDIR=$(dirname $ABSPATH)

source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

fuser -k -n tcp $IDLE_PORT
