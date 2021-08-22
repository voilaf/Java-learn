# 服务实例数
nums=$1

# gateway-server jar包路径
jarPath=/Users/doosp/www/gateway-server-0.0.1-SNAPSHOT.jar

startPort=8000
for i in $(seq 1 $nums)
do
  port=$(expr $startPort + $i)
  nohup java -jar $jarPath --server.port=$port >/dev/null 2>&1 &
  echo '正在启动端口'$port
done
