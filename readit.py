import re

sTarget1 = 'Capacity'
sTarget2 = 'Uid'
uIdTotal = 0.0
uIdNamesList = []
uIdValuesList = []
doc= open("confluence.txt","w+")
#fileName = 'batterylogsPhysical.txt'
fileName = 'batterylogs.txt'

def manageBankData(item): 
    for node in item:
        sectionated = re.split('=',node)
        #print("sectionated",sectionated)
        itemName = sectionated[0]
        itemValue = sectionated[1]
        
        if not itemName in uIdNamesList:
            uIdNamesList.append(itemName)
            uIdValuesList.append(itemValue)
        else: 
            pos = uIdNamesList.index(itemName)
            uIdValuesList[pos] = float(uIdValuesList[pos]) + float(itemValue)

def createStackParams(duple):
    #print("dupla",duple)
    for stack in duple:
        manageBankData(re.split(',',stack))
    
def checkIsTypePhysical():
    with open(fileName) as file:
            while True:
                line = file.readline()
                if not line:
                    return False
                
                if sTarget2.lower() in line.lower() and ("fg" in line or "bg" in line):#Physic
                    print(line)
                    return True
            
    return False

isPhysical = checkIsTypePhysical()

with open(fileName) as file:
    while True:
        line = file.readline()
        if not line:
            break
        if sTarget1 in line:
            capData = re.split(",|:",line)
            lineCap1 = "Capacidad de batería "+ capData[1].strip().lstrip()+ " mAh\r\n"
            lineCap2 = "Batería drenada por procesos "+ capData[3].strip().lstrip()+ " mAh\r\n\r\n"
            
            doc.writelines([lineCap1,lineCap2])

        if sTarget2.lower() in line.lower():
            if isPhysical == False: 
                arr =  re.split(':|[(]|[)]',line)
                uIdTotal +=  float(arr[1])
                paramsId = re.split(" ",arr[2].strip())
                #print("array",arr,"vParams",paramsId)
                createStackParams(paramsId)   
            else:
                arr =  re.split(':',line)
                #newVal = re.split(" ",arr[1])
                #uIdTotal +=  float(newVal[1])
                #paramsId = re.split()
                #print("array",arr)
                #createStackParams(paramsId)
            
#Empieza a escribir .txt
if(isPhysical == False):
    uIdTotalStr = "Uso total de energía = ",str(uIdTotal)+ " mAh\r\n"
    doc.writelines(uIdTotalStr)

    i = 0
    while i <= len(uIdNamesList) - 1:
        createLine = "  - Uso total de ",str(uIdNamesList[i])," = ", str(uIdValuesList[i]), " mAh\r\n"
        doc.writelines(createLine)
        i += 1

    doc.close()
