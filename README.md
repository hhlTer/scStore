# summerCumpStore
version 1
Developed in web format.
The store is simply ArrayList. So the data is available only during the session. To simplify the test, the orders store is automatically filled up with 11 items.

Commands for launch:

git clone https://github.com/hhlTer/scStore

from the /scStore directory:

sudo mvn package

from /target directory:

java -jar store-0.0.1-SNAPSHOT.jar 

application is awailable by localhost:8080/ 

Adding product: menu -> operation -> add order
Clear by year: menu -> operation -> clear
List products: menu -> show -> all
Reports: menu -> reports forming

