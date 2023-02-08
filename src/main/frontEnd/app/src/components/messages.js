import React from 'react';
import HumanizedTime from './humanizedTime';

function Messages() {
    return (
      <div key={'message.id'} className="list-group-item">
        <div className="media">
          <div className="media-left">
            <img className="media-object img-circle" src={'message.user.avatar'}/>
          </div>
          <div className="media-body">
            <div className="row">
              <div className="col-md-2 text-left text-info">
                {'message.user.alias'}
              </div>
              <div className="col-md-8 text-left">{'message.message'}</div>
              <div className="col-md-2 text-right text-info">
                <small><HumanizedTime date={'message.timestamp'}/></small>
              </div>
            </div>
          </div>
        </div>
    </div>
    );
  }

  export default Messages;