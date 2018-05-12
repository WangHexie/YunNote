# coding=UTF-8
import threading
import requests
import time
import traceback

cot = 0
fault = 0


def write(text):
    with open("list","w") as f:
        f.write(text)
def read():
    with open("list", "r") as f:
        text = f.read()
    return text

realList = read()

def attack(number,server):
    inner_count = 0
    while True:
        try:
            inner_count += 1
            global cot
            cot = cot+1
            json1 = {'cookies': 'e8a67a8ff8f7ab64e5b683f933f3caf6647a110c98f0a417aab0fb75f3fa19b5'}
            response = requests.post(server + "list", data=json1,timeout = 30)
            global realList
            if response.text!=realList:
                global fault
                fault = fault + 1
            if inner_count == number:
                return

        except:
            fault = fault + 1
            print(traceback.format_exc())



if __name__ == "__main__":
    url = "http://ipv6.dfen.xyz:5000/"
    x = int(input("Input the amount of threads: "))
    y = 70

    start_time = time.time()
    pool = []
    for i in range(x):
        pool.append(threading.Thread(target=attack, args=(y, url)))

    print("Start test....")
    for i in range(x):
        pool[i].start()

    for i in range(x):
        pool[i].join()

    finish_time = time.time()
    print("Total request:",cot)
    print("Total fault:",fault)
    print("Fault percent:",fault/cot)
    print("Duration:",finish_time-start_time,"s")