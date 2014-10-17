#!/usr/bin/python
import random
import sys

n = int(sys.argv[1])
with open('data.txt','w') as f:
	for i in xrange(n):
		a = random.randint(1,n)
		f.write(str(a)+" ")
