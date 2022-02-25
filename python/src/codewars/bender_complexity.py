import sys
import math
import numpy as np

alpha = 0.05

# Model works on
# - linear if not normalized
# - on constant (if not normalized)
# has problems on quadratic if constant or linear provided
def cost(Theta, X, y):
    m = len(y)
    h = np.dot(X, Theta)  # hyp
    error = h - y.flatten()  # errors
    error = error**2
    return (0.5 / m) * np.sum(error)


def grad(Theta, X, y):
    m = len(y)
    h = np.dot(X, Theta)  # hyp
    error = (h - y.flatten()) / m  # errors
    g = np.dot(error, X)
    return g.flatten()


def normalize(X, minx, sigma):
    X[:, 1:] = X[:, 1:] - minx[1:] + 1.0
    # print( X)
    if np.min(sigma[1:]) > 1.0:
        X[:, 1:] = X[:, 1:] / sigma[1:]
    # print( X)
    return X


def gradient_descend(Theta, X, y):
    c1 = cost(Theta, X, y)
    i = 0
    while i < 5000:
        delta = grad(Theta, X, y)
        # print( "+    ", delta)
        Theta = Theta - alpha * delta

        c2 = cost(Theta, X, y)
        # print( "-   ", c2)
        if c2 > c1:
            print("Increasing!!!")
            break
        else:
            c1 = c2

        if np.sum(np.abs(delta)) < 0.0000001:
            print("grad very low")
            break
        i += 1
    return Theta


# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.
def fit_constant(X, y):
    n = 2  # number of features

    # Only ones
    X = np.array(X)
    y = np.array(y)
    avgy = np.average(y, axis=0)
    sigmay = np.std(y, axis=0)
    print("Sigma on y", sigmay)
    # If constant, best guess is the average
    Theta = np.zeros(2)
    Theta[0] = avgy
    Theta[1] = 0
    print("Final Cost", cost(Theta, X, y))
    print("Final Gradient", grad(Theta, X, y))
    print("Final Coefs", Theta)


# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.
def fit_linear(X, y):
    n = 2  # number of features
    # Copy
    X = np.array(X)
    y = np.array(y)

    Theta = np.zeros(n)

    # Write an action using print()
    # To debug: print( >> sys.stderr, "Debug messages...")
    # lets do a simple linear regression
    # print( "Initial Cost", cost(Theta, X, y))
    # print( "Initial Gradient", grad(Theta, X, y))
    Theta = gradient_descend(Theta, X, y)

    print("Final Cost", cost(Theta, X, y))
    print("Final Gradient", grad(Theta, X, y))
    print("Final Coefs", Theta)

    return Theta


def fit_quadratic(X, y):
    n = 3  # number of features
    m = X.shape[0]
    X = np.array(X)
    y = np.array(y)

    # third column x^2
    X = np.insert(X, 2, X[:, 1] ** 2, axis=1)

    Theta = np.zeros(n)
    # Write an action using print()
    # To debug: print( >> sys.stderr, "Debug messages...")
    # lets do a simple linear regression
    # print( "Initial Cost", cost(Theta, X, y))
    # print( "Initial Gradient", grad(Theta, X, y))
    Theta = gradient_descend(Theta, X, y)

    print("Final Cost", cost(Theta, X, y))
    print("Final Gradient", grad(Theta, X, y))
    print("Final Coefs", Theta)
    return Theta


def fit_cubic(X, y):
    n = 4  # number of features
    m = X.shape[0]
    X = np.array(X)
    y = np.array(y)

    # quad and cub columns
    X = np.insert(X, 2, X[:, 1] ** 2, axis=1)  # X^2
    X = np.insert(X, 3, X[:, 1] ** 3, axis=1)  # X^2

    # Coefs vector
    Theta = np.zeros(n)

    # Write an action using print()
    # To debug: print( >> sys.stderr, "Debug messages...")
    # lets do a simple linear regression
    # print( "Initial Cost", cost(Theta, X, y))
    # print( "Initial Gradient", grad(Theta, X, y))
    Theta = gradient_descend(Theta, X, y)

    print("Final Cost", cost(Theta, X, y))
    print("Final Gradient", grad(Theta, X, y))
    print("Final Coefs", Theta)
    return Theta


def fit_log(X, y):
    n = 2  # number of features
    m = X.shape[0]
    X = np.array(X)
    y = np.array(y)

    X = np.insert(X[:, :1], 1, np.log(X[:, 1]), axis=1)  # log

    # Coefs vector
    Theta = np.zeros(n)
    # Write an action using print()
    # To debug: print( >> sys.stderr, "Debug messages...")
    # lets do a simple linear regression
    # print( "Initial Cost", cost(Theta, X, y))
    # print( "Initial Gradient", grad(Theta, X, y))
    Theta = gradient_descend(Theta, X, y)

    print("Final Cost", cost(Theta, X, y))
    print("Final Gradient", grad(Theta, X, y))
    print("Final Coefs", Theta)
    return Theta


test_costs = {}

m = int(raw_input())  # number of samples
X = np.ones((m, 2))
y = np.zeros((m, 1))
for i in xrange(m):
    parts = [int(j) for j in raw_input().split()]
    X[i][1] = parts[0]  # Second column
    y[i][0] = parts[1]

# Coefs vector
# Normalize x
minx = np.min(X, axis=0)
sigma = np.std(X, axis=0)
normalize(X, minx, sigma)

# Normalize y
avgy = np.average(y)
sigmay = np.std(y)
y = y - avgy
if sigmay > 1.0:
    y = y / sigmay


# TODO if sigma y too low, its constant. what means too low?
# Sigma x against simga y?
print(sigma[1], sigmay)
print(sigma[1] / sigmay)

index = np.array([i for i in xrange(m)])
topX = X[index % 2 == 0, :]
botX = X[index % 2 == 1, :]
topy = y[index % 2 == 0, :]
boty = y[index % 2 == 1, :]

thetaLinear = fit_linear(topX, topy)
c = cost(thetaLinear, botX, boty)

print("Linear test cost", c)
test_costs["O(n)"] = c

thetaQuad = fit_quadratic(topX, topy)
botX = np.insert(botX, 2, botX[:, 1] ** 2, axis=1)
c = cost(thetaQuad, botX, boty)

print("Quadratic test cost", c)
test_costs["O(n^2)"] = c

thetaCub = fit_cubic(topX, topy)
botX = np.insert(botX, 3, botX[:, 1] ** 3, axis=1)
c = cost(thetaCub, botX, boty)
print("Cubic Test Cost", c)
test_costs["O(n^3)"] = c

thetaLog = fit_log(topX, topy)
botX = np.insert(botX[:, :1], 1, np.log(botX[:, 1]), axis=1)  # only logarithmic column
c = cost(thetaLog, botX, boty)
print("Logarithmic cost", c)
test_costs["O(log n)"] = c

test_costs = sorted(test_costs.items(), key=lambda x: x[1])
print(test_costs)
# TODO change of plans
# TODO linear regression and match against half the data
# TODO apply inverse operation and fit linear always

"""
print( "Constant")
fit_constant(X, y)
print( "Quadratic")
fit_quadratic(X,y)

print( "Log")
fit_log(X,y)"""
