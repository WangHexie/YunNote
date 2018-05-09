import requests

case = 0


def server_test(server, case):
    proxies = {
        "http": "http://127.0.0.1:8888",
    }
    if case == 1:
        json1 = {'doc': 'oldpig'}
        response = requests.post(server + "store", data=json1)
        print(response.text)

    if case == 2:
        # Test Get
        print("inject test")
        json2 = {'key': '6c253648e0dfff00d6aa44566f34dae651f0d2191d5bf59dd74d8ce7314cb3e7'}
        json_sql_inject = {'key': "' or '1'='1"}
        response = requests.get(server + "get", params=json_sql_inject)
        print(response.text)

    if case == 0:
        # Test Server Connection
        response = requests.get(server)
        print(response.text)

    if case == 3:
        json1 = {'username': '123', 'password': "888"}
        response = requests.post(server + "login", data=json1)
        print("cookies", response.text)

    if case == 4:
        json1 = {'username': '123', 'password': "8888"}
        response = requests.post(server + "login", data=json1)
        print("fail login test", response.text)

    if case == 5:
        json1 = {'cookies': '002e7e436441939758c41ed393257d2cea503d0dfb234201af1bfc21bfbc6d55'}
        response = requests.post(server + "list", data=json1)
        import json
        x = json.loads(response.text)
        print(x)

    if case == 6:
        longSentence = """
        0
        
        0
        """
        json1 = {'doc': longSentence}
        response = requests.post(server + "storeck", data=json1)
        print("cnk", response.text)

    if case == 7:
        json1 = {'cnkey': '才白以两名'}
        response = requests.get(server + "getbyck", params=json1)
        print("doc", response.text)


if __name__ == "__main__":
    # url = "http://localhost:5000/"
    # url = "http://127.0.0.1:5000/"
    url = "http://ipv4.dfen.xyz:5000/"
    server_test(url, 7)
    # for i in range(6):
    #     server_test(url, i)
