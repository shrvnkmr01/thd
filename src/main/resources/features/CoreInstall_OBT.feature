Feature: CoreInstall

@Scenario1
Scenario: Core Install with Retail Trigger Validations_BISTIO
Given Login detail of application
And 	select core install request
And 	create a core install request
And   update excel with retail in cost-retail sheet and upload
And 	Submit the Request
And 	Validate Retail triggers in OBT SUSP table_BISTIO
And 	Validate DB retail tables


@Scenario2
Scenario: Core Install with cost Trigger Validations_BISTIOC
Given Login detail of application
And 	select core install request
And 	create a core install request
And   update excel with cost in cost-retail sheet and upload
And 	Submit the Request
And 	Validate cost triggers in OBT SUSP table_BISTIOC
And 	Validate DB cost tables


@Scenario3
Scenario: Core Install with retail Trigger Validations_BISTSKU
Given Login detail of application
And 	select core install request
And 	create a core install request
And   update excel with retail in sku-hdr sheet and upload
And 	Submit the Request
And 	Validate Retail triggers in OBT SUSP table_BISTSKU
And 	Validate DB retail tables

@Scenario4
Scenario: Core Install with cost Trigger Validations_BISTVSKU
Given Login detail of application
And 	select core install request
And 	create a core install request
And   update excel with cost in sku-hdr sheet and upload
And 	Submit the Request
And 	Validate Cost triggers in OBT SUSP table_BISTVSKU
And 	Validate DB cost tables


@Scenario5
Scenario: Core Install with cost Trigger Validations_BISTIBCL
Given Login detail of application
And 	select core install request
And 	create a core install request
And   update excel with base option cost in sku-hdr sheet and upload
And 	Submit the Request
And 	Validate Cost triggers in OBT SUSP table_BISTIBCL
And 	Validate DB cost tables

@Scenario6
Scenario: Core Install with retail Trigger Validations_BISTIBPL
Given Login detail of application
And 	select core install request
And 	create a core install request
And   update excel with base option retail in sku-hdr sheet and upload
And 	Submit the Request
And 	Validate Retail triggers in OBT SUSP table_BISTIBPL
And 	Validate DB retail tables



