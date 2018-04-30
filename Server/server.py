from flask import Flask, request, render_template
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


if __name__ == '__main__':
    app.run(host='::', threaded=True)
