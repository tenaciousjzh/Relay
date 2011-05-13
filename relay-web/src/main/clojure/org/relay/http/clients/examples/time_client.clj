(ns org.relay.http.clients.examples.time-client
  (:import (java.net.InetSocketAddress)
    (java.util.concurrent Executors)
    (org.jboss.netty.channel ChannelPipelineFactory Channels SimpleChannelHandler)
    (org.jboss.netty.bootstrap ClientBootstrap)
    (org.jboss.netty.channel.socket.nio NioClientSocketChannelFactory))
  (:use '(org.relay.http.clients.examples.time-client-handler))
  )

(def pipeline-factory
  (reify ChannelPipelineFactory
    (getPipeline [this]
      (Channels/pipeline (into-array SimpleChannelHandler (make-time-handler)))
      )))

(defn run-client [args]
  (let [host (args 0)
        port (args 1)
        factory (NioClientSocketChannelFactory. (Executors/newCachedThreadPool) (Executors/newCachedThreadPool))
        bootstrap (ClientBootstrap. factory)]
    (doto bootstrap
      (.setPipelineFactory pipeline-factory)
      (.setOption "tcpNoDelay" true)
      (.setOption "keepAlive" true)
      (.connect (InetSocketAddress host port)))))