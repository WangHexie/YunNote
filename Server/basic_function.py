import hashlib
import random
import time

dic = {}
reverse_dic = {}


def create_key():
    hash_string = str(time.time()) + str(random.randrange(99999999))
    final_key = hashlib.sha256(hash_string.encode('utf-8')).hexdigest()
    return final_key


def set_dic():
    with open("dic.txt", "r") as f:
        dic_str = f.read()
    global dic
    dic = eval(dic_str)


def get_dic():
    global dic
    if dic == {}:
        set_dic()
    return dic


def set_reverse_dic():
    dic = get_dic()
    global reverse_dic
    reverse_dic = dict(zip(dic.values(), dic.keys()))


def get_reverse_dic():
    global reverse_dic
    if reverse_dic == {}:
        set_reverse_dic()
    return reverse_dic


def get_two_hex(character):
    reverse_dic = get_reverse_dic()
    return reverse_dic[character]


def get_single_character(two_hex):  # need to rewrite
    dic = get_dic()
    return dic[two_hex]


def hash_to_chinese_key(hash, lenth=5):
    chinese_key = ""
    for i in range(lenth):
        chinese_key += get_single_character(hash[2 * i:2 * i + 2])
    return chinese_key


def chinese_key_to_hash(chinese_key):
    hash = ""
    for i in chinese_key:
        hash += get_two_hex(i)
    return hash

def create_cookies():
    hash_string =  str(random.randrange(99999999)) + str(time.time())
    final_cookies = hashlib.sha256(hash_string.encode('utf-8')).hexdigest()
    return final_cookies

def real_password(password):
    hash_string = password + "db_salt"
    for i in range(200):
        hash_string = hashlib.sha256(hash_string.encode('utf-8')).hexdigest()
    return hash_string

if __name__ == '__main__':
    hash = "8bfd6fb7e44396db8033cd6715a25432f1e370a6a9d7a2b6674024d3696baf5c"
    word = hash_to_chinese_key(hash)
    hash2 = chinese_key_to_hash(word)
    print(hash)
    print(hash2)
    print(word)

    print(real_password("16513"))
