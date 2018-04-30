import pymysql.cursors
from Server import basic_function


def get_doc_from_database(key):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)
    try:
        with connection.cursor() as cursor:
            sql = "SELECT doc FROM key_doc where key_hash = '" + key + "'"
            cursor.execute(sql)
            result = cursor.fetchone()[0]
    finally:
        connection.close()

    return result


def store_doc_to_database(doc):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)
    try:
        with connection.cursor() as cursor:
            sql = "insert into key_doc value('" + basic_function.create_key() + "','" + doc + "');"
            cursor.execute(sql)
            connection.commit()
    finally:
        connection.close()
    return 0


if __name__ == '__main__':
    print(get_doc_from_database('6c253648e0dfff00d6aa44566f34dae651f0d2191d5bf59dd74d8ce7314cb3e7'))
