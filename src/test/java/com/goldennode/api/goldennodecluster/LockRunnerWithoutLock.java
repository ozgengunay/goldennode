package com.goldennode.api.goldennodecluster;

public class LockRunnerWithoutLock implements Runnable {
    private ClusteredLockTest tl;
    private int loopCount;
    private int taskDuration;

    public LockRunnerWithoutLock(ClusteredLockTest tl, int loopCount, int taskDuration) {
        this.tl = tl;
        this.loopCount = loopCount;
        this.taskDuration = taskDuration;
    }

    private void doJob() {
        try {
            for (int i = 0; i < loopCount; i++) {
                int tmp = tl.getCounter();
                if (taskDuration > 0) {
                    Thread.sleep(taskDuration);
                }
                tl.setCounter(tmp + 1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        doJob();
    }
}
