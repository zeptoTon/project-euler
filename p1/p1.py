"""
P1 Multiples of 3 and 5
Find the sum of all the multiples of 3 or 5 below 1000.
"""
"""
since 3+3*2+3*3+3*4 = 3(1+2+3+4)
therefore -> 3(1+2+3+....n) where n*3 <1000, find max n

from above we can construct 2 formula for 3 and 5.
but we need to remove the duplicate value of being 3 AND 5 multiples

"""

# from memory_profiler import profile
from multiprocessing import Pool
import time
import cProfile

BOUND = 100000000
NUM_PROCESS = 10
# Warning, we assume STEP is a integar only.
STEP = BOUND/NUM_PROCESS

# @profile
def main():
  print fastest_way()
  print alternate_method_xrange()
  print alternate_method_range()


def fastest_way():
  n = int((BOUND-1)/3)
  m = int((BOUND-1)/5)
  r = int((BOUND-1)/(5*3))

  return (3*(n+1)*n/2) + (5*(m+1)*m/2) - (15*(r+1)*r/2)


"""
use in multiprocessing_the_wrong_way
"""
def mod_check(x):
  if x%3==0 or x%5==0:
    return x
  else:
    return 0

"""
use in multiprocessing_the_right_way
"""
def mod_check_with_range(r):
  a = 0
  for x in xrange(r, r+STEP):
    if x%3==0 or x%5==0:
      a += x
  return a  


def alternate_method_xrange():
  # loop throught each value from 1..1000 by range / xrange
  # check value and add it up if condition matched
  a = 0
  for x in xrange(BOUND):
    if x%3==0 or x%5==0:
      a += x
  return a


def alternate_method_range():
  a = 0
  for x in range(BOUND):
    if x%3==0 or x%5==0:
      a += x
  return a
  pass


def alternate_method_multiprocess_the_wrong_way():
  t = time.time()
  p = Pool(NUM_PROCESS)
  res = p.map(mod_check, xrange(BOUND))
  r = sum(res)
  print '\tmap(mod_check, range(BOUND)):\n\t\t%s seconds' % \
          (time.time() - t)
  return r


def alternate_method_multiprocess_the_right_way():
  t = time.time()
  p = Pool(NUM_PROCESS)
  res = p.map(mod_check_with_range, range(0, BOUND, STEP))
  r = sum(res)
  print '\tmap(mod_check_with_range, range(BOUND)):\n\t\t%s seconds' % \
          (time.time() - t)
  return r  


if __name__ == "__main__":
  cProfile.run('main()')
  # print alternate_method_multiprocess_the_wrong_way()
  print alternate_method_multiprocess_the_right_way()

"""
Easter Egg - memory_profiler:
By profiling the script, 
you should see no different because the BOUND(range) is too small.
Trying with sth like 100000000 will see the result.
however, the range method consume memory. (thats why we have xrange)
install memory_profiler, by pip or easy install 
$ easy_install -U memory_profiler # pip install -U memory_profiler

uncomment "from memory_profiler import profile"
uncomment "@profile"

run with -m memory_profiler
The memeory used around 3MB for BOUND 100K
and 31MB for BOUND 1000K

=================================================

Additional: multiprocessing (CPU BOUND Solution)
I am working on quad cpu machine, therefore we could gain adv!
Note: STEP has to be in int only, else everything break
question is how many step(process)? I have a very good time with 10 step with 100K.
Remeber the greater the number, its harder to mod(%). =]
early finished process will take up the next waiting process in the quene(although we are using pool, principle stay the same)

=================================================
"""

