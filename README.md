# Accounts app
Start with ./gradlew bootRun and open localhost:8080/accounts. 
Short API description see below

Data is stored in an H2 in memory database. For production 
the database should be exchanged (see application.properties).

Tests exists, however, race conditions with the statistics
may occur. 

If the load for writing events gets heavier. 

For stats I decided to calculate the stats on demand. 
I.E. stats for all events in the database are calculated 
and stats from a stats database table are merged to this result.
This makes reading stats slower but writing events is quick. 

As an alternative one could update the stats table whenever an event
occurs. Database synchronization when writing events
whould then be needed.



# Endpoints

POST creates a new resource 
PUT updates an existing
GET returns the resource (or a list)

## POST, GET, PUT /accounts
```
{
    id: number
    name: string,
    accountId: number
}
```

## POST, GET /events
```
{
    id: number
    type: string,
    time: timestamp,
    accountId: number
}
```
Events are physically deleted all ${accounts.deleteOldJob.delay} milliseconds.
Statistics for deleted events are being generated and merged to the statistics table.

## GET /accounts/{id}/stats
```
   {
        "type": null,
        "year": 2019,
        "month": 12,
        "day": 13,
        "count": 2
    }
```

Statistics are calculated from /events and a statistics table. 