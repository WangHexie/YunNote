import requests

case = 0

def server_test(server,case):
    proxies = {
        "http": "http://127.0.0.1:8888",
    }
    if case == 1:
        json1 = {'doc': 'oldpig'}
        response = requests.post(server + "store", data=json1, proxies=proxies)
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

    if case == 5:
        json1 = {'cookies': 'b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c'}
        response = requests.post(server + "list", data=json1)
        print(response.text)
        import json
        x = json.loads(response.text)
        print(x)
    if case == 6:
        json1 = {'key':"b0d32ad6498a4ce5b7ab35b6b9e737378acae25be78ce3102a38cfa7a5b54425","cookies":"b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c"}
        response = requests.post(server + "delete", data=json1)
        print(response.text)
    if case == 7:
        json1 = {'doc': "美滋滋",
                 "cookies": "b4373a57ae6b094ab2e9837fe2a79f1f247dd2bfb04083f6aba15a0d90b2cf4c"}
        response = requests.post(server + "storelist", data=json1, proxies=proxies)
        print(response.text)

if __name__ == "__main__":

    # url = "http://127.0.0.1:5000/"
    url = "http://ipv6.dfen.xyz:5000/"
    server_test(url, 7)
    # for i in range(6):
    #     server_test(url,i)
