iterations=$1
for ((i=1; i<=iterations; i++))
do
	curl -X GET "http://localhost:8080/v1/search?query=test&sort=accuracy&page=1&size=3" &
done
