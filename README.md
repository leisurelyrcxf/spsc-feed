A light weight spsc feed system which aims to minimize synchronization overhead.   

Verified using jcstress, see https://github.com/leisurelyrcxf/jcstress-memorder/blob/master/src/main/java/com/vmlens/stresstest/tests/datastructure/concurrent/producerconsumer/SpscBlockingQueue_No_Dangling.java

Result:    Results across all configurations:

RESULT&nbsp;&nbsp;&nbsp;&nbsp;SAMPLES&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FREQ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EXPECT&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DESCRIPTION&nbsp;&nbsp;&nbsp;&nbsp;
0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;25,920,822&nbsp;&nbsp;100.00%&nbsp;&nbsp;&nbsp;Acceptable&nbsp;&nbsp;Gracefully&nbsp;finished