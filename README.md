A light weight spsc feed system which aims to minimize synchronization overhead.   

Verified using jcstress, see https://github.com/leisurelyrcxf/jcstress-memorder/blob/master/src/main/java/com/vmlens/stresstest/tests/datastructure/concurrent/producerconsumer/SpscBlockingQueue_No_Dangling.java

Result:    Results across all configurations:

RESULT     SAMPLES     FREQ       EXPECT       DESCRIPTION
0         25,920,822  100.00%   Acceptable  Gracefully finished