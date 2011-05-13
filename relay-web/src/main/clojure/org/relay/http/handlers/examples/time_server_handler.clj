(ns org.relay.http.handlers.examples.time-server-handler
  (:import
    (org.jboss.netty.bootstrap ServerBootstrap)
    (org.jboss.netty.buffer ChannelBuffers)
    (org.jboss.netty.channel SimpleChannelHandler ChannelFutureListener)
    (org.jboss.netty.channel.socket.nio NioServerSocketChannelFactory)
    (java.net InetSocketAddress)
    (java.util.concurrent Executors)))

(def future-listener
  (reify ChannelFutureListener
    (operationComplete [this channel-future]
      (println "Closing connection as time has been sent.")
      (..
        (.getFuture channel-future)
        (close)))))

(def time-handler
  (proxy [SimpleChannelHandler] []
    (channelConnected [channel-handler-context channel-state-event]
      ;writing a 32-bit integer to the buffer so we need to set capacity for the buffer to 4-bytes.
      (let [capacity 4
            channel (.getChannel channel-state-event)
            time (ChannelBuffers/buffer capacity)
            _ (. time writeInt (/ (.currentTimeMillis System) 1000))
            future (. channel write time)]
        (. future addListener future-listener)))

    (exceptionCaught [channel-handler-context exception-event]
      (..
        (.getCause exception-event)
        (printStackTrace))
      (..
        (.getChannel exception-event)
        (close)))))


;(defn run-time-server
;  "Generates a ChannelHandler that will send a 32-bit integer representing the current time when
;  a connection is meade and hten close the connection."
;  [port]
;  (let [factory (NioServerSocketChannelFactory. (Executors/newCachedThreadPool) (Executors/newCachedThreadPool))
;        bootstrap (ServerBootstrap. factory)]
;    (doto bootstrap
;      (.setPipelineFactory channel-pipeline-factory)
;      (.setOption "child.tcpNoDelay" true)
;      (.setOption "child.keepAlive" true)
;      (.bind (InetSocketAddress. port)))))
