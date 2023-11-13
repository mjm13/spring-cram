import sys
import json

# print(sys.argv[1].replace("'", "\""))
# test = "{\"x\":2,\"y\":3}"
data = json.loads(sys.argv[1].replace("'", "\""))
# data = sys.argv[1]
x = data['x']
y = data['y']
print(x+y)