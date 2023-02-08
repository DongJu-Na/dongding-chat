import React from 'react';
import moment from 'moment/moment';

function HumanizedTime({prefix,suffix,time,date}) {

  if(!time.now){
    return (<div/>);
  }

    const _prefix = prefix || "";
    const _suffix = suffix || "ago";
    const timeAgo = moment.duration(time.now.getTime() - date);
    return (
      <span>{_prefix} {timeAgo.humanize()} {_suffix}</span>
    );
  }

  export default HumanizedTime;