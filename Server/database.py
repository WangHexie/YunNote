import threading
import time
import traceback

import pymysql

from Server import basic_function

lenth_of_username = 15
lenth_of_password = 70
lenth_of_cookies = 70
lenth_of_uid = 15
lenth_of_doc = 65536
lenth_of_key = 70
expire_time = 60 * 60 * 24 * 7


def get_doc_from_database(key):
    with  pymysql.connect(host="45.76.223.233", user="root",
                          password="root", db="MobileAppDB", port=3306).cursor() as cursor:
        try:
            sql = "SELECT doc FROM key_doc where key_hash=%s"
            cursor.execute(sql, [key])
            fet = cursor.fetchone()
            if fet == None:
                result = 0
            else:
                result = fet[0]
        except:
            print(traceback.format_exc())

    return result


def store_doc_to_database(doc):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)
    try:
        key = basic_function.create_key()
        with connection.cursor() as cursor:
            sql = "insert into key_doc(key_hash, doc, MODIFY_TIME)  value(%s, %s, %s);"
            cursor.execute(sql, [key, doc, float(basic_function.time_now())])
            connection.commit()
    finally:
        connection.close()
    return key


def part_key_to_full_key(part_key):
    return 0


def store_full_key_and_part_key(full_key, part_key):
    return 0


def get_list_doc(list_keys):
    list_doc = []
    list_key = []
    lock = threading.Lock()

    def append_list(list_doc, list_keys, key):
        doc = get_doc_from_database(key)
        lock.acquire()
        list_doc.append(doc)
        list_keys.append(key)
        lock.release()

    with  pymysql.connect(host="45.76.223.233", user="root",
                          password="root", db="MobileAppDB", port=3306).cursor() as cursor:
        try:
            thread_list = []
            for i in list_keys:
                thread_list.append(threading.Thread(target=append_list,
                                                    kwargs={"key": i, "list_doc": list_doc, "list_keys": list_key}))
            for i in thread_list:
                i.start()
            for i in thread_list:
                i.join()
            return list_doc, list_keys
        except:
            print(traceback.format_exc())
            return list_doc, list_keys


def add_cookies_live_time(cookies):
    def find_and_add_time(cookies):
        if len(cookies) <= lenth_of_cookies:
            with  pymysql.connect(host="45.76.223.233", user="root",
                                  password="root", db="MobileAppDB", port=3306).cursor() as cursor:
                try:
                    sql = "UPDATE COOKIES_LIST SET TIME=%s where COOKIES = %s;"
                    cursor.execute(sql, [str(int(basic_function.time_now()) + expire_time), cookies])
                    cursor.connection.commit()
                    return 1
                except:
                    print(traceback.format_exc())
                    return 0
        else:
            return 0

    th = threading.Thread(target=find_and_add_time, kwargs={"cookies": cookies}, name='add_time')
    th.start()
    return 0


def add_uid_and_cookies(uid, cookies):
    with  pymysql.connect(host="45.76.223.233", user="root",
                          password="root", db="MobileAppDB", port=3306).cursor() as cursor:
        try:
            sql = "INSERT INTO COOKIES_LIST (USER_NAME, COOKIES, TIME) VALUES (%s, %s, %s);"
            cursor.execute(sql, [uid, cookies, str(int(basic_function.time_now()) + expire_time)])
            cursor.connection.commit()
        except:
            print(traceback.format_exc())
            return 0
        return 1


def check_user_exist(username):
    if len(username) <= lenth_of_username:
        with  pymysql.connect(host="45.76.223.233", user="root",
                              password="root", db="MobileAppDB", port=3306).cursor() as cursor:
            try:
                sql = "select * from NOTE_ACCOUNT where USER_NAME = %s;"
                cursor.execute(sql, [username])
                if cursor.fetchone() != None:
                    return 1
                else:
                    return 0
            except:
                print(traceback.format_exc())
                return 1
    else:
        return 1


def add_user(username, password):
    if len(username) <= lenth_of_username and len(password) <= lenth_of_password and not check_user_exist(username):

        try:
            with  pymysql.connect(host="45.76.223.233", user="root",
                                  password="root", db="MobileAppDB", port=3306).cursor() as cursor:
                insert = "INSERT INTO NOTE_ACCOUNT (USER_NAME, USER_PASS) VALUES (%s, %s);"
                ps_hash = basic_function.real_password(password)
                cursor.execute(insert, (username, ps_hash))
                cursor.connection.commit()
            cookies = basic_function.create_cookies()
            if add_uid_and_cookies(username, cookies):
                return cookies
            return 0
        except:
            print(traceback.format_exc())
            return 0
    else:
        return 0


def check_user_password(username, password):
    if len(username) <= lenth_of_username and len(password) <= lenth_of_password:
        with  pymysql.connect(host="45.76.223.233", user="root",
                              password="root", db="MobileAppDB", port=3306).cursor() as cursor:
            try:
                # check user and password
                sql = "select * from NOTE_ACCOUNT where USER_NAME = %s AND USER_PASS = %s;"
                # hash password into store form
                cursor.execute(sql, [username, basic_function.real_password(password)])

                if cursor.fetchone() != None:
                    # if password is right,then create cookies and save cookies
                    cookies = basic_function.create_cookies()
                    if add_uid_and_cookies(username, cookies):
                        return cookies
                    else:
                        return 0
                else:
                    return 0
            except:
                print(traceback.format_exc())
                return 0
    else:
        return 0


def get_user_by_cookies(cookies):
    if len(cookies) <= lenth_of_cookies:
        with  pymysql.connect(host="45.76.223.233", user="root",
                              password="root", db="MobileAppDB", port=3306).cursor() as cursor:
            try:
                sql = "select USER_NAME from COOKIES_LIST where COOKIES = %s;"
                cursor.execute(sql, [cookies])
                user_id = cursor.fetchone()
                if user_id != None:
                    return user_id
                else:
                    return None
            except:
                print(traceback.format_exc())
                return None
    else:
        return None


def get_user_list(user_id):
    key_list = []
    if len(user_id) <= lenth_of_uid:
        with  pymysql.connect(host="45.76.223.233", user="root",
                              password="root", db="MobileAppDB", port=3306).cursor() as cursor:
            try:
                sql = "select NOTE_KEY from NOTE_LIST where USER_NAME = %s;"
                cursor.execute(sql, [user_id])
                result = cursor.fetchall()
                if result != None:
                    for i in result:
                        key_list.append(i[0])
                    return key_list
                else:
                    return 0
            except:
                print(traceback.format_exc())
                return 0
    else:
        return 0


def add_into_list(user_id, doc):
    if len(user_id) <= lenth_of_uid and len(doc) < lenth_of_doc:
        with  pymysql.connect(host="45.76.223.233", user="root",
                              password="root", db="MobileAppDB", port=3306).cursor() as cursor:
            try:
                key = store_doc_to_database(doc)

                sql = "INSERT INTO NOTE_LIST (USER_NAME, NOTE_KEY) VALUES (%s, %s);"
                cursor.execute(sql, [user_id, key])
                cursor.connection.commit()
                return key
            except:
                print(traceback.format_exc())
                return 0
    else:
        return 0


def delete_form_list(user_id, key):
    if len(user_id) <= lenth_of_uid and len(key) <= lenth_of_key:
        with  pymysql.connect(host="45.76.223.233", user="root",
                              password="root", db="MobileAppDB", port=3306).cursor() as cursor:
            try:
                sql = "DELETE FROM NOTE_LIST WHERE USER_NAME = %s AND NOTE_KEY = %s;"
                cursor.execute(sql, [user_id, key])
                cursor.connection.commit()
                delete_from_key_doc = "DELETE FROM key_doc WHERE key_hash = %s"
                cursor.execute(delete_from_key_doc, [key])
                cursor.connection.commit()
                return 1
            except:
                print(traceback.format_exc())
                return 0
    else:
        return 0


def delete_one_cookies(cookies):
    with  pymysql.connect(host="45.76.223.233", user="root",
                          password="root", db="MobileAppDB", port=3306).cursor() as cursor:
        try:
            sql = "DELETE FROM COOKIES_LIST WHERE COOKIES = %s;"
            cursor.execute(sql, [cookies])
            cursor.connection.commit()
            return 1
        except:
            print(traceback.format_exc())
            return 0


def delete_useless_cookies():
    def delete_timeout_cookies():
        with  pymysql.connect(host="45.76.223.233", user="root",
                              password="root", db="MobileAppDB", port=3306).cursor() as cursor:
            try:
                sql = "SELECT COOKIES,TIME FROM COOKIES_LIST ;"
                cursor.execute(sql)
                all_cookies = cursor.fetchall()
                time_now = basic_function.time_now()
                for one_cookies in all_cookies:
                    if one_cookies[1] != "":
                        if int(one_cookies[1]) < int(time_now):
                            print("deleting", one_cookies[0])
                            y = threading.Thread(target=delete_one_cookies, kwargs={"cookies": one_cookies[0]})
                            y.start()
                    else:
                        print("deleting", one_cookies[0])
                        y = threading.Thread(target=delete_one_cookies, kwargs={"cookies": one_cookies[0]})
                        y.start()
            except:
                return 0

    while True:
        threading.Thread(target=delete_timeout_cookies).start()
        time.sleep(1200)


if __name__ == '__main__':
    delete_useless_cookies()
