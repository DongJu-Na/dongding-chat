import React from 'react';
import '../styles/index.scss';
import MessageInput from './messageInput';
import Messages from './messages';
import Nav from './nav';
import OnlineUsers from './onlineUsers';
import UserProfile from './userProfile';

function Chat() {
    return (
    <div className="full-height">
        <div className="row">
          <Nav/>
        </div>
        <div className="row full-height">
          <div className="col-md-3 full-height">
            <UserProfile/>
            <OnlineUsers/>
          </div>
          <div className="col-md-9 full-height">
            <div className="full-height">
              <Messages/>
              <MessageInput/>
            </div>
          </div>
        </div>
      </div>
    );
  }

  export default Chat;