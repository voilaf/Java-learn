jps -l | grep gateway-server-0.0.1-SNAPSHOT.jar | awk '{print $1}' | xargs kill -9
