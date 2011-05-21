(ns org.relay.http.clients.examples.time-client
  (:import (java.net.InetSocketAddress)
    (java.util.concurrent Executors)
    (org.jboss.netty.channel ChannelPipelineFactory Channels SimpleChannelHandler)
    (org.jboss.netty.bootstrap ClientBootstrap)
    (org.jboss.netty.channel.socket.nio NioClientSocketChannelFactory))
  (:use '(org.relay.http.clients.examples.time-client-handler)
    '(org.relay.http.clients.examples.time-decoder))
  )

(def pipeline-factory
  (reify ChannelPipelineFactory
    (getPipeline [this]
      ;build a multi-stage pipeline where the time decoder determines if there are enough bytes
      ;received yet in the stream of data to hand a message event to the time handler.
      (Channels/pipeline (into-array ChannelHandler (list (make-time-decoder) (make-time-handler))))
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