<h2>Building and Running application</h2>
>mvn clean install
>mvn spring-boot:run


<h3> Calling api from postman </h3>

url :- 
> http://localhost:9090/employees/publish

example body:- 
> {
"employee":{
"empId":10001,
"empName": "Nagaraju",
"clientId": 101,
"companyName": "IBM",
"projectId": 10
},
"client":{
"clientId": 101,
"clientName": "MBRDI",
"projectId": 10
},
"project":{
"projectId":10,
"projectName": "Kafka-SESP",
"department": "ID"
}

}