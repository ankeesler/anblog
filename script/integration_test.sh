#!/bin/bash

SCHEME="http"
HOST="localhost:8080"

cd "$(dirname "${BASH_SOURCE[0]}")/.."

./gradlew bootRun 1>/tmp/out 2>/tmp/err &
echo "note: started server"

tries=0
max_tries=10
while true; do
    status="$(curl 2>/dev/null "$SCHEME://$HOST/actuator/health" | jq -r .status)"
    if [[ "$status" == "UP" ]]; then
        break
    fi

    tries=$((tries + 1))
    if [[ $tries -eq $max_tries ]]; then
        echo "error: server failed to come up"
        kill %1 # just in case?
        exit 1
    fi
    echo "note: waiting for server to come up ($tries/$max_tries)"
    sleep 5
done
echo "note: server is up"

echo "note: running tests"
python3 integration/integration.py \
        --directory "$PWD/client" --scheme "$SCHEME" --host "$HOST"
code=$?

kill %1

exit $code
