from flask import Flask, request, render_template

from Server import basic_function
from Server import database

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
    doc = request.args.get('doc')
    key = database.store_doc_to_database(doc)
    return key


@app.route('/getbyck', methods=['GET'])
def get_doc_by_cnkey():
    cnkey = request.args.get('cnkey')
    part_key = basic_function.chinese_key_to_hash(cnkey)
    full_key = database.part_key_to_full_key(part_key)
    return database.get_doc_from_database(full_key)


@app.route('/storeck', methods=['POST'])
def store_doc_by_cnkey():
    doc = request.args.get('doc')
    key = database.store_doc_to_database(doc)
    cnk = basic_function.hash_to_chinese_key(key)
    return cnk


if __name__ == '__main__':
    app.run(host='::', threaded=True)
