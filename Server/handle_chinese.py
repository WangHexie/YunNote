# useless file
with open("./1000.txt", "r") as f:
    text = f.read()

chi_list = text.split(" ")
dic = {}

for i in range(16):
    for i2 in range(16):
        print(str(hex(i))[-1] + str(hex(i2))[-1])
        dic[str(hex(i))[-1] + str(hex(i2))[-1]] = chi_list[(i) * 16 + i2]

with open("./dic.txt", "w") as f:
    f.write(str(dic))
