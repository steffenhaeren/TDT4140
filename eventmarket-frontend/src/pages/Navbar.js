import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import '../style/style.css';
import logo from '../images/event-market-logo.png'


class Navbar extends Component{


render() {
    return (
        <>
            <div className="navbar">
                <Link to="/pages/explore"><img src={logo} width="150px" height="150px" alt="logo" /></Link>
                <div class="nav-buttons">
                    <Link class="ui-button ui-button-primary" to="/pages/explore">Utforsk</Link>
                    <Link class="ui-button ui-button-primary" to="/pages/profile">Profil</Link>
                    <Link class="ui-button ui-button-primary" to="/pages/createevent">Lag en annonse</Link>
                    <Link class="ui-button ui-button-primary" to="/">Logg ut</Link>
                </div>
            </div>
        </>
    )
}

}

export default Navbar;
