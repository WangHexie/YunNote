import pymysql.cursors

from Server import basic_function


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
    return 0

def check_user_exist(username):
    return 1 | 0


def add_user(username, password):
    cookies = 0
    return cookies | 0


def check_user_password(username, password):
    cookies = 0
    return cookies | 0


def get_user_by_cookies(cookies):
    user_id = 0
    return user_id


def get_user_list(user_id):
    doc_list = []
    return doc_list


def add_into_list(user_id, doc):
    key = 0
    return key


def delete_form_list(user_id, key):
    return 0 | 1


if __name__ == '__main__':
    print(get_doc_from_database('6c253648e0dfff00d6aa44566f34dae651f0d2191d5bf59dd74d8ce7314cb3e7'))
