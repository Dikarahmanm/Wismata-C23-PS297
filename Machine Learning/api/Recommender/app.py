import requests
import json

url = "http://127.0.0.1:5000/"  # Replace with the appropriate URL of your Flask app


# Prepare the data payload

f = open('test.json')

data = json.load(f)

User_Id = data['User_Id']

Place_Id = data['Place_Id']

Place_Ratings = data['Place_Ratings']


# Send a POST request to the Flask app
response = requests.get(url, params={"User_Id" : User_Id,
                                     "Place_Id" : Place_Id ,
                                     "Place_Ratings" : Place_Ratings 

                                    })


print(response.json())

# Check the response status
if response.status_code == 200:
    # Print the response data
    response_data = response.json()
    print(response_data)
else:
    print("Request failed with status:", response.status_code)