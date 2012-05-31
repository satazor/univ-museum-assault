start "Logger" "cmd" "/K java -jar -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar logger"
start "Shared Site" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar shared-site"

start "Corridor 1" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar corridor 1"
start "Corridor 2" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar corridor 2"
start "Corridor 3" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar corridor 3"
start "Corridor 4" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar corridor 4"
start "Corridor 5" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar corridor 5"

start "Room 1" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar room 1"
start "Room 2" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar room 2"
start "Room 3" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar room 3"
start "Room 4" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar room 4"
start "Room 5" "cmd" "/K java -Djava.security.policy=./java.policy -jar SD_P3_T2G01.jar room 5"

start "Thiefs" "cmd" "/K java -jar SD_P3_T2G01.jar thief"
start "Chiefs" "cmd" "/K java -jar SD_P3_T2G01.jar chief"
