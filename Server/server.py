from flask import Flask, request, render_template
import threading
from Server import basic_function
from Server import database
import json

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def home():
    return render_template('home.html')


@app.route('/get', methods=['GET'])
def get_doc():
    key = request.args.get('key')
    return database.get_doc_from_database(key)


@app.route('/store', methods=['POST'])
def store_doc():
    doc = request.form['doc']
    key = database.store_doc_to_database(doc)
    return key


@app.route('/getbyck', methods=['GET'])
def get_doc_by_cnkey():
    cnkey = request.args.get('cnkey')
    part_key = basic_function.chinese_key_to_hash(cnkey)
    full_key = database.part_key_to_full_key(part_key)
    return basic_function.check_result(database.get_doc_from_database(full_key))


@app.route('/storeck', methods=['POST'])
def store_doc_by_cnkey():
    doc = request.form['doc']
    key = database.store_doc_to_database(doc)
    cnk = basic_function.hash_to_chinese_key(key)
    return cnk


@app.route('/login', methods=['POST'])
def login():
    username = request.form['username']
    password = request.form['password']
    return  basic_function.check_result(database.check_user_password(username,password))


@app.route('/check', methods=['GET'])
def check():
    username = request.args.get('username')
    return  basic_function.check_result(database.check_user_exist(username))

@app.route('/signin', methods=['POST'])
def signin():
    username = request.form['username']
    password = request.form['password']
    return  basic_function.check_result(database.add_user(username=username, password=password))

@app.route('/storelist', methods=['POST'])
def storelist():
    doc = request.form['doc']
    cookies = request.form['cookies']
    uid = database.get_user_by_cookies(cookies)
    if uid == None:
        return '0'
    else:
        database.add_cookies_live_time(cookies)
        result = database.add_into_list(user_id=uid,doc=doc)
        if result == 0:
            return '0'
        else:
            return result

@app.route('/list', methods=['POST'])
def list():
    cookies = request.form['cookies']
    uid = database.get_user_by_cookies(cookies)
    if uid == None:
        return 0
    else:
        database.add_cookies_live_time(cookies)
        result = database.get_user_list(user_id=uid)
        if result == 0 :
            return '0'
        else:
            list_doc = database.get_list_doc(result)
            re_dic = {"list":list_doc}
            return json.dumps(re_dic, ensure_ascii=False)


@app.route('/delete', methods=['POST'])
def delete():
    cookies = request.form['cookies']
    key = request.form['key']
    uid = database.get_user_by_cookies(cookies)
    if uid == None:
        return '0'
    else:
        database.add_cookies_live_time(cookies)
        return basic_function.check_result(database.delete_form_list(user_id=uid, key=key))


if __name__ == '__main__':
    threading.Thread(target=database.delete_useless_cookies).start()
    app.run( threaded=True)
