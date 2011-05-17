(ns org.relay.http.clients.examples.time-client-handler
  (:import (java.util.Date)
    (org.jboss.netty.buffer ChannelBuffer ChannelBuffers)
    (org.jboss.netty.channel SimpleChannelHandler)))

(defn make-time-handler []
  (proxy [SimpleChannelHandler] []
    (messageReceived [channel-handler-context message-event]
      ;dynamicBuffer can determine variable length packets
      ;which is useful when you don't know how many bytes were in the
      ;message that was sent.
      (let [buf (ChannelBuffers/dynamicBuffer)
            m (cast ChannelBuffer (. message-event getMessage))
            _ (. buf writeBytes m)
            current-time-millis (. buf readInt * 1000)]
        (println (Date. current-time-millis))
        (.. message-event
          (getChannel)
          (close))))

    (exceptionCaught [channel-handler-context exception-event]
      (.. exception-event
        (getCause)
        (printStackTrace))
      (.. exception-event
        (getChannel)
        (close)))))
