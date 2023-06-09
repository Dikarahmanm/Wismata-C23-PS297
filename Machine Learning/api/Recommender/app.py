import requests
import json

url = "http://127.0.0.1:5000/"


f = open('test.json')

with open('test.json') as json_file:
    data = json.load(json_file) 


User_Id = data['User_Id']
Place_Id = data['Place_Id']
Place_Ratings = data['Place_Ratings']



r = requests.get(url, params={"User_Id" : User_Id, "Place_Id" : Place_Id, "Place_Ratings" : Place_Ratings})

print(r.json)

