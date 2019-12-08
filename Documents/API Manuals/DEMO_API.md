# DEMO API
This Document contains the descriptions of all endpoints present in the DEMO project

## Swagger Documentation
To access the swagger api documentation for this service head to
```
http:<host_url>:8080/
```
Where the `<host_url>` is the url of the server that is hosting your demo service

Once there you should see an image 
![demo_swagger](https://raw.githubusercontent.com/CMU-Capstone/TBC-Final-Deliverable/master/Documents/images/demo_swagger.PNG)

This would indicate that your service is up and running.

## Service Endpoints
The base URL for all service endpoints is:
```
http:<host_url>:8080/demo_controller/api/
```

The endpoints have 2 main categories:
1. Combine Services
    1. `<base>/getQuestionsByProblems`
    1. `<base>/getTechByProblems`
1. Dashboard Services
    1. `<base>/getAllHackathons`
    1. `<base>/getTop5Industry`
    1. `<base>/getTop5Technology`
    1. `<base>/topFiveProblems`
    1. `<base>/getTotalNumberOfProjects`
    1. `<base>/getUniqueTechCount`
    1. `<base>/getUniqueUserCount`

# Combined Services

## getQuestionsByProblems

#### Description:
This service will combine the slack and git scanners search results base on email and get the combined search results within the given search <b>industry</b> and <b>problem</b> type.

#### URL:
```
<base>/getQuestionsByProblems
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Input
Parameters:
1. `industry` - The industry that you are interested in
2. `problem` - The problem that you are interested in

Sample Query: `<base>/getQuestionsByProblems?industry=<industry>&problem=<problem>`

#### Output
Responds Body (sample):
```
{
    "problem": "Provenance",
    "Questions": {
        "abc@gmail.com": [],
        "xyz@hotmail.com": []
    }
}
```


## getTechByProblems

#### Description:
This service will count all the different technology used within the given search <b>industry</b> and <b>problem</b> type.

#### URL:
```
<base>/getTechByProblems
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Input
Parameters:
1. `industry` - The industry that you are interested in
2. `problem` - The problem that you are interested in

Sample Query: `<base>/getTechByProblems?industry=<industry>&problem=<problem>`

#### Output
Responds Body (sample):
```
{
    "problem": "Provenance",
    "Progressive Web App": 6,
    "Algorand SDK": 12
}
```

# Dashboard Services
## getAllHackathons
#### Description:
This service get all hackathons within the mongodb.

#### URL:
```
<base>/getAllHackathons
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Inputs
No input requried

#### Output
Responds Body (sample):
```
[
    "BUIDL",
    "BUIDL Online",
    "DEMO",
    "Quipu"
]
```

## getTop5Industry
#### Description:
This service get all the top 5 industries with the most projects within TBC's hackathons.
#### URL:
```
<base>/getTop5Industry
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Inputs
No input requried

#### Output
Responds Body (sample):
```
{
    "Health Care": 1,
    "Finance": 1,
    "IT": 1,
    "Coffee": 1
}
```

## getTop5Technology
#### Description:
This service get all the top 5 technology being used within TBC's hackathons.
#### URL:
```
<base>/getTop5Technology
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Inputs
No input requried

#### Output
Responds Body (sample):
```
{
    "Progressive Web App": 1,
    "Algorand SDK": 1,
    "Liquidity": 1,
    "Tezos Smart contract": 1
}
```

## topFiveProblems
#### Description:
This service get all the top 5 problems being solved within TBC's hackathons.
#### URL:
```
<base>/topFiveProblems
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Inputs
No input requried

#### Output
Responds Body (sample):
```
{
    "Provenance": 1,
    "Pament": 1,
    "Small Business": 1,
    "Supply Chain": 1
}
```

## getTotalNumberOfProjects
#### Description:
This service gets the total number of projects.
#### URL:
```
<base>/getTotalNumberOfProjects
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Inputs
No input requried

#### Output
Responds Body (sample):
```
44
```

## getUniqueTechCount
#### Description:
This service gets the total number of unique technology used.
#### URL:
```
<base>/getUniqueTechCount
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Inputs
No input requried

#### Output
Responds Body (sample):
```
4
```

## getUniqueUserCount
#### Description:
This service gets the total number of unique unsers (identified by email in both slack and git scanners).
#### URL:
```
<base>/getUniqueUserCount
```
#### Method
GET

#### Headers
```
Origin: *
Content-Type: application/json
```

#### Inputs
No input requried

#### Output
Responds Body (sample):
```
7
```

