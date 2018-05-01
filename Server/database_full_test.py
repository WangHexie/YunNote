from Server import database


def test_database(case):
    if case == 0:
        print(database.check_user_exist("123"))
    if case == 1:
        print(database.add_user("123", "888"))
    if case == 2:
        print(database.check_user_password("123", "888"))
    if case == 3:
        print(database.get_user_by_cookies("213b43a726605044db6cdaf69aaecba1cd178e17a2341601184022d96b41f9b1"))
    if case == 4:
        print(database.get_user_list("123"))
    if case == 5:
        print(database.add_into_list("123", "879546"))
    if case == 6:
        print(database.delete_form_list('123', '7eca4c462b21f8af44b31102786a40bb761dec5d44fbe8e370b6ca484c14a4fa'))
    if case == 7:
        print(database.add_cookies_live_time("6629034154b9015c7b189c60b9f95ea02d1a6174a4433b6c8ea649ad3947f766"))
    if case == 8:
        print(database.delete_one_cookies("89891a727f1a952a35e420e94fc56b3a2dd28a41a16c75b79201adef42a64459"))

if __name__ == "__main__":
    test_database(8)
    # for i in range(7):
    #     test_database(i)
