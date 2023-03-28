# Getting Started
to build and compile :

Run > mvn clean install  
Run > mvn spring-boot:run

I have added a sample postman export for calculating Rewards and creating Transaction.
the code is using H2 in memory db for storing data.

create a H2 Data source. the properties are in application.properties file
and load customer ( data.sql)  and transaction data (transaction.sql) 

try connecting with H@ database UI after running the code to make sure the tables are properly generated and populated.
else you will get Table not found exception.


to run the rest call to calculate rewards points 

http://localhost:8080/api/v1/rewards/1001 ( get )

 
PUT - > insert data
http://localhost:8080/api/v1/rewards/transaction

json data : {
"transactionId": "10022",
"customerId":"1003",
"transactionDate": "2023-02-01",
"amount":"49.00"
}



POST - > update data
http://localhost:8080/api/v1/rewards/transaction

json data : {
"transactionId": "10022",
"customerId":"1003",
"transactionDate": "2023-02-01",
"amount":"49.00"
}


Delete - > delete  data
http://localhost:8080/api/v1/rewards/transaction

json data : {
"transactionId": "10022",
"customerId":"1003",
"transactionDate": "2023-02-01",
"amount":"49.00"
}


