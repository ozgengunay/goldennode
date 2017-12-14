package com.goldennode.api.cluster;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.goldennode.api.core.RequestOptions;
import com.goldennode.api.core.Response;
import com.goldennode.api.core.Server;
import com.goldennode.api.goldennodecluster.HeartbeatTimer;
import com.goldennode.api.helper.LockHelper;

public abstract class Cluster {
    public abstract Server getOwner();

    public abstract Server getCandidateServer();

    public abstract <T extends ClusteredObject> T attach(T t) throws ClusterException;

    public abstract <T extends ClusteredObject> T newClusteredObjectInstance(String publicName, Class<T> claz)
            throws ClusterException;

    public abstract <K, V> Map<K, V> newReplicatedMemoryMapInstance(String publicName) throws ClusterException;

    public abstract <E> List<E> newReplicatedMemoryListInstance(String publicName) throws ClusterException;

    public abstract <E> Set<E> newReplicatedMemorySetInstance(String publicName) throws ClusterException;

    public abstract void multicast(Operation operation, RequestOptions options) throws ClusterException;

    public abstract Object safeMulticast(Operation o) throws ClusterException;

    public abstract MultiResponse tcpMulticast(Collection<Server> peers, Operation operation, RequestOptions options);

    public abstract Response unicastTCP(Server remoteServer, Operation operation, RequestOptions options)
            throws ClusterException;

    public abstract Response unicastUDP(Server remoteServer, Operation operation, RequestOptions options)
            throws ClusterException;

    public abstract void start() throws ClusterException;

    public abstract void stop() throws ClusterException;

    public abstract Collection<Server> getPeers();

    protected abstract void lock(ClusteredObject co) throws ClusterException;

    protected abstract void unlock(ClusteredObject co) throws ClusterException;

    protected abstract void lockInterruptibly(ClusteredObject co) throws ClusterException;

    protected abstract boolean tryLock(ClusteredObject co) throws ClusterException;

    protected abstract boolean tryLock(ClusteredObject co, long timeout, TimeUnit unit) throws ClusterException;

    @Override
    public String toString() {
        return " > Cluster [owner=" + getOwner() + "] ";
    }

    public void reboot() {
        try {
            stop();
            LockHelper.sleep(HeartbeatTimer.TASK_PERIOD * 2);
            start();
        } catch (Exception e) {//NOPMD
            //Nothing to do
        }
    }
}
