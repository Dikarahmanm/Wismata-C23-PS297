import requests
import json

# url = "http://127.0.0.1:5000/api/get_rating"
url = "https://getrecommendationrating-3boqvovjka-as.a.run.app/"

response = requests.get(url)
response.raise_for_status()
if response.status_code != 204:
    print(response.json())

# f = open('test.json')

# data = json.load(f)

# category = data['category']

# price = data['price']

# r = requests.get(url, params={"category": category, "price": price})

# print(r.json())