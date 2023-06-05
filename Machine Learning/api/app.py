import requests

url = "http://127.0.0.1:5000/api/get_rating"

r = requests.get(url, params={"category": 1, "price": 4})

print(r.json())