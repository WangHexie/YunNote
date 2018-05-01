import pymysql.cursors

def excute_sql(sql):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)

    with connection.cursor() as cursor:
        cursor.execute(sql)
        result = cursor.fetchone()
        print(result)



if __name__ == '__main__':
    sql_list =[]
    create_account = "CREATE TABLE NOTE_ACCOUNT (USER_NAME VARCHAR(15) NOT NULL,USER_PASS VARCHAR(70) NOT NULL,PRIMARY KEY (USER_NAME));"
    sql_list.append(create_account)
    create_note_list = "CREATE TABLE NOTE_LIST (USER_NAME VARCHAR(15) NOT NULL ,USER_PASS VARCHAR(70) NOT NULL,FOREIGN KEY (USER_NAME) REFERENCES NOTE_ACCOUNT(USER_NAME));"
    sql_list.append(create_note_list)
    create_cookies_list = "CREATE TABLE COOKIES_LIST (USER_NAME VARCHAR(15) NOT NULL,COOKIES VARCHAR(20) NOT NULL,PRIMARY KEY (COOKIES),FOREIGN KEY (USER_NAME) REFERENCES NOTE_ACCOUNT(USER_NAME));"
    sql_list.append(create_cookies_list)


    for i in sql_list:
        excute_sql(i)