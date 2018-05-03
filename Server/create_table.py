import pymysql.cursors


def excute_sql(sql):
    connection = pymysql.connect(host="45.76.223.233", user="root",
                                 password="root", db="MobileAppDB", port=3306)

    with connection.cursor() as cursor:
        result = cursor.execute(sql)
        print(result)


if __name__ == '__main__':
    sql_list = []
    create_account = "CREATE TABLE NOTE_ACCOUNT (USER_NAME VARCHAR(15) NOT NULL,USER_PASS VARCHAR(70) NOT NULL,PRIMARY KEY (USER_NAME));"
    sql_list.append(create_account)
    create_note_list = "CREATE TABLE NOTE_LIST (USER_NAME VARCHAR(15) NOT NULL ,NOTE_KEY VARCHAR(70) NOT NULL,FOREIGN KEY (USER_NAME) REFERENCES NOTE_ACCOUNT(USER_NAME));"
    sql_list.append(create_note_list)
    create_cookies_list = "CREATE TABLE COOKIES_LIST (USER_NAME VARCHAR(15) NOT NULL,COOKIES VARCHAR(70) NOT NULL,PRIMARY KEY (COOKIES),FOREIGN KEY (USER_NAME) REFERENCES NOTE_ACCOUNT(USER_NAME));"
    sql_list.append(create_cookies_list)

    modify = "ALTER TABLE COOKIES_LIST MODIFY COLUMN COOKIES VARCHAR(70);"

    modify2 = "ALTER TABLE NOTE_LIST ADD NOTE_KEY VARCHAR(70) NOT NULL;"

    modify3 = "ALTER TABLE NOTE_LIST  DROP COLUMN USER_PASS;"

    modify4 = "ALTER TABLE NOTE_ACCOUNT MODIFY COLUMN USER_PASS VARCHAR(70);"

    modify5 = "ALTER TABLE COOKIES_LIST ADD  TIME  VARCHAR(20) NOT NULL;"

    modify6 = "ALTER TABLE key_doc MODIFY MODIFY_TIME float;"

    excute_sql(modify6)
