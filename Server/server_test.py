import requests

case = 0

def server_test(server,case):
    proxies = {
        "http": "http://127.0.0.1:8888",
    }
    if case == 1:
        json1 = {'doc': 'oldpig'}
        response = requests.post(server + "store", datas=json1, proxies=proxies)
        print(response.text)

    if case == 2:
        # Test Get
        json2 = {'key': '6c253648e0dfff00d6aa44566f34dae651f0d2191d5bf59dd74d8ce7314cb3e7'}
        json_sql_inject = {'key': "' or '1'='1"}
        response = requests.get(server + "get", params=json_sql_inject)
        print(response.text)

    if case == 0:
        # Test Server Connection
        response = requests.get(server)
        print(response.text)

    if case == 3:
        json1 = {'username': '123','password':"888"}
        response = requests.post(server + "login", data=json1)
        print("cookies",response.text)

    if case == 4:
        json1 = {'username': '123','password':"8888"}
        response = requests.post(server + "login", data=json1)
        print("fail login test",response.text)

if __name__ == "__main__":

    url = "http://127.0.0.1:5000/"
    server_test(url, 4)
