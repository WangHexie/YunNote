import time
import random
import hashlib

def create_key():
    hash_string = str(time.time()) + str(random.randrange(99999999))
    final_key = hashlib.sha256(hash_string.encode('utf-8')).hexdigest()
    return final_key

if __name__ == '__main__' :
    print(create_key())