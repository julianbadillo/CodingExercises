from Euclid import euclidExt


def minimizeEquation(A,B,C):

    d,x,y = euclidExt(A,B)
    ##si no es divisible, no existe
    if C % d != 0:
        print("No es divisible")
        return None 
    k = C // d
    x = x*k
    y = y*k
    
    #buscar una solucion positiva, reducimos a y al menor positivo
    y = y % (A//d)
    x = (C - B*y)//A
    
    #incrementamos x hasta que sea positivo
    while x < 0:
        x = x + B//d
        y = y - A//d
        if y < 0:
            print("No existen puntos")
            return None
    sol1 = (x,y)
    
    #buscamos la otra solucion con x el menor positivo
    x = x % (B//d)
    y = (C - A*x)//B
    
    #incrementamos y hasta que sea positivo
    while y < 0:
        x = x - B//d
        y = y + A//d
        if x < 0:
            print("No existen puntos")
            return None
    sol2 = (x,y)
            
    #escoger la menor
    if sol1[0]+sol1[1] < sol2[0]+sol2[1]:
        return sol1
    else:
        return sol2

if __name__ == '__main__':
    A = 6
    B = 1
    C = 22
    
    print(minimizeEquation(A, B, C))