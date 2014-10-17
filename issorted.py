import sys

file_ = sys.argv[1]
with open(file_) as f:
    a = f.read()
    a = a.split()
    a = [int(i) for i in a]
    b = [i for i in a]
    a.sort()
    if a == b:
        print "Sorted"
    else:
        print "Not sorted"
    
