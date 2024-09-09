from flask import Flask
import json

app = Flask(__name__)

@app.route('/')
def get_course():
    with open('course.json') as f:
        course = json.load(f)
    return course

if __name__ == '__main__':
    app.run()