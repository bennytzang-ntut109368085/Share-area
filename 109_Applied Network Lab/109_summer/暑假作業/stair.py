#匯入pandas
# import pandas as pd 

# stair =pd.Series(["v", "u","t", "s", "r"])
# cross =pd.DataFrame({[1, 8,  't'],[2, 3, 's']})
# stair = [1, 's', 't']
# cross = [1, 8,  't'],[2, 3, 's']
# print(stair[0])
# print(cross[0][0])

# if stair[0] == cross[0][0]:
#     print("yes")
# else:
#     print("no")

# print(cross)

# for i in range(int(len(cross)/3)):
    # new_cross[i].append(cross[0:2])
# print(new_cross)        
# cross = [1, 2, 'r'],[2, 3, 's']
#cross = [1, 2,  'r'],[2, 3, 's'],[3, 4, 'r'],[1, 8, 'u'],[6, 7, 's'],[7, 8, 'r'],[1, 2, 't'],[2, 3, 'u'],[3, 4, 'v'],[5, 6, 'v'],[6, 7, 'u'],[7, 8, 't'],[1, 2, 'v'],[4, 5, 's'],[4, 5, 't'],[4, 5, 'u']
#錯誤的例子
#cross = [1, 2,  'r'],[2, 3, 's'],[3, 4, 'r'],[1, 8, 'u'],[3,4,'s'],[7,8,'s'],[6, 7, 's'],[7, 8, 'r'],[1, 2, 't'],[2, 3, 'u'],[3, 4, 'v'],[5, 6, 'v'],[6, 7, 'u'],[7, 8, 't'],[1, 2, 'v'],[4, 5, 's'],[4, 5, 't'],[4, 5, 'u']
#cross = [1, 8,  't'],[2, 3, 's'],[3, 4, 'r'],[4, 5, 's'],[6, 7, 's'],[7, 8, 'r'],[1, 8, 'u'],[2, 3, 'u'],[3, 4, 't'],[4, 5, 'u'],[6, 7, 'u'],[7, 8, 'v'],[1, 2, 'v'],[5, 6, 'r'],[5, 6, 't'],[5, 6, 'v']


import sys

def StringToList(string1):

    string2 = list(string1)
    cross =[]
    a = []
    for i in string2:
        if i == ',' or i =='[' or i == ']' or i == " " or i =="'":
            continue
        else:
            a.append(i)
    # print(a)
    cross = [a[i:i+3]for i in range(0,len(a),3)]
    # cross = [int(cross[i:i+2])for i in range(0,len(cross),2)]
    for i in range(len(cross)):
        cross[i][0] = int(cross[i][0])
        cross[i][1] = int(cross[i][1])
    return cross

def judge(cross):
    judgement = False
    for i in range(len(cross)-2): #i= 0~13
        for k in range(len(cross)-1):
            if k+1 > i:
                if cross[i][2] == cross[k+1][2]: 
                    if cross[i][0] == cross[k+1][0] or cross[i][0] == cross[k+1][1] or cross[i][1] == cross[k+1][0] or cross[i][1] == cross[k+1][1]:
                        print("有相連接的線段",cross[i],cross[k+1],"，請重新設定參數")
                        judgement = True

    if judgement == True:
        sys.exit()
           
def run(cross):
    stair = ['v', 'u', 't', 's', 'r', 'end']
    locate = ['none', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H']
    num = 1
    while num <= 8:
        start = [num,'v']
        for step in range(len(stair)):
            for i in range(len(cross)):
                if start[0] == cross[i][0] or start[0] == cross[i][1]:
                    if start[1] == cross[i][2]:
                        if start[0] != cross[i][0]:
                            start[0] = cross[i][0]
                            #print(start)
                        else:
                            start[0] = cross[i][1]
                            #print(start)
            if start[1] == stair[5]:
                break            
            start[1] = stair[step+1]
            #print(start)
        
        print("No",num,"參賽者之籤碼為 ","[",locate[start[0]],"]")
        num = num+1
    print("-----------End-----------")

def main(cross):
    cross = StringToList(string1)
    judge(cross)
    run(cross)

string1 = input("input: ")
main(string1)