# Rent service

## Description
A service is responsible for managing user cars, rent companies and rentals information. Company managers display information about their cars, including details such as type, model, brand, and availability. Clients search for cars based on their preferences, such as location, type, model, brand of vehicle and then make a reservation during an available time period. Authorization is done using Spring Security and user roles(client, manager). Communication with the user service is done synchronously, to obtain information needed for making rentals(ranks and discounts). Data is stored in relational database(MySQL). 

## Structure
This service is part of a Rent-a-Car application that is structured as a set of microservices:
* [User service](https://github.com/lukamilo99/user-service) <br/>
* [Rent service](https://github.com/lukamilo99/rent-service) <br/>
* [Notification service](https://github.com/lukamilo99/notification-service) <br/>
* [Api gateway service](https://github.com/lukamilo99/api-gateway-service) <br/>
* [Registry service](https://github.com/lukamilo99/registry-service) <br/>

Each microservice is responsible for a specific task, and they work together to provide the functionality.
