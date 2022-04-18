# EECS4413-Backend
Course Project for EECS4413

## Run Locally
1. Navigate to the Java project `shopcart-backend`
2. Build the docker image `docker build -t .`
3. Create a `.env` file with the following values to connect the backend service to the database
```env
PSCALE_DB_NAME=<MySQL_DB_NAME>
PSCALE_USERNAME=<MySQL_DB_USERNAME>
PSCALE_DB_URL=<MySQL_DB_CONNECTION_URL>
PSCALE_PASSWORD=<MySQL_DB_PASSWORD>
```
4. Run the previously built docker image `docker run -p 8080:8080 --env-file .env shopcart-backend`
5. The backend is successfully deployed
