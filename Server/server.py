from flask import Flask, request, render_template

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def home():
    return render_template('home.html')


@app.route('/get', methods=['GET'])
def get_doc():
    key = request.args.get('key')
    return key

@app.route('/store', methods=['POST'])
def get_doc():
    doc = request.args.get('doc')
    key = store_doc_to_database(doc)
    return key

def get_doc_from_database(key):
    return 0

def store_doc_to_database(key):
    return 0


if __name__ == '__main__':
    app.run(host='::', threaded=True)
