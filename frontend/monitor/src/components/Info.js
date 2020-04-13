import React from 'react';

export  default class Info extends React.Component {

  render() {
    let key = 1;
    const messages = this.props.messages.map((msg) =>
      <p key={ key++ }>{msg}</p>
    );

    return (
      <div className="info">{messages}</div>
    );
  }

}