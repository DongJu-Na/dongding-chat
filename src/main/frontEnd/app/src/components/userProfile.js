import React from 'react';
import '../styles/profile.scss';

function UserProfile() {
    return (
      <div className="list-group user-profile">
        <div className="list-group-item">
          <img src={'this.props.user.avatar'} className="img-responsive img-circle center-block profile-image" />
        </div>
        <div className="list-group-item">
          <p className="text-center">{'this.props.user.alias'}</p>
        </div>
      </div>
    );
  }

  export default UserProfile;