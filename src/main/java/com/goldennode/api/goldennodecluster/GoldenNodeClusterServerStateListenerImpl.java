package com.goldennode.api.goldennodecluster;

import org.slf4j.LoggerFactory;

import com.goldennode.api.cluster.ClusterException;
import com.goldennode.api.cluster.Operation;
import com.goldennode.api.core.RequestOptions;
import com.goldennode.api.core.Server;
import com.goldennode.api.core.ServerStateListener;

public class GoldenNodeClusterServerStateListenerImpl implements ServerStateListener {
	static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GoldenNodeClusterServerStateListenerImpl.class);
	/**
	 * A reference to cluster in case it is needed in this class
	 */
	private GoldenNodeCluster cluster;

	GoldenNodeClusterServerStateListenerImpl(GoldenNodeCluster cluster) {
		this.cluster = cluster;
	}

	/**
	 * This method is called when local server is started. addServerToCluster is
	 * called within cluster
	 */
	@Override
	public void serverStarted(Server server) {
		LOGGER.debug("***server started... id : " + server.getShortId());
	}

	@Override
	public void serverStopping(Server server) {
		LOGGER.debug("***server stopping... id : " + server.getShortId());
		try {
			cluster.multicast(new Operation(null, "announceServerLeaving", cluster.getOwner()), new RequestOptions());
		} catch (ClusterException e) {
			LOGGER.error("Can't announce server leaving: " + cluster.getOwner());
			// This shouldn't never happen.
		}
	}

	@Override
	public void serverStopped(Server server) {
	}

	@Override
	public void serverStarting(Server server) {
		//
	}
}
