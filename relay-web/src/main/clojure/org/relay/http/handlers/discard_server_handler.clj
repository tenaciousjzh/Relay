(ns org.relay.http.handlers.discard-server-handler
  (:import
    (org.jboss.netty.bootstrap ServerBootstrap)
    (org.jboss.netty.buffer ChannelBuffer)
    (org.jboss.netty.channel SimpleChannelHandler ChannelPipelineFactory Channels)
    (org.jboss.netty.channel.socket.nio NioServerSocketChannelFactory)
    (java.net InetSocketAddress)
    (java.util.concurrent Executors)
    )
  )



(def discard-server-handler
  (proxy [SimpleChannelHandler] []
    (messageReceived [channel-handler-context event]
      (let [buf (cast ChannelBuffer (. event getMessage))]
        (while (. buf readable)
            (println (cast char (. buf readByte)))
          )))
    (exceptionCaught [channel-handler-context exception-event]
      (println "Discard Server ran into a problem processing a request: " exception-event)
      )))

(def channel-pipeline-factory
  (reify ChannelPipelineFactory
    (getPipeline [this]
      (Channels/pipeline discard-server-handler))))

(defn run-discard-server [port]
  (let [factory (NioServerSocketChannelFactory. (Executors/newCachedThreadPool) (Executors/newCachedThreadPool))
        bootstrap (ServerBootstrap. factory)
        _ (. bootstrap (setPipelineFactory channel-pipeline-factory))
        _ (. bootstrap (setOption "child.tcpNoDelay" true))
        _ (. bootstrap (setOption "child.keepAlive" true))]
    (. bootstrap (bind (InetSocketAddress. port)))))
