import {Component} from 'react';
import Navbar from '../pages/Navbar.js';
import { Navigate, Link } from 'react-router-dom'
import '../style/style.css';


class Profile extends Component {
  
    render() {
        let bearer = window.localStorage.getItem("bearer");
        if (bearer == null || bearer === "") {
            return <Navigate to="/" />;
        }
        // I really don't like this structure.
        return (
        <div>
            <Navbar />
            <div class="container">
                <h1>Min profil</h1> 

                <table class="profile-info">
                    <tr>
                        <td>Brukernavn</td>
                        <td>{window.localStorage.getItem("username")}</td>
                    </tr>
                    <tr>
                        <td>Epost</td>
                        <td>{window.localStorage.getItem("email")}</td>
                    </tr>
                    <tr>
                        <td>Telefonnr.</td>
                        <td>{window.localStorage.getItem("phoneNumber") || "ikke registrert"}</td>
                    </tr>
                </table>
                <br />
                <Link to="../pages/explore">Tilbake</Link>
            </div>
        </div>
        );
    };
}
  
export default Profile;
