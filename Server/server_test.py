import requests

server = "http://localhost:5000/"
# server = "http://ipv4.dfen.xyz:5000/"
# Test Store
json1 = {'doc': 'oldpig'}
response = requests.post(server + "store", params=json1)
print(response.text)

# Test Get
json2 = {'key': '6c253648e0dfff00d6aa44566f34dae651f0d2191d5bf59dd74d8ce7314cb3e7'}
json_sql_inject = {'key': "' or '1'='1"}
response = requests.get(server+"get", params=json_sql_inject)
print(response.text)

# Test Server Connection
response = requests.get(server)
print(response.text)
