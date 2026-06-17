/** 

 * useWebSocket Custom Hook 

 * Manages WebSocket connection 

 * for real-time chat 

 */ 

import { useEffect, useRef, useState } 

                              from 'react'; 

import { Client } from '@stomp/stompjs'; 

import SockJS from 'sockjs-client'; 

 

const useWebSocket = (roomId, username) => { 

  const [messages, setMessages] = useState([]); 

  const [connected, setConnected] = 

                              useState(false); 

  const clientRef = useRef(null); 

 

  useEffect(() => { 

    if (!roomId || !username) return; 

 

    const token = localStorage.getItem('token'); 

 

    const client = new Client({ 

      webSocketFactory: () => 

        new SockJS('http://localhost:8080/ws'), 

 

      connectHeaders: { 

        Authorization: `Bearer ${token}`, 

      }, 

 

      onConnect: () => { 

        setConnected(true); 

 

        // Subscribe to room 

client.subscribe(
  `/topic/room/${roomId}`,
  (message) => {

    console.log("RECEIVED WS:", message.body);

    const received = JSON.parse(message.body);

    setMessages((prev) => [
      ...prev,
      received
    ]);
  }
);

 

        // Send join notification 

        client.publish({ 

          destination: 

            `/app/chat/${roomId}/join`, 

          body: JSON.stringify({}), 

        }); 

      }, 

 

      onDisconnect: () => { 

        setConnected(false); 

      }, 

 

      onStompError: (frame) => { 

        console.error('STOMP error:', frame); 

        setConnected(false); 

      }, 

    }); 

 

    client.activate(); 

    clientRef.current = client; 

 

    return () => { 

      if (clientRef.current?.connected) { 

        clientRef.current.publish({ 

          destination: 

            `/app/chat/${roomId}/leave`, 

          body: JSON.stringify({}), 

        }); 

      } 

      client.deactivate(); 

    }; 

  }, [roomId, username]); 

 

const sendMessage = (content) => {
  if (clientRef.current?.connected) {

    const payload = {
      content: content,
      sender: username
    };

    console.log("SENDING:", payload);

    clientRef.current.publish({
      destination: `/app/chat/${roomId}`,
      body: JSON.stringify(payload)
    });
  }
};

 

  return { messages, connected, sendMessage }; 

}; 

 

export default useWebSocket; 