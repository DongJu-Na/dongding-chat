import React, { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/login.scss';

const DEFAULT_AVATAR = '//ssl.gstatic.com/accounts/ui/avatar_2x.png';

function Login() {
  const [alias,setAlias] = useState('');
  const [avatar,setAvatar] = useState('');
  const aliasInput = useRef(null);
  const navigate = useNavigate();
  useEffect(()=>{
    updateAvatar(alias);
  },[]);

  const onSubmit = (e)=> {
    e.preventDefault();
    navigate('/chat');
    return false;
  }

  const updateAvatar = (alias) =>{
    console.log(alias);
    const _avatar = alias ? encodeURI(`https://robohash.org/${alias.toLowerCase()}.png`) : DEFAULT_AVATAR;
    setAvatar(_avatar);
  }

    return (
      <div className="container">
        <div className="panel panel-default card card-container">
          <img id="profile-img" className="profile-img-card" src={avatar} />
          <p id="profile-name" className="profile-name-card"></p>
          <form className="form" 
                onSubmit={(e)=>onSubmit(e)}
            >
            <div className="form-group">
              <input type="text"
                id="inputAlias"
                value={alias}
                className="form-control input-lg"
                placeholder="Alias"
                ref={input => aliasInput.current = input}
                onChange={event => setAlias(event.target.value)}
                required autoFocus/>
            </div>
            <div className="form-group">
              <button className="btn btn-lg btn-success btn-block" type="submit">Chat</button>
            </div>
          </form>
        </div>
      </div>
    );
  }

  export default Login;