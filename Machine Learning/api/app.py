import requests
import json

url = "http://127.0.0.1:5000/api/get_rating"


f = open('test.json')

data = json.load(f)

category = data['category']

price = data['price']

r = requests.get(url, params={"category": category, "price": price})

print(r.json())