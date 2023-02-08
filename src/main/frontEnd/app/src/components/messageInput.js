import React from 'react';

function MessageInput() {

    return (
      <div className="container-fluid">
      <form className="form-horizontal" role="form" 
            //onSubmit={this.onSubmit.bind(this)}
        >
        <div className="form-group">
          <div className="input-group">
            <div className="input-group-addon">Type Message</div>
            <input type="text"
              value={'this.state.message'}
              //onChange={event => this.onInputChange(event.target.value)}
              className="form-control input-lg"></input>
          </div>
        </div>
      </form>
    </div>
    );
  }

  export default MessageInput;