A light weight spsc feed system which aims to minimize synchronization overhead.   

Verified using jcstress, see https://github.com/leisurelyrcxf/jcstress-memorder/blob/master/src/main/java/com/vmlens/stresstest/tests/datastructure/concurrent/producerconsumer/SpscBlockingQueue_No_Dangling.java

Result:  
RESULT  SAMPLES     FREQ      EXPECT  DESCRIPTION
0  136,557  100.00%  Acceptable  Gracefully finished