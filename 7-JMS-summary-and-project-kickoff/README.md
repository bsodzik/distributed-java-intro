## Labs 7 - JMS summary/project kickoff##

### Requirements ###
- Run Linux on UAM workstation
- Use IntelliJ IDEA

### Goals: Get familiar with ###
- JMS summary
- JMS project planning
- Q&A

#### JMS summary ####
- Messaging models
- Spring JMS
- QA

#### JMS project planning ####
System contains 4 separated components
- Warehouse: single instance
- Point of sales (POS): multiple instances
- Reporting: single instance
- "supermarket/prototype" module is:
	- Prototype implementation 
	- Without JMS 
	- All business requirements are implemented

Requirements:
- Divide components from prototype into separated java modules and connect them via JMS
- Warehouse 
	- Contains product list
	- User can change product price only in Warehouse 
	- Warehouse is sending "price change" messages
	- Warehouse is sending list of products as a reply to for "full product list" messages

- Point of sale
	- Contains product list
	- Is receiving "price change" messages
	- Is sending "full product list" messages
	- Is receiving product list from Warehouse (as a response for "full product list" message)
	- Is sending message with sales information to Reporting system
	- Sales is possible only if full product list is available locally

- Reporting
	- Counts all sales facts
	- Is receiving all information about sale from Point of sales

- General requirements
	- Components will communicate with others only via JMS (Queue/Topic)
	- Spring JMS is required
	- POJO and message converters are nice to have. Common module can contains shared code
    - "mvn clean install" on top level is required to get changes from common module
    - Command line UI is ok

Project plan:
- All clarifications need to be done until 27.11.2014
- Implementation need to be done until 04.12.2014
- Send Source code to mateusz.jancy@gmail.com (mail should contain link to github/bitbucket repository or zip file with source code)

##[Feedback](http://goo.gl/forms/DmWOfcJnRV)##
