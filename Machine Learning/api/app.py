import requests
import json

url = "http://127.0.0.1:8080/api/recommendation"
#url = "https://recommendationrating-3boqvovjka-et.a.run.app"
#url = "https://recommendationrating-3boqvovjka-et.a.run.app/api/recommendation"




r = requests.get(url, params={'IdUser_Login': 1})

print(r.json())