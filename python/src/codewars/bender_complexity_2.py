import numpy as np


def build_f_const(X, y):
    # max plus half a standard deviation
    k = np.max(y)  # Plus an epsilon?
    return k


def build_f_log(X, y):
    # Start wit min constant
    # y = k * log(x)
    # -> k = np.max(y / log(x))
    k = np.max(y / np.log(X)) # plus an epsilon ?
    return k


def build_f_line(X, y):
    k = np.max(y / X) # plus an epsilon ?
    return k


def build_f_nlogn(X, y):
    k = np.max(y / (X*np.log(X))) # plus an epsilon ?
    return k


def build_f_quad(X, y):
    k = np.max(y / X**2) # plus an epsilon ?
    return k


def build_f_n2logn(X, y):
    k = np.max(y / (X**2 * np.log(X)))+0.4  # plus an epsilon ?
    return k


def build_f_cub(X, y):
    k = np.max(y / X**3)  # plus an epsilon ?
    return k


def main():
    m = int(raw_input())  # number of samples - read
    X = np.ones((m, 1))
    y = np.zeros((m, 1))
    for i in xrange(m):
        parts = [int(j) for j in raw_input().split()]
        X[i][0] = parts[0]  # Second column
        y[i][0] = parts[1]

    # Split them in test vs bound
    index = np.array([i for i in xrange(m)])
    tp = 0.7
    topX = X[index < m*tp, :]
    botX = X[index >= m*tp, :]
    topy = y[index < m*tp, :]
    boty = y[index >= m*tp, :]

    # idea - try to bound them in increasing order
    # O(1)
    k1 = build_f_const(topX, topy)

    # test if further will surpass it- TODO count how many?
    if np.all(boty < k1):
        print "O(1)"
        return

    # O(log n)
    k2 = build_f_log(topX, topy)
    if np.all(boty < k2*np.log(botX)):
        print "O(log n)"
        return

    # O(n)
    k3 = build_f_line(topX, topy)
    if np.all(boty < k3*botX):
        print "O(n)"
        return

    # O(n log n)
    k3 = build_f_nlogn(topX, topy)
    if np.all(boty < k3*botX*np.log(botX)):
        print "O(n log n)"
        return

    # O(n^2)
    k3 = build_f_quad(topX, topy)
    if np.all(boty < k3*(botX**2)):
        print "O(n^2)"
        return

    # O(n^2*log n)
    k3 = build_f_n2logn(topX, topy)
    if np.all(boty < k3*(botX**2)*np.log(botX)):
        print "O(n^2 log n)"
        return

    # O(n^3)
    k3 = build_f_cub(topX, topy)
    if np.all(boty < k3*(botX**3)):
        print "O(n^3)"
        return

    # O(2^n)
    print "O(2^n)"

if __name__ == '__main__':

    main()
