## indieHoodDemo

The app have two activities 
1. Main Activity
2. Detail Activity


### Main Activity
Main activity fetches the data and schema from the server url and lists the top level data for example `loan 1`. 
On clicking the item it generates the dynamic UI and shows different layouts according the schema.

### Details Activity
Generates the dynamic UI based on Schema.

### Supports
Api Level > 24

### To suport another schema
In order to support other schemas, please change `DATA_URL` and `SCHEMA_URL` in `Main Activity`
