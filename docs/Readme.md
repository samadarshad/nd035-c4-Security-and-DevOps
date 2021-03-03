# Splunk

### Generating logs using Postman and uploading to Splunk
There are postman tests for the api, which can be used to generate fake user data.
![postman](res\splunk\postman.PNG)

These cause console log outputs on the running server.
![postman_output](res\splunk\postman_output.PNG)

These logs can now be imported into Splunk.

### Searching Splunk
Searching for "error"
`source="logs.txt" host="ABDUS-SAMAD" sourcetype="Postman tests" error`
![searching for error](res\splunk\search_error.PNG)

Searching for "order submit failed"
`source="logs.txt" host="ABDUS-SAMAD" sourcetype="Postman tests" order submit failed`
![searching for order submit failed](res\splunk\search_order_fail.PNG)

### Setting up alert when order submit failed
Whenever an "order fail" event occurs in the log, an alert will be triggered.
I had re-uploaded the logs to trigger any alerts.
Here we can see the order-fail alert is set up:
![setup order fail alert](res\splunk\alert_order_fail.PNG)

Then clicking into this alert, we can view 2 alerts had occurred at 2 times:
![setup order fail alert list](res\splunk\alert_order_fail_list.PNG)

Selecting one of the items we can see its details, showing the "order fail" logs:
![setup order fail alert list details](res\splunk\alert_order_fail_details_of_first_alert_item.PNG)

### Adding a dashboard for number of successful orders
Setting the search term for successful orders, assembled into time series:
`source="logs.txt" host="ABDUS-SAMAD" sourcetype="Postman tests" order submission successful | timechart count`
![dashboard_searchterm](res\splunk\dashboard_searchterm.PNG)

This can now be visualised in a dashboard on a time chart - the x-axis is time, y-axis is the count of "order successful" events.
![dashboard_output](res\splunk\dashboard_output.PNG)

# Jenkins Pipeline

### Setting up the project
To trigger whenever the github master branch is updated.

![Project setup 1](res\jenkins\project_setup_1.PNG)
![Project setup 2](res\jenkins\project_setup_2.PNG)
![Project setup 3](res\jenkins\project_setup_3.PNG)
![Project setup 4](res\jenkins\project_setup_4.PNG)

### The build output on the console
Console output showing tests pass

![Console output showing tests pass](res\jenkins\console_log_test_results.PNG)

Console output showing the WAR build artifacts have been archived

![Console output showing tests pass](res\jenkins\console_log_build_artifacts.PNG)

### The build output on the dashboard
Test results

![Test results](res\jenkins\test_results.PNG)

WAR Build artifacts automatically built by Jenkins pipeline

![Build artifacts](res\jenkins\build_artifacts.PNG)

