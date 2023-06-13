import requests
import json

#url = "http://127.0.0.1:8080/api/recommendation" --> Must be run on local machine
#url = "https://wismata-recommendation-api-3boqvovjka-uc.a.run.app/api/predict_rating" #this is for predict rating
url = "https://wismata-recommendation-api-3boqvovjka-uc.a.run.app/api/recommendation" #this is for recommendation destination




r = requests.get(url, 

#params={'IdUser_Login': 1} # this is for recommendation destination
#params={"Category": 3, "Price": 2} # this is for predict rating


)

print(r.json())