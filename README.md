# Accounts rest api


## View all accounts:
HTTP METHOD: GET

URL: http://localhost:8080/account-project/rest/account/json

Returns 200 when successful

## Add an account:
HTTP METHOD: POST

Content-Type: application/json

URL: http://localhost:8080/account-project/rest/account/json

Sample request body:

{
	"firstName":"John",
	"lastName":"Doe",
	"accountNumber":"11233"
}

Returns:

200 when successful

400 for invalid requests

## Delete an account:

HTTP METHOD: DELETE

URL: http://localhost:8080/account-project/rest/account/json/{ID}

Returns:

200 when successful

404 when ID not found
