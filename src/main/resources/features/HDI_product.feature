Feature: HDI_Product

@Scenario1
Scenario: HDI product request E2E
Given Login detail of application
And 	select HDI product request
And 	create a Hdi product request
And 	Submit the Request
Then 	validate Oracle tables
Then 	validate the request in DB2 tables
Then 	Cassandra services for Retail and Cost
