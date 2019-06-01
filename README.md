# TacoLoco
Web service to calculate order totals.

## Assumptions
Ideally, i would have liked to discuss my understanding of the problem and clear any questions I have.
As this was not possible here, I have made few assumptions which are listed below.

1. Tax is calculated on total amount before discount is deducted at flat rate of 6%
2. Being realsitic, assumed total bill for any particular order on Taco truck would not be beyond max allowed value on Integer


## Design/Architecture considerations
1. As suggested, I am using springboot framework to develop this REST API. The project template is created using start.spring.io
2. Initial thought was to create GET endpoint for orderTotal, however considering the number of parameters required from the user, it made sense to use request body 
   and hence implemented POST endpoint.
3. URI versioning is used
4. The price chart reference could have been done by modifying the ItemName enum. As H2 was easily available and we can modify price without need to change the code 
   if it was in a table,(obviously not in-memory database) price_chart table is created.The codebase uses JPA.
5. Request JSON validations are done using annotations from javax.validation libraries.
4. Unit tests are writted using Junit/Mockito.
5. Integration tests are done using SpringRunner.
6. Gradle is used for dependency management.
7. I use Pivotal Cloud Foundary to host api's. I ran out of trial period and currently I do not have access to any org and hence did not publish this API.


## Quick Start Guide
1. Please clone/download the product from github - https://github.com/badalpurohit/tacoLoco.git.

2. You can now import this as gradle project in your facourite IDE(cheers if it is Intellij) and run as SpringBoot app.
   Once application is started successfully, The API should be now ready to test.
   You can find the POSTMAN collection readily available at "https://documenter.getpostman.com/view/6683735/S1TVXyAU?version=latest"	

3. on command promt go to root of the application you just downlaoded. Please run 'gradlew clean build'. 
   This should run all the tests and create executable JAR named 'order-0.0.1-SNAPSHOT' under build/libs folder.
4. You can publish API using this executable JAR.

## API Documentation
I have published API Collection on postman, you can refer below link to see sample request. Please keep in mind it uses localhost and port 8080. 
You might need to change it in case you change server.port before running the springboot app.
https://documenter.getpostman.com/view/6683735/S1TVXyAU?version=latest

### Sample Request
```
curl --location --request POST "http://localhost:8080/v1/orderTotal" 
  --header "Content-Type: application/json" 
  --data "{
    "orderNumber": 1,
    "orderDetailsList": [
        {
            "itemName": "VeggieTaco",
            "itemQuantity": 5
        },
        {
            "itemName": "ChickenTaco",
            "itemQuantity": 10
        },
        {
            "itemName": "BeefTaco",
            "itemQuantity": 2
        },
        {
            "itemName": "ChorizoTaco",
            "itemQuantity": 1
        }
    ]
}"
```


### Sample Response
```
{
    "orderTotalRequest": {
        "orderNumber": 1,
        "orderDetailsList": [
            {
                "itemName": "VeggieTaco",
                "itemQuantity": 5
            },
            {
                "itemName": "ChickenTaco",
                "itemQuantity": 10
            },
            {
                "itemName": "BeefTaco",
                "itemQuantity": 2
            },
            {
                "itemName": "ChorizoTaco",
                "itemQuantity": 1
            }
        ]
    },
    "subTotal": 52,
    "taxAmount": 3.12,
    "discountAmount": 10.4,
    "orderTotal": 44.72
}
```
