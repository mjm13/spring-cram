import json

def add(data):
    data = json.loads(data)
    return data['x'] + data['y']