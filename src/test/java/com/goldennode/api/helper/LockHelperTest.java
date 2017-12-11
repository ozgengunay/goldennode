package com.goldennode.api.helper;

import org.junit.Test;

import com.goldennode.testutils.GoldenNodeJunitRunner;
import com.goldennode.testutils.ThreadUtils;

public class LockHelperTest  extends GoldenNodeJunitRunner{

    @Test
    public void sleep() {
        final long waitInMs = 1000;
        ThreadUtils.threadInterrupter(Thread.currentThread(), waitInMs);
        LockHelper.sleep(waitInMs * 2);
        try {
            synchronized (this) {

                wait(waitInMs);
            }
        } catch (InterruptedException e) {
            // Don't throw
        }
    }

    @Test(expected = InterruptedException.class)
    public void threadSleep() throws InterruptedException {
        final long waitInMs = 1000;
        ThreadUtils.threadInterrupter(Thread.currentThread(), waitInMs);
        Thread.sleep(waitInMs * 2);
    }

}