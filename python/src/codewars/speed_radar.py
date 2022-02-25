"""
Problem Statement
    
A speed radar is installed in a highway zone where the maximum speed limit is maxLimit mph, and the minimum speed limit is minLimit mph. Any reading that is strictly above or below this interval is an infringement.
Periodically, the radar readings are analyzed to make sure that the sensors are working properly. It is assumed that most drivers obey speed limits, and therefore, the radar will be considered faulty if more than 10% of its readings are infringements.
Given the radar readings over a period of time, return the average speed of all cars that are driving within the speed limits. If the radar is faulty, return 0.0.
Definition
    
Class:
SpeedRadar
Method:
averageSpeed
Parameters:
integer, integer, tuple (integer)
Returns:
float
Method signature:
def averageSpeed(self, minLimit, maxLimit, readings):

"""
class SpeedRadar:
    def averageSpeed(self, minLimit, maxLimit, readings):
        total = 0.0
        inf = 0
        for r in readings:
            if r > maxLimit or r < minLimit:
                inf += 1
            else:
                total += r

        if inf*10 > len(readings):
            return 0.0
        else:
            print total, len(readings)
            avg = total / (len(readings)-inf)
            print avg
            return avg
       

s = SpeedRadar()
a = s.averageSpeed(1, 50, [42,43,44,45,46,47,48,49,50,51] )
print a
