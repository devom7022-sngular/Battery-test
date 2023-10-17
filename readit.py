import re
from tabulate import tabulate

sCapacity = 'Capacity'
sUID = 'Uid'
uIdTotal = 0.0
uIdNamesList = []
uIdValuesList = []
doc= open("results.txt","w+")
#fileName= 'batterylogsPhysical.txt'
fileName= 'batterylogs.txt'
package= "com.example.batterytestapplication"

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
    
def checkIsTypePhysical():
    with open(fileName) as file:
        while True:
            line = file.readline()
            if not line:
                return False
            
            if sUID.lower() in line.lower() and ("fg" in line or "bg" in line):#Physic
                return True
            
    return False

def getIdPackage():
    indexId = 0
    temporal= []
    with open(fileName) as file:
        n= 0
        while True:
            line = file.readline()
            
            if not line:
                break
            if "  " in line and "   " not in line:
                arrT = line.rstrip().replace(':','').replace('  ','')
                n+=1
                temporal.append(arrT)
                if arrT == "1000":
                    indexId = n
    
    return temporal[indexId]         

isPhysical= checkIsTypePhysical()
appID= getIdPackage()

with open(fileName) as file:
    while True:
        line = file.readline()
        if not line:
            break
        if sCapacity in line:
            capData = re.split(",|:",line)
            lineCap1 = "Capacidad de batería "+ capData[1].strip().lstrip()+ " mAh\r\n"
            lineCap2 = "Batería drenada por procesos "+ capData[3].strip().lstrip()+ " mAh\r\n\r\n"
            
            doc.writelines([lineCap1,lineCap2])
       
        if isPhysical == False: 
            arr =  re.split(':|[(]|[)]',line)
            uIdTotal +=  float(arr[1])
            paramsId = re.split(" ",arr[2].strip())
            for stack in paramsId:
                manageBankData(re.split(',',stack))
        else:
            if "UID "+appID in line:
                #Primero separar los valores con mayor jerarquía 
                segments = re.split('[(]|[)]',line)
                line = line.strip()
                indexChilds = line.index('(')
                mainData = re.split(' ',line[0:indexChilds].strip())
                #add params and values to array
                for i in range(1,len(mainData)):
                    if ":" in mainData[i] :
                        uIdNamesList.append(mainData[i])
                        
                    else:
                        uIdValuesList.append(mainData[i])
                
                #manage data child
                childData = line[indexChilds+1: len(line)-1 ]
                cildrenArr = re.split('=',childData)
                
                uIdNamesList.append(cildrenArr[0].strip())

                for i in range(1,len(cildrenArr)-1):
                    segment= cildrenArr[i].strip()
                    cutSegment= re.split(' ',segment)
                    nextValue = cutSegment[len(cutSegment)-1]
                    indexSegment = segment.index(nextValue)
                    firstName = segment[0:indexSegment]
                    uIdValuesList.append(firstName)
                    uIdNamesList.append(nextValue)
                    print(nextValue,indexSegment, firstName)

                    
                uIdValuesList.append(cildrenArr[len(cildrenArr)-1])
                


                # childData = childData.replace(":",">").replace(")","),").replace("=",":=").replace(" (","(")
                # childData = re.split(',',childData)

                # for i in range(0,len(childData)-1):
                #     values = childData[i].count(':=') 
                #     if values > 1:
                #         nesteds = re.split(" ",childData[i])
                #         childData.pop(i)
                #         for j in range(len(nesteds)):
                #             childData.append(nesteds[j])

                # for child in childData:
                #     child = re.split('=',child.strip())

                #     if len(child) == 1:
                #         continue

                #     for i in range(0,len(child)):
                #         if ":" in child[i] :
                #             uIdNamesList.append(child[i].strip())
                #         else:
                #             uIdValuesList.append(child[i].strip())
            
#Empieza a escribir .txt
#if(isPhysical == False):
#uIdTotalStr = "Uso total de energía = ",str(uIdTotal)+ " mAh\r\n"
#doc.writelines(uIdTotalStr)

tableNames = []

i = 0
while i <= len(uIdNamesList) - 1:
    row = [str(uIdNamesList[i]),str(uIdValuesList[i])]
    tableNames.append(row)
    
    #createLine = "  - ",str(uIdNamesList[i]), " ", str(uIdValuesList[i]), " \r\n"
    #doc.writelines(createLine)
    i += 1

tabFull = tabulate(tableNames,["Campos","Valor"])
print(tabFull)
doc.writelines(tabFull)
doc.close()
