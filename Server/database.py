import pymysql.cursors
import traceback
from Server import basic_function

lenth_of_username = 15
lenth_of_password = 70
lenth_of_cookies = 70
lenth_of_uid = 15
lenth_of_doc = 65536
lenth_of_key = 70

def get_doc_from_database(key):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)
    try:
        with connection.cursor() as cursor:
            sql = "SELECT doc FROM key_doc where key_hash=%s"
            cursor.execute(sql, [key])
            fet = cursor.fetchone()
            if fet == None:
                result = 'NOT Found'
            else:
                result = fet[0]

    finally:
        connection.close()

    return result


def store_doc_to_database(doc):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)
    try:
        key = basic_function.create_key()
        with connection.cursor() as cursor:
            sql = "insert into key_doc value(%s, %s);"
            cursor.execute(sql, [key, doc])
            connection.commit()
    finally:
        connection.close()
    return key


def part_key_to_full_key(part_key):
    return 0


def store_full_key_and_part_key(full_key, part_key):
    return 0


def login_check(username, password):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)
    try:
        with connection.cursor() as cursor:
            sql = "select password from Account where username = %s;"
            cursor.execute(sql, [username])
            if password == cursor.fetchone()[0]:
                return True
    finally:
        connection.close()

    return False

def add_uid_and_cookies(uid,cookies):
    with pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306) as connection:
        try:
            with connection.cursor() as cursor:
                sql = "INSERT INTO COOKIES_LIST (%S, %S);"
                cursor.execute(sql, [uid, cookies])
        except:
            print(traceback.format_exc())
            return 0
        return 1

def check_user_exist(username):
    if len(username) <= lenth_of_username :
        with pymysql.connect(host="45.76.223.233", user="root",
                                     password="root", db="MobileAppDB", port=3306) as connection:
            try:
                with connection.cursor() as cursor:
                    sql = "select * from NOTE_ACCOUNT where USER_NAME = %s;"
                    cursor.execute(sql, [username])
                    if cursor.fetchone() != None :
                        return 1
                    else:
                        return 0
            except:
                print(traceback.format_exc())
                return 1
    else:
        return 1



def add_user(username, password):
    if len(username) <= lenth_of_username and len(password) <= lenth_of_password and not check_user_exist():
        with pymysql.connect(host="45.76.223.233", user="root",
                                     password="root", db="MobileAppDB", port=3306) as connection:
            try:
                with connection.cursor() as cursor:
                    sql = "select * from NOTE_ACCOUNT where USER_NAME = %s;"
                    cursor.execute(sql, [username, basic_function.real_password(password)])
                    cookies = basic_function.create_cookies()
                    if add_uid_and_cookies(username,cookies):
                        return cookies
                    return 0
            except:
                print(traceback.format_exc())
                return 0
    else:
        return 0


def check_user_password(username, password):
    if len(username) <= lenth_of_username and len(password) <= lenth_of_password:
        with pymysql.connect(host="45.76.223.233", user="root",
                             password="root", db="MobileAppDB", port=3306) as connection:
            try:
                with connection.cursor() as cursor:
                    sql = "select * from NOTE_ACCOUNT where USER_NAME = %s AND USER_PASS = %s;"
                    cursor.execute(sql, [username, basic_function.real_password(password)])
                    if cursor.fetchone() != None:
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
    if len(cookies) <= lenth_of_cookies :
        with pymysql.connect(host="45.76.223.233", user="root",
                                     password="root", db="MobileAppDB", port=3306) as connection:
            try:
                with connection.cursor() as cursor:
                    sql = "select USER_NAME from COOKIES_LIST where COOKIES = %s;"
                    cursor.execute(sql, [cookies])
                    user_id = cursor.fetchone()[0]
                    if user_id != None :
                        return user_id
                    else:
                        return None
            except:
                print(traceback.format_exc())
                return None
    else:
        return None



def get_user_list(user_id):
    doc_list = []
    if len(user_id) <= lenth_of_uid :
        with pymysql.connect(host="45.76.223.233", user="root",
                                     password="root", db="MobileAppDB", port=3306) as connection:
            try:
                with connection.cursor() as cursor:
                    sql = "select NOTE_KEY from NOTE_LIST where USER_NAME = %s;"
                    cursor.execute(sql, [user_id])
                    result = cursor.fetchall()
                    if result != None:
                        for i in result:
                            doc_list.append(i[0])
                        return doc_list
                    else:
                        return doc_list
            except:
                print(traceback.format_exc())
                return 1
    else:
        return doc_list



def add_into_list(user_id, doc):
    if len(user_id) <= lenth_of_uid and len(doc) < lenth_of_doc:
        with pymysql.connect(host="45.76.223.233", user="root",
                             password="root", db="MobileAppDB", port=3306) as connection:
            try:
                key = store_doc_to_database(doc)
                with connection.cursor() as cursor:
                    sql = "INSERT INTO NOTE_LIST (%S, %S);"
                    cursor.execute(sql, [user_id, key])
                return key
            except:
                print(traceback.format_exc())
                return 0
    else:
        return 0


def delete_form_list(user_id, key):
    if len(user_id) <= lenth_of_uid and len(key) <= lenth_of_key:
        with pymysql.connect(host="45.76.223.233", user="root",
                             password="root", db="MobileAppDB", port=3306) as connection:
            try:
                with connection.cursor() as cursor:
                    sql = "DELETE FROM NOTE_LIST WHERE USER_NAME = %S AND NOTE_KEY = %S);"
                    cursor.execute(sql, [user_id, key])
                    delete_from_key_doc = "DELETE FROM key_doc WHERE key_hash = %s"
                    cursor.execute(delete_from_key_doc,[key])
                return 1
            except:
                print(traceback.format_exc())
                return 0
    else:
        return 0



if __name__ == '__main__':
    print(get_doc_from_database('6c253648e0dfff00d6aa44566f34dae651f0d2191d5bf59dd74d8ce7314cb3e7'))
