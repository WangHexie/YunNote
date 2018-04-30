import pymysql.cursors

connection = pymysql.connect(host="45.76.223.233", user="root",
                             password="root", db="MobileAppDB", port=3306)

try:
    with connection.cursor() as cursor:
        # Read a single record
        sql = "SELECT `username`, `password` FROM Account where username = 'admin'"
        cursor.execute(sql)
        result = cursor.fetchone()
        print(result)
finally:
    connection.close()
