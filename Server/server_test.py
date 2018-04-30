import requests

# Test Store
json1 = {'doc': 'oldpig'}
response = requests.post("http://ipv4.dfen.xyz:5000/store", params=json1)
print(response.text)

# Test Get
json2 = {'key': '6c253648e0dfff00d6aa44566f34dae651f0d2191d5bf59dd74d8ce7314cb3e7'}
response = requests.get("http://ipv4.dfen.xyz:5000/get", params=json2)
print(response.text)

# Test Server Connection
response = requests.get("http://ipv4.dfen.xyz:5000/")
print(response.text)
