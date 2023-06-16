iterations=$1
for ((i=1; i<=iterations; i++))
do
	curl -X GET "http://localhost:8080/v1/search?query=test1&sort=accuracy&page=1&size=3" &
	curl -X GET "http://localhost:8080/v1/search?query=test2&sort=accuracy&page=1&size=3" &
	curl -X GET "http://localhost:8080/v1/search?query=test3&sort=accuracy&page=1&size=3" &
	sleep 0.01
done
